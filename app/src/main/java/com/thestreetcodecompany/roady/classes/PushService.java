package com.thestreetcodecompany.roady.classes;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

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
        Toast.makeText(this, " MyService Created ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //Toast.makeText(this, " MyService Started", Toast.LENGTH_LONG).show();
        Log.d("Test","push");

        DBHandler dbh = new DBHandler();
        User user = dbh.getTestUser();

        Date currentTime = Calendar.getInstance().getTime();
        DrivingSession ds = new DrivingSession();
        ds.setUser(user);
        ds.setKmStart(200);
        ds.setKmEnd(400);
        ds.setDateTimeStringStart("");
        //ds.setDateTimeStringEnd();
        ds.setName(currentTime.toString());

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Servics Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

}