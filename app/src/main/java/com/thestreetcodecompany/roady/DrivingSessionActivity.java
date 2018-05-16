package com.thestreetcodecompany.roady;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.model.DrivingSession;

public class DrivingSessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_session);

        Intent intent = getIntent();

        long id = intent.getLongExtra("id", 1);
        DrivingSession session = DrivingSession.find(DrivingSession.class, "id = ?", id+"").get(0);
        ((TextView) findViewById(R.id.distance)).setText(session.getDistance() + " km");
        ((TextView) findViewById(R.id.car)).setText(session.getCar().getName());
        ((TextView) findViewById(R.id.co_driver)).setText(session.getCoDriver().getName());
    }
}
