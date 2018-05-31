package com.thestreetcodecompany.roady;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Ref;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import com.thestreetcodecompany.roady.classes.model.DrivingSession;

public class NewDrivingSession extends AppCompatActivity  {

    final Calendar calStart = Calendar.getInstance();
    final Calendar calEnd = Calendar.getInstance();
    final Calendar calNow = Calendar.getInstance();
    int buttonID = 0;

    String st;
    String stDateandTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context c = this;
        setContentView(R.layout.activity_new_driving_session);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //EnterMileage
         final EditText editTextMileage =(EditText) findViewById(R.id.editMileage);



        //SetCurrentDateandTime
        final TextView StartDate = (TextView) findViewById(R.id.textViewDate);
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
        final String currentDateandTime = sdf.format(new Date());
        StartDate.setText(currentDateandTime);

        //Refresh current Date and Time
        final CountDownTimer RefreshTimer = new CountDownTimer(1, 1) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
               final SimpleDateFormat checkCurrentDateandTime = new SimpleDateFormat("dd MM yyyy HH:mm");
               final String currentDateandTime = checkCurrentDateandTime.format(new Date());

                if(sdf != checkCurrentDateandTime)
                {
                    StartDate.setText(currentDateandTime);
                    start();
                };
            }
        }.start();
        ;


        //Change to StopWatchScreen
        findViewById(R.id.StopwatchStartButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewDrivingSession.this,StopWatch.class);
                st = editTextMileage.getText().toString();
                i.putExtra("from_NDS_to_SW",st);
                stDateandTime = StartDate.getText().toString();
                i.putExtra("StartTime",stDateandTime);
                startActivity(i);
            }
        });
        ;

    }







};

