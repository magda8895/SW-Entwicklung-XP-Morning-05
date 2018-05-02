package com.thestreetcodecompany.roady;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.thestreetcodecompany.roady.classes.PushService;

import java.util.Calendar;

import static com.thestreetcodecompany.roady.classes.Helper.MakeToast;

public class PushActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button push_button = (Button)findViewById(R.id.push_button);

        push_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MakeToast("click",getApplicationContext());
                startService(new Intent(getApplicationContext(), PushService.class));
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(getApplicationContext(), PushService.class);
                PendingIntent pintent = PendingIntent
                        .getService(getApplicationContext(), 0, intent, 0);

                AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                // Start service every hour
                alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        1000, pintent);


/*
                String title="title";
                String subject="asdf";
                String body="asdf";

                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getApplicationContext()).setContentTitle(title).setContentText(body).
                        setContentTitle(subject).setSmallIcon(R.drawable.fab_add).build();

                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);*/
            }
        });


    }

}
