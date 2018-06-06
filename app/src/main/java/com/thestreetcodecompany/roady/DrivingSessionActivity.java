package com.thestreetcodecompany.roady;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thestreetcodecompany.roady.classes.model.DrivingSession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DrivingSessionActivity extends AppCompatActivity {

    private TextView distance;
    private TextView car;
    private TextView coDriver;
    private TextView date;
    private TextView duration;
    private TextView weatherCondition;
    private TextView roadCondition;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_session);

        Intent intent = getIntent();

        final long[] id = {intent.getLongExtra("id", 1)};
        List<DrivingSession> sessions = DrivingSession.find(DrivingSession.class, "id = ?", id[0] +"");
        if(sessions.size() == 0) {
            finish();
            return;
        }
        final DrivingSession[] session = {sessions.get(0)};

        distance = findViewById(R.id.distance);
        car = findViewById(R.id.car);
        coDriver = findViewById(R.id.co_driver);
        date = findViewById(R.id.date);
        duration = findViewById(R.id.duration);
        weatherCondition = findViewById(R.id.weather_condition);
        roadCondition = findViewById(R.id.street_condition);
        deleteButton = findViewById(R.id.delete_button);

        final ImageView left = findViewById(R.id.left);
        final ImageView right = findViewById(R.id.right);

        sessions = DrivingSession.listAll(DrivingSession.class, "date_timestart");
        final long[] leftId = {-1};
        final long[] rightId = { -1 };
        for(int i = 0; i < sessions.size(); i++) {
            if(session[0].getId() == sessions.get(i).getId()) {
                if(i>0) leftId[0] = sessions.get(i-1).getId();
                if(i<sessions.size()-1) rightId[0] = sessions.get(sessions.size()-1).getId();
            }
        }

        left.setEnabled(leftId[0] !=-1);
        final List<DrivingSession> finalSessions = sessions;
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session[0] = DrivingSession.find(DrivingSession.class, "id=?", ""+leftId[0]).get(0);
                updateData(leftId[0]);
                id[0] = leftId[0];
                leftId[0] = -1;
                rightId[0] = -1;
                for(int i = 0; i < finalSessions.size(); i++) {
                    if(session[0].getId().equals(finalSessions.get(i).getId())) {
                        if(i>0) leftId[0] = finalSessions.get(i-1).getId();
                        if(i< finalSessions.size()-1) rightId[0] = finalSessions.get(i+1).getId();
                    }
                }
                left.setEnabled(leftId[0] !=-1);
                right.setEnabled(rightId[0] !=-1);
            }
        });

        right.setEnabled(rightId[0] !=-1);
        right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                session[0] = DrivingSession.find(DrivingSession.class, "id=?", ""+rightId[0]).get(0);
                updateData(rightId[0]);
                id[0] = rightId[0];
                leftId[0] = -1;
                rightId[0] = -1;
                for(int i = 0; i < finalSessions.size(); i++) {
                    if(session[0].getId().equals(finalSessions.get(i).getId())) {
                        if(i>0) leftId[0] = finalSessions.get(i-1).getId();
                        if(i< finalSessions.size()-1) rightId[0] = finalSessions.get(i+1).getId();
                    }
                }
                left.setEnabled(leftId[0] !=-1);
                right.setEnabled(rightId[0] !=-1);
            }
        });

        updateData(id[0]);
    }

    private void updateData(long sessionId) {
        List<DrivingSession> sessions = DrivingSession.find(DrivingSession.class, "id = ?", sessionId+"");
        if(sessions.size() == 0) {
            finish();
            return;
        }
        final DrivingSession session = sessions.get(0);

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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DrivingSession.delete(session))
                    finish();
            }
        });
    }
}
