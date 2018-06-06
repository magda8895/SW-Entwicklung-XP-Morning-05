package com.thestreetcodecompany.roady;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.RoadyData;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.FileHistory;
import com.thestreetcodecompany.roady.classes.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExportActivity extends AppCompatActivity {

    Button shareButton;
    EditText textFieldFileName;
    RoadyData rd = RoadyData.getInstance();
    List<String> fileHistory = new ArrayList<String>();


    public String getDaytime(DrivingSession ds){

        SimpleDateFormat localHourFormat = new SimpleDateFormat("HH");
        int hourString = Integer.parseInt(localHourFormat.format(ds.getDateTimeStart()));

        SimpleDateFormat localMinuteFormat = new SimpleDateFormat("mm");
        int minuteString = Integer.parseInt(localMinuteFormat.format(ds.getDateTimeStart()));

        SimpleDateFormat localSecondFormat = new SimpleDateFormat("ss");
        int secondString = Integer.parseInt(localSecondFormat.format(ds.getDateTimeStart()));

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, hourString);
        cal.set(Calendar.MINUTE, minuteString);
        cal.set(Calendar.SECOND, secondString);
        Time dsTime = new Time(cal.getTime().getTime());

        cal.set(Calendar.HOUR_OF_DAY, 06); // Start hour
        cal.set(Calendar.MINUTE, 00); // Start Mintue
        cal.set(Calendar.SECOND, 00); // Start second
        Time morning = new Time(cal.getTime().getTime());

        cal.set(Calendar.HOUR_OF_DAY, 12); // Start hour
        cal.set(Calendar.MINUTE, 00); // Start Mintue
        cal.set(Calendar.SECOND, 00); // Start second
        Time midday = new Time(cal.getTime().getTime());

        cal.set(Calendar.HOUR_OF_DAY, 18); // Start hour
        cal.set(Calendar.MINUTE, 00); // Start Mintue
        cal.set(Calendar.SECOND, 00); // Start second
        Time evening = new Time(cal.getTime().getTime());

        String daytime = "";

        if(dsTime.after(morning) && dsTime.before(midday)){
            daytime = "Morning";
        }else if(dsTime.after(midday) && dsTime.before(evening)){
            daytime = "Afternoon";
        }else if(dsTime.after(evening) ||  dsTime.before(morning)){
            daytime = "Night";
        }else if(dsTime.after(morning) && dsTime.before(evening)){
            daytime = "Daytime";
        }else{
            daytime = "Not Sure";
        }

        return daytime;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_export);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shareButton = (Button) findViewById(R.id.buttonShare);
        textFieldFileName = (EditText) findViewById(R.id.editTextFileName);

        ListView listView = (ListView) findViewById(R.id.file_history_list);
        List<FileHistory> fileHistories = rd.user.getAllFileHistories();
        Log.i("Export", "Test " + fileHistories.size());
        for(FileHistory fh : fileHistories){
            fileHistory.add(fh.getHistory());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileHistory);
        listView.setAdapter(adapter);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                // for testing
                // DBHandler db = new DBHandler();
                // User user = db.makeTestDataForExport();

                RoadyData rd = RoadyData.getInstance();
                if(rd.user==null) rd.user = new DBHandler().makeTestData();

                List<DrivingSession> drivingSessions = rd.user.getAllDrivingSessions();

                if(drivingSessions.isEmpty())
                {
                    Snackbar.make(view, R.string.export_no_driving_sessions, Snackbar.LENGTH_LONG).show();
                    return;
                }

                String fileName = textFieldFileName.getText().toString() + ".csv" ;

                if(fileName.equals(".csv")){
                    Snackbar.make(view, R.string.export_empty_filename, Snackbar.LENGTH_LONG).show();
                    return;
                }

                File file = new File(getApplicationContext().getFilesDir(), fileName);

                try {
                    FileWriter writer = new FileWriter(file);

                    String heading = "Date," +
                            "Driven Kilometers," +
                            "Mileage Start," +
                            "Mileage End, " +
                            "License Plate," +
                            "Daytime," +
                            "Driving Route," +
                            "\"Weather/Street\nCondition\"," +
                            "\"Signature\nCo-Driver\t\t\t\tDriver\"" + '\n';
                    writer.append(heading);

                    for(DrivingSession ds: drivingSessions){

                        if(ds.getCar() != null  && ds.getActive() != true){
                            writer.append(ds.getDateStringStart() + ',');
                            writer.append(String.valueOf(ds.getDistance()) + ',');
                            writer.append(String.valueOf(ds.getKmStart()) + ',');
                            writer.append(String.valueOf(ds.getKmEnd()) + ',');
                            writer.append(ds.getCar().getKfz() + ',');
                            writer.append(getDaytime(ds) + ',');
                            writer.append(ds.getName() + ',');
                            writer.append(getApplicationContext().getResources().getStringArray(R.array.Weather)[ds.getWeather()] + '/');
                            writer.append(getApplicationContext().getResources().getStringArray(R.array.RoadConditions)[ds.getStreetCondition()] + ',');
                            writer.append('\n');
                        }
                    }

                    writer.flush();
                    writer.close();
                    Log.i("File Writing stuff", "success");
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }

                Uri csvURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() +
                        ".com.thestreetcodecompany.roady.provider", file);

                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date today = new Date();

                Intent sharingIntent = new Intent();
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                sharingIntent.setAction(Intent.ACTION_SEND);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Journey Log");
                //sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Here is your recent journey log from:\n" + dt.format(today) );
                sharingIntent.putExtra(Intent.EXTRA_STREAM, csvURI);
                sharingIntent.setType("text/plain");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

                String newHistory = "File: " + fileName + "\nCreated on: " + new Date();
                FileHistory fh = new FileHistory(newHistory, rd.user);
                fh.save();
                List<FileHistory> fileHistories = rd.user.getAllFileHistories();
                Log.i("Export", "Test " + fileHistories.size());
                fileHistory.clear();
                for(FileHistory fhs : fileHistories){
                    fileHistory.add(fhs.getHistory());
                }
            }
        });

    }
}
