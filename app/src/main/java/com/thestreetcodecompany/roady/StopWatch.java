package com.thestreetcodecompany.roady;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;


import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.RoadyData;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;

public class StopWatch extends AppCompatActivity {

    private Chronometer chronometer;
    String St;
    String StDateandTime;

   // boolean activeDrivingSession = getIntent().getBooleanExtra("activeDrivingSession",false);

    RoadyData rd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);



        chronometer = findViewById(R.id.chronometer);


        //when the app was quit unexep.

        int StopWatchCrash = getIntent().getIntExtra("StopWatchCrash",1);
        if(StopWatchCrash == 1)
        {
            calcDiff();
        }
        else
        {
            chronometer.start();

        }

        TextView MileageView = (TextView) findViewById(R.id.textViewMileage);
        TextView StartTimeView = (TextView) findViewById(R.id.textViewStartTime);
        RoadyData rd;

        //get and set the passed Value
        St = getIntent().getExtras().getString("from_NDS_to_SW");
        MileageView.setText(St + " km");
        Float Mileage = Float.parseFloat(St);


        StDateandTime = getIntent().getExtras().getString("StartTime");
        StartTimeView.setText(StDateandTime);
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm ");



        //STOP Chronometer and change to DrivingSessionAfterScreen
        Button StopChronometer = (Button) findViewById(R.id.finishchronometer);

        StopChronometer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                chronometer.stop();
                Intent i = new Intent(StopWatch.this, DrivingSessionAfter.class);
                i.putExtra("from_SW_to_NDA", St);
                i.putExtra("StartTime", StDateandTime);
                i.putExtra("Pass", 1);
                startActivity(i);


            }
        });
        Button BackButton = (Button) findViewById(R.id.backButton);
        BackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public  void calcDiff() {
        // DB Connect
        final DBHandler dbh = new DBHandler();
        rd = RoadyData.getInstance();
        DrivingSession lastDrivingSession = rd.user.getLastDrivingSession();
        long lgLastSessionStartTime = lastDrivingSession.getDateTimeStart();
        Date d = new Date(lgLastSessionStartTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String StLastSessionStartTime = dateFormat.format(d);
        String stringlastStartedHour = StLastSessionStartTime.substring(11,13);
        String stringlastStartedMinute = StLastSessionStartTime.substring(14,16);
        String stringlastStartedSecond = StLastSessionStartTime.substring(17,19);

        Date now = Calendar.getInstance().getTime();
        String Stnow = dateFormat.format(now);
        String stringhournow = Stnow.substring(11,13);
        String stringminutenow = Stnow.substring(14,16);
        String stringsecondnow = Stnow.substring(17,19);



        //How many hours have passed since last session
        //Specify the data format
        DateFormat df = new SimpleDateFormat("HH");
        String lastTime = stringlastStartedHour;
        String currentTime = stringhournow;
        long diff = 0;

        try {

            //Convert to Date
            Date startDate = df.parse(lastTime);
            Calendar c1 = Calendar.getInstance();
            //Change to Calendar Date
            c1.setTime(startDate);

            //Convert to Date
            Date endDate = df.parse(currentTime);
            Calendar c2 = Calendar.getInstance();
            //Change to Calendar Date
            c2.setTime(endDate);

            //get Time in milli seconds
            long ms1 = c1.getTimeInMillis();
            long ms2 = c2.getTimeInMillis();
            //get difference in milli seconds
            diff = ms2 - ms1;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Find number of days by dividing the mili seconds
        int diffHour = (int) (diff / ( 60 * 1000));

        //To get number of seconds diff/1000
        //To get number of minutes diff/(1000 * 60)
        //To get number of hours diff/(1000 * 60 * 60)



        //--How many hours have passed since last session


        //How many minutes have passed since last session

        //Specify the data format
        DateFormat dm = new SimpleDateFormat("mm");
        String lastMin = stringlastStartedMinute;
        String currentMin = stringminutenow;
        long diffmin = 0;

        try {

            //Convert to Date
            Date startDate = dm.parse(lastMin);
            Calendar c1 = Calendar.getInstance();
            //Change to Calendar Date
            c1.setTime(startDate);

            //Convert to Date
            Date endDate = dm.parse(currentMin);
            Calendar c2 = Calendar.getInstance();
            //Change to Calendar Date
            c2.setTime(endDate);

            //get Time in milli seconds
            long ms1 = c1.getTimeInMillis();
            long ms2 = c2.getTimeInMillis();
            //get difference in milli seconds
            diffmin = ms2 - ms1;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Find number of days by dividing the mili seconds
        int diffMin = (int) (diffmin / ( 60 * 1000));

        //--How many minutes have passed since last session


        //How many seconds have passed since last session

        //Specify the data format
        DateFormat ds = new SimpleDateFormat("ss");
        String lastSec = stringlastStartedSecond;
        String currentSec = stringsecondnow;
        long diffsec = 0;

        try {

            //Convert to Date
            Date startDate = ds.parse(lastSec);
            Calendar c1 = Calendar.getInstance();
            //Change to Calendar Date
            c1.setTime(startDate);

            //Convert to Date
            Date endDate = ds.parse(currentSec);
            Calendar c2 = Calendar.getInstance();
            //Change to Calendar Date
            c2.setTime(endDate);

            //get Time in milli seconds
            long ms1 = c1.getTimeInMillis();
            long ms2 = c2.getTimeInMillis();
            //get difference in milli seconds
            diffsec = ms2 - ms1;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Find number of days by dividing the mili seconds
        int diffSec = (int) (diffsec / (1000));

        //--How many seconds have passed since last session
        //Toast.makeText(this, "saved successfully" + d , Toast.LENGTH_SHORT).show();




        chronometer.setBase(SystemClock.elapsedRealtime() - ((diffMin + diffHour) * 60000 + diffSec * 1000));
        chronometer.start();


    }







}




