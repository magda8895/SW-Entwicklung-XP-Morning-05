package com.thestreetcodecompany.roady.classes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.thestreetcodecompany.roady.classes.model.CoDriver;
import com.thestreetcodecompany.roady.classes.Helper.*;

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
        Log.d("Push","push service started"); }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d("Push","push");
        //TODO: Cases:
        /*
        Wenn er lang nicht mehr gefahren ist.
        Wenn er eine laufende Fahrt hat, die er noch nicht beendet hat
        Wenn er 1000 km erreicht hat...
         */

        //TODO: add CheckBox for the Service in the Settingsactiviy


        //push
        MakePush("Push Test",getApplicationContext());


        //all for testing (save one CoDriver with name:currentDateTime)
        DBHandler dbh = new DBHandler();
        Date currentTime = Calendar.getInstance().getTime();
        CoDriver new_push = new CoDriver();
        new_push.setName(currentTime.toString());
        new_push.setUser(dbh.getTestUser());
        new_push.save();

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.d("Push","push service stopped");
        MakeToast("onDestroy",getApplication());
        super.onDestroy();
    }

}