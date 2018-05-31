package com.thestreetcodecompany.roady;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.media.MediaCas;
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
import android.widget.Toast;

import com.amitshekhar.utils.DatabaseHelper;
import com.orm.SugarRecord;
import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.RoadyData;
import com.thestreetcodecompany.roady.classes.model.Car;
import com.thestreetcodecompany.roady.classes.model.CoDriver;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class DrivingSessionAfter extends AppCompatActivity {

    final Calendar calStart = Calendar.getInstance();
    final Calendar calEnd = Calendar.getInstance();
    final SimpleDateFormat formatDate = new SimpleDateFormat("EEE, d MMM yyyy");
    final SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
    int buttonID = 0;
    String PassedDate;
    String StMileage;
    int Pass;


    RoadyData rd;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context c = this;
        setContentView(R.layout.activity_driving_session_after);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //if bool true
        //object holen


        // show date and time
        calStart.add(Calendar.HOUR_OF_DAY, -1);
        calStart.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.MINUTE, 0);

        Button startDate = findViewById(R.id.buttonDateStart);
        Button endDate = findViewById(R.id.buttonDateEnd);
        Button endTime = findViewById(R.id.buttonTimeEnd);
        Button startTime = findViewById(R.id.buttonTimeStart);
        TextView kmStart = findViewById(R.id.editTextMileageStart);


        Pass = getIntent().getIntExtra("Pass",0);



        final SimpleDateFormat sdfDate = new SimpleDateFormat("EEE, d MMM yyyy");
        final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        final String currentDate = sdfDate.format(new Date());
        final String currentTime = sdfTime.format(new Date());

        // in case the drive was started yesterday
        final SimpleDateFormat sdfCheckDate = new SimpleDateFormat("dd MM yyyy");
        String checkDate = sdfCheckDate.format(new Date());
        checkDate = checkDate.substring(0,10);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        // DB Connect
        final DBHandler dbh = new DBHandler();
        rd = RoadyData.getInstance();

        if (Pass == 1)
        {
            PassedDate =  getIntent().getStringExtra("StartTime");
            String PassedTime = PassedDate.substring(11);
            PassedDate = PassedDate.substring(0,10);
            if(checkDate.equals(PassedDate))
           {
                startDate.setText(sdfDate.format(new Date()));
                startTime.setText(PassedTime);
           }

            else
           {
               startDate.setText(sdfDate.format(cal.getTime()));
               startTime.setText(PassedTime);
           }

            //Set Current Date and Time as EndTime
            endDate.setText(currentDate);
            endTime.setText(currentTime);

            //Set passed Mileage
            StMileage = getIntent().getExtras().getString("from_SW_to_NDA");
            kmStart.setText(StMileage);
            }

        else
        {
            startDate.setText(formatDate.format(calStart.getTime()));
            endDate.setText(formatDate.format(calEnd.getTime()));
            endTime.setText(formatTime.format(calEnd.getTime()));
            startTime.setText(formatTime.format(calStart.getTime()));
        }


        // list cars
        List<Car> cars = rd.user.getCars();
        ArrayList<String> carArray = new ArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            carArray.add(cars.get(i).getName());
        }

        Spinner vehicleSpinner = findViewById(R.id.spinnerVehicle);
        ArrayAdapter<String> adapterVehicle = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carArray);
        adapterVehicle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleSpinner.setAdapter(adapterVehicle);

        Log.d("cars", cars.toString());

        // list coDriver
        List<CoDriver> coDrivers = rd.user.getCoDrivers();
        ArrayList<String> coDriverArray = new ArrayList<>();
        for (int i = 0; i < coDrivers.size(); i++) {
            coDriverArray.add(coDrivers.get(i).getName()); }

        final Spinner coDriverSpinner = findViewById(R.id.spinnerCoDriver);
        ArrayAdapter<String> adapterCoDriver = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, coDriverArray);
        adapterCoDriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coDriverSpinner.setAdapter(adapterCoDriver);

        //Log.d("coDrivers", coDrivers.toString());


        final Spinner weatherSpinner = findViewById(R.id.spinnerWeather);
        ArrayAdapter<CharSequence> adapterWeather = ArrayAdapter.createFromResource(this,
                R.array.Weather, android.R.layout.simple_spinner_item);
        adapterWeather.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherSpinner.setAdapter(adapterWeather);

        // Road Condition
        final Spinner roadConditionsSpinner = findViewById(R.id.spinnerRoadCondition);
        ArrayAdapter<CharSequence> adapterRoadCondition = ArrayAdapter.createFromResource(this,
                R.array.RoadConditions, android.R.layout.simple_spinner_item);
        adapterRoadCondition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roadConditionsSpinner.setAdapter(adapterRoadCondition);


        // Start Date
        startDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                buttonID = v.getId();

                final int mYear, mMonth, mDay;
                mYear = calStart.get(Calendar.YEAR);
                mMonth = calStart.get(Calendar.MONTH);
                mDay = calStart.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(c, datePickerListener, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        // Start Time
        startTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                buttonID = v.getId();

                final int mHour, mMinute;
                mHour = calStart.get(Calendar.HOUR_OF_DAY);
                mMinute = calStart.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(c, timePickerListener, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });


        // End Date
        endDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                buttonID = v.getId();

                final int mYear, mMonth, mDay;
                mYear = calEnd.get(Calendar.YEAR);
                mMonth = calEnd.get(Calendar.MONTH);
                mDay = calEnd.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(c, datePickerListener, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        // End Time
        endTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                buttonID = v.getId();

                final int mHour, mMinute;
                mHour = calEnd.get(Calendar.HOUR_OF_DAY);
                mMinute = calEnd.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(c, timePickerListener, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });


        final Button save = findViewById(R.id.buttonSave);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {

                    TextView nameText = findViewById(R.id.editTextRoute);
                    String name = nameText.getText().toString();

                    if (name == null || name.isEmpty()) {
                        throw new DrivingSessionException("please enter a name");
                    }


                    Date dateTime_start = calStart.getTime();
                    Date dateTime_end = calEnd.getTime();

                    if (!dateTime_end.after(dateTime_start)) {
                        throw new DrivingSessionException("can't finish driving before starting - check dates");
                    }


                    Spinner carSpinner = findViewById(R.id.spinnerVehicle);
                    String car = carSpinner.getSelectedItem().toString();


                    float km_start = -1;
                    TextView kmStart = findViewById(R.id.editTextMileageStart);
                    if (!kmStart.getText().toString().isEmpty()) {
                        km_start = Float.parseFloat(kmStart.getText().toString());
                    }


                    float km_end = -1;
                    TextView kmEnd = findViewById(R.id.editTextMileageEnd);
                    if (!kmEnd.getText().toString().isEmpty()) {
                        km_end = Float.parseFloat(kmEnd.getText().toString());
                    }

                    if (km_start < 0 || km_end < 0) {
                        throw new DrivingSessionException("please enter mileage");
                    } else if (km_end - km_start < 0) {
                        throw new DrivingSessionException("end mileage must be higher than start");
                    } else if (km_end - km_start == 0) {
                        throw new DrivingSessionException("start and end mileage can't be same");
                    }


                    // DB Connect
                    //DBHandler dbh = new DBHandler();

                    if (rd.user == null) {
                        throw new DrivingSessionException("please add a user in settings first");
                    }


                    Spinner coDriverSpinner = findViewById(R.id.spinnerCoDriver);
                    String co_driver = coDriverSpinner.getSelectedItem().toString();

                    int weather = weatherSpinner.getSelectedItemPosition();
                    int street_condition = roadConditionsSpinner.getSelectedItemPosition();

                    // save to db
                    if(Pass == 1){
                        //get the ID from the db-row we created in the stopwatch screen
                        //long drivingSessionID = rd.user.getLastDrivingSessionID();
                       //long lastId = lastDrivingSession.getId();
                      //latestSession.executeQuery("UPDATE user SET name = 'updatetest' WHERE id=? " + lastId );

                        DrivingSession lastDrivingSession = rd.user.getLastDrivingSession();

                        lastDrivingSession.setName(name);
                        lastDrivingSession.setDateTimeStart(dateTime_start.getTime());
                        lastDrivingSession.setDateTimeEnd(dateTime_end.getTime());
                        lastDrivingSession.setCar(car);
                        lastDrivingSession.setCoDriver(co_driver);
                        lastDrivingSession.setKmStart(km_start);
                        lastDrivingSession.setKmEnd(km_end);
                        lastDrivingSession.setWeather(weather);
                        lastDrivingSession.setStreetCondition(street_condition);
                        lastDrivingSession.setUser(rd.user);
                        lastDrivingSession.save();
                        //lastDrivingSession.update();

                    }
                    else {
                        DrivingSession newSession = new DrivingSession(name, dateTime_start.getTime(), dateTime_end.getTime(), car, co_driver,
                                km_start, km_end, weather, street_condition, rd.user);
                        newSession.save();
                    }
                    // make toast
                    Toast.makeText(c, "saved successfully", Toast.LENGTH_SHORT).show();
                    finish();



                } catch (DrivingSessionException ex) {

                    ex.printStackTrace();

                    // make toast
                    Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();

                }




            }
        });



    }


    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            Button button;

            switch (buttonID) {
                case R.id.buttonDateStart:
                    calStart.set(Calendar.YEAR, selectedYear);
                    calStart.set(Calendar.MONTH, selectedMonth);
                    calStart.set(Calendar.DAY_OF_MONTH, selectedDay);

                    button = findViewById(buttonID);
                    button.setText(formatDate.format(calStart.getTime()));
                    break;

                case R.id.buttonDateEnd:
                    calEnd.set(Calendar.YEAR, selectedYear);
                    calEnd.set(Calendar.MONTH, selectedMonth);
                    calEnd.set(Calendar.DAY_OF_MONTH, selectedDay);

                    button = findViewById(buttonID);
                    button.setText(formatDate.format(calEnd.getTime()));
                    break;
            }

        }
    };


    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                    Button button;

                    switch (buttonID) {
                        case R.id.buttonTimeStart:
                            calStart.set(Calendar.HOUR_OF_DAY, selectedHour);
                            calStart.set(Calendar.MINUTE, selectedMinute);

                            button = findViewById(buttonID);
                            button.setText(formatTime.format(calStart.getTime()));
                            break;

                        case R.id.buttonTimeEnd:
                            calEnd.set(Calendar.HOUR_OF_DAY, selectedHour);
                            calEnd.set(Calendar.MINUTE, selectedMinute);

                            button = findViewById(buttonID);
                            button.setText(formatTime.format(calEnd.getTime()));
                            break;
                    }
                }
            };

}




/**
 * driving session exception
 */
class DrivingSessionException extends Exception
{
    // Parameterless Constructor
    public DrivingSessionException() {}

    // Constructor that accepts a message
    public DrivingSessionException(String message)
    {
        super(message);
    }
}
