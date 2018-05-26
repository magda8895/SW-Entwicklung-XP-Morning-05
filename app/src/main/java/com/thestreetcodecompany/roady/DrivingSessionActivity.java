package com.thestreetcodecompany.roady;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.model.DrivingSession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DrivingSessionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_session);

        Intent intent = getIntent();

        long id = intent.getLongExtra("id", 1);
        DrivingSession session = DrivingSession.find(DrivingSession.class, "id = ?", id+"").get(0);

        TextView distance = findViewById(R.id.distance);
        TextView car = findViewById(R.id.car);
        TextView coDriver = findViewById(R.id.co_driver);
        TextView date = findViewById(R.id.date);
        TextView duration = findViewById(R.id.duration);
        TextView weatherCondition = findViewById(R.id.weather_condition);
        TextView roadCondition = findViewById(R.id.street_condition);

        Spanned unknown = Html.fromHtml("<i>Unknown</i>");

        distance.setText(session.getDistance() + " km");
        if(session.getCar() != null)
            car.setText(session.getCar().getName());
        else
            car.setText(unknown);
        if(session.getCoDriver() != null)
            coDriver.setText(session.getCoDriver().getName());
        else
            coDriver.setText(unknown);
        DateFormat dfDate = new SimpleDateFormat("E, d YYYY 'at' HH:mm");
        date.setText(dfDate.format(new Date(session.getDateTimeStart())));
        Date d = new Date(session.getDateTimeEnd() - session.getDateTimeStart());
        DateFormat dfDuration = new SimpleDateFormat("HH:mm");
        duration.setText(dfDuration.format(d));
        weatherCondition.setText(getResources().getStringArray(R.array.Weather)[session.getWeather()]);
        roadCondition.setText(getResources().getStringArray(R.array.RoadConditions)[session.getStreetCondition()]);
    }
}
