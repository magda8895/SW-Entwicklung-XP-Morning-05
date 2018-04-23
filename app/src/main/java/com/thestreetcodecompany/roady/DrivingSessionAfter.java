package com.thestreetcodecompany.roady;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.model.Car;
import com.thestreetcodecompany.roady.classes.model.CoDriver;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Spinner weatherSpinner = findViewById(R.id.spinnerWeather);
        ArrayAdapter<CharSequence> adapterWeather = ArrayAdapter.createFromResource(this,
                R.array.Weather, android.R.layout.simple_spinner_item);
        adapterWeather.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherSpinner.setAdapter(adapterWeather);

        Spinner roadConditionsSpinner = findViewById(R.id.spinnerRoadCondition);
        ArrayAdapter<CharSequence> adapterRoadCondition = ArrayAdapter.createFromResource(this,
                R.array.RoadConditions, android.R.layout.simple_spinner_item);
        adapterRoadCondition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roadConditionsSpinner.setAdapter(adapterRoadCondition);



        // DB Connect
        DBHandler dbh = new DBHandler();

        // list cars
        List<Car> cars = dbh.getAllCars();
        ArrayList<String> carArray = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            carArray.add(cars.get(i).getName());
        }

        Spinner vehicleSpinner = findViewById(R.id.spinnerVehicle);
        //ArrayAdapter<CharSequence> adapterVehicle = ArrayAdapter.createFromResource(this, carArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapterVehicle = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carArray);
        adapterVehicle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleSpinner.setAdapter(adapterVehicle);


        Log.d("cars", cars.toString());



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


        final Button save = findViewById(R.id.buttonSave);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: check all input
                String name = "GRZ - VIE";
                Date dateTime_start = calStart.getTime();
                Date dateTime_end = calEnd.getTime();

                Spinner carSpinner = findViewById(R.id.spinnerVehicle);
                String car = carSpinner.getSelectedItem().toString();

                TextView kmStart = findViewById(R.id.editTextMileageStart);
                float km_start = Float.parseFloat(kmStart.getText().toString());

                TextView kmEnd = findViewById(R.id.editTextMileageEnd);
                float km_end = Float.parseFloat(kmEnd.getText().toString());

                // DB Connect
                DBHandler dbh = new DBHandler();
                User user = dbh.getTestUser();

                //TextView coDriver = findViewById(R.id.spinnerCoDriver);
                //String co_driver = coDriver.getText().toString();
                String co_driver = "Dummy";

                int weather = 0;
                int street_condition = 0;

                // save to db
                DrivingSession newSession = new DrivingSession(name, dateTime_start, dateTime_end, car,
                        km_start, km_end, user, co_driver, weather, street_condition);
                newSession.save();
            }
        });

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
