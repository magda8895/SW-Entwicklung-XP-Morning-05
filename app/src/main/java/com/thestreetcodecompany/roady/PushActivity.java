package com.thestreetcodecompany.roady;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.thestreetcodecompany.roady.classes.DBHandler;
import com.thestreetcodecompany.roady.classes.PushService;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.Calendar;

import static com.thestreetcodecompany.roady.classes.Helper.*;



public class PushActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button startAlarm = (Button)findViewById(R.id.button_startAlarm);
        Button cancelAlarm = (Button)findViewById(R.id.button_cancelAlarm);
        Button db_button = (Button)findViewById(R.id.btn_printdb);

        final AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        final Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(getApplicationContext(), PushService.class);
        final PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, intent, 0);

        startAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(), PushService.class));

                // Start service every hour
                //alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 8 * 3600 * 10000, pintent); //

                alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        AlarmManager.INTERVAL_FIFTEEN_MINUTES, pintent); //
                //https://stackoverflow.com/questions/17718154/alarmmanager-setrepeating?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

            }

        });
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarm.cancel(pintent);
                stopService(new Intent(getApplicationContext(), PushService.class));
            }

        });

        db_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbh = new DBHandler();
                User user = dbh.getTestUser();

                dbh.logAllCoDrivers();
                MakePush("sdf","asdf",SettingsBackend.class,getApplicationContext());
            }
            });

    }

}
