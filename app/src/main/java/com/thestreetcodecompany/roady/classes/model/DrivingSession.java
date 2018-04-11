package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.thestreetcodecompany.roady.classes.Helper.MakeToast;

/**
 * Created by Rutter on 23.03.2018.
 */

public class DrivingSession  extends SugarRecord {

    //id
    boolean active;
    int weather;
    int street_condition;
    Date dateTime_start;
    Date dateTime_end;
    float km_start;
    float km_end;
    User user;
    Car car;
    CoDriver codriver;
    boolean deleted;


    public DrivingSession(){}

    public DrivingSession(boolean active, String dateTime_start, float km_start, User user) {
        this.active = active;
        setDateTime_start(dateTime_start);
        this.km_start = km_start;
        this.user = user;
    }

    public void setDateTime_start(String dateTime) {

        this.dateTime_start = setDateTime(dateTime);
    }

    public void setDateTime_end(String dateTime) {

        this.dateTime_end = setDateTime(dateTime);
    }

    public Date setDateTime(String dateTime) {
        String pattern = "dd-mm-yyyy hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date();
        date.setHours(0);
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public float getDistance()
    {
        //TODO: Distanz berechnen
        return km_start;
    }
    public double getTimeSpan()
    {
        //TODO: Zeit ausrechnen
        return 30.0;
    }

    public String getName()
    {
        return "Graz - Wien";
    }

}
