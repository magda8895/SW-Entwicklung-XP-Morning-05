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


        // DB Connect
        final DBHandler dbh = new DBHandler();
        rd = RoadyData.getInstance();
        DrivingSession lastDrivingSession = rd.user.getLastDrivingSession();


        //when the app was quit unexep.

        int StopWatchCrash = getIntent().getIntExtra("StopWatchCrash",0);
        if(StopWatchCrash == 1)
        {

            long lgLastSessionStartTime =lastDrivingSession.getDateTimeStart();


            Date d = new Date(lgLastSessionStartTime);
            Date now = new Date();


            String lastSessionTime = d.toString();
            //lastSessionTime = lastSessionTime.substring(11,19);
            Toast.makeText(this, "last Driving Session : " + lastSessionTime + d  , Toast.LENGTH_SHORT).show();


        }


        chronometer = findViewById(R.id.chronometer);
        //chronometer.setBase(SystemClock.elapsedRealtime() - (2 * 60000 + 3 * 1000));
        chronometer.start();
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


}




