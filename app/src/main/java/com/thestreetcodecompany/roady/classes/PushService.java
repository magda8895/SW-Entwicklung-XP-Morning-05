package com.thestreetcodecompany.roady.classes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.thestreetcodecompany.roady.R;
import com.thestreetcodecompany.roady.StopWatch;
import com.thestreetcodecompany.roady.classes.model.CoDriver;
import com.thestreetcodecompany.roady.classes.Helper.*;
import com.thestreetcodecompany.roady.classes.model.Push;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.Calendar;
import java.util.Date;

import static com.thestreetcodecompany.roady.classes.Helper.MakePush;
import static com.thestreetcodecompany.roady.classes.Helper.MakeToast;

/**
 * Created by Rutter on 02.05.2018.
 */

public class PushService extends Service {


    public PushService()
    {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("Push","push service started");
        MakeToast("service started",getApplicationContext());
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d("Push","push");

        RoadyData rd = RoadyData.getInstance();
        DBHandler dbh = new DBHandler();

        if(rd.user != null) {
            long time = rd.user.getTimeSinceLastDrivingSession();
            long one_week = 1000 * 60 * 60 * 24 * 7;
            long hours = 1000 * 60 * 60 * 8;

            if (rd.user.hasActiveDrivingSession() != null && time > hours) {
                MakePush(getString(R.string.activepush_title), getString(R.string.activepush_body), StopWatch.class, getApplicationContext());
                Push p = new Push(Calendar.getInstance().getTimeInMillis());
                p.save();
            } else if (time > one_week) {
                long last = dbh.getTimeSinceLastPush();
                Log.d("Push", "last: " + last);
                if (last > one_week) {
                    MakePush(getString(R.string.timepush_title), getString(R.string.timepush_body), getApplicationContext());
                    Push p = new Push(Calendar.getInstance().getTimeInMillis());
                    p.save();
                }
            }

        //push to DB


        }

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.d("Push","push service stopped");
        MakeToast("push service stopped",getApplication());
        super.onDestroy();
    }

}