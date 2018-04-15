package com.thestreetcodecompany.roady;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DrivingSessionAfter extends AppCompatActivity {

    final Calendar calStart = Calendar.getInstance();
    final Calendar calEnd = Calendar.getInstance();
    final Calendar calNow = Calendar.getInstance();
    int buttonID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context c = this;
        setContentView(R.layout.activity_driving_session_after);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Spinner weatherSpinner = findViewById(R.id.spinnerWeather);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Weather, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherSpinner.setAdapter(adapter1);

        Spinner roadConditionsSpinner = (Spinner) findViewById(R.id.spinnerRoadCondition);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.RoadConditions, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roadConditionsSpinner.setAdapter(adapter2);



        // Start Date
        final Button startDate = findViewById(R.id.buttonDateStart);
        startDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                buttonID = v.getId();

                final int mYear, mMonth, mDay;
                mYear = calNow.get(Calendar.YEAR);
                mMonth = calNow.get(Calendar.MONTH);
                mDay = calNow.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(c, datePickerListener, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        // Start Time
        final Button startTime = findViewById(R.id.buttonTimeStart);
        startTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                buttonID = v.getId();

                final int mHour, mMinute;
                mHour = calNow.get(Calendar.HOUR_OF_DAY);
                mMinute = calNow.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(c, timePickerListener, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });



        // End Date
        final Button endDate = findViewById(R.id.buttonDateEnd);
        endDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                buttonID = v.getId();

                final int mYear, mMonth, mDay;
                mYear = calNow.get(Calendar.YEAR);
                mMonth = calNow.get(Calendar.MONTH);
                mDay = calNow.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(c, datePickerListener, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        // End Time
        final Button endTime = findViewById(R.id.buttonTimeEnd);
        endTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                buttonID = v.getId();

                final int mHour, mMinute;
                mHour = calNow.get(Calendar.HOUR_OF_DAY);
                mMinute = calNow.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(c, timePickerListener, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }



    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            final Button button;
            SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy");

            switch (buttonID) {
                case R.id.buttonDateStart:
                    calStart.set(Calendar.YEAR, selectedYear);
                    calStart.set(Calendar.MONTH, selectedMonth);
                    calStart.set(Calendar.DAY_OF_MONTH, selectedDay);

                    button = findViewById(buttonID);
                    button.setText(format.format(calStart.getTime()));
                    break;

                case R.id.buttonDateEnd:
                    calEnd.set(Calendar.YEAR, selectedYear);
                    calEnd.set(Calendar.MONTH, selectedMonth);
                    calEnd.set(Calendar.DAY_OF_MONTH, selectedDay);

                    button = findViewById(buttonID);
                    button.setText(format.format(calEnd.getTime()));
                    break;
            }

        }
    };


    private TimePickerDialog.OnTimeSetListener timePickerListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                final Button button;
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                switch (buttonID) {
                    case R.id.buttonTimeStart:
                        calStart.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calStart.set(Calendar.MINUTE, selectedMinute);

                        button = findViewById(buttonID);
                        button.setText(format.format(calStart.getTime()));
                        break;

                    case R.id.buttonTimeEnd:
                        calEnd.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calEnd.set(Calendar.MINUTE, selectedMinute);

                        button = findViewById(buttonID);
                        button.setText(format.format(calEnd.getTime()));
                        break;
                }
            }
    };

}
