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
import java.util.Date;

import com.thestreetcodecompany.roady.classes.model.DrivingSession;

public class StopWatch extends AppCompatActivity {

    private Chronometer chronometer;
    String St;
    String StDateandTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        chronometer = findViewById(R.id.chronometer);
        chronometer.start();
        TextView MileageView = (TextView) findViewById(R.id.textViewMileage);
        TextView StartTimeView = (TextView) findViewById(R.id.textViewStartTime);


        //get and set the passed Value
       St = getIntent().getExtras().getString("from_NDS_to_SW");
       MileageView.setText( St + " km");

        StDateandTime = getIntent().getExtras().getString("StartTime");
        StartTimeView.setText(StDateandTime);


        //STOP Chronometer and change to DrivingSessionAfterScreen
        Button StopChronometer = (Button)findViewById(R.id.finishchronometer);

        StopChronometer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                chronometer.stop();
                Intent i = new Intent(StopWatch.this,DrivingSessionAfter.class);
                i.putExtra("from_SW_to_NDA",St);
                i.putExtra("StartTime",StDateandTime);
                i.putExtra("Pass",1);
                startActivity(i);


            }
        });
        Button BackButton = (Button)findViewById(R.id.backButton);
        BackButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }



}




