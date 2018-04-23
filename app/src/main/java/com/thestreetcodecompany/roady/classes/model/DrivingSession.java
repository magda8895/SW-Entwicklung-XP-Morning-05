package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.thestreetcodecompany.roady.classes.Helper.MakeToast;

/**
 * Created by Rutter on 23.03.2018.
 * Last changed by Schauberger on 23.04.2018
 */

public class DrivingSession extends SugarRecord {

    //id
    boolean active;
    String name;
    Date dateTime_start;
    Date dateTime_end;
    Car car;
    float km_start;
    float km_end;
    CoDriver coDriver;
    int weather;
    int street_condition;
    User user;
    boolean deleted;


    public DrivingSession() {}

    public DrivingSession(boolean active, String dateTime_start, float km_start, User user) {
        this.active = active;
        setDateTime_start(dateTime_start);
        this.km_start = km_start;
        this.user = user;
    }

    public DrivingSession(String name, Date dateTime_start, Date dateTime_end, String car,
                          float km_start, float km_end, String coDriver, int weather, int street_condition) {
        this.name = name;
        this.dateTime_start = dateTime_start;
        this.dateTime_end = dateTime_end;
        //this.car = car;
        this.km_start = km_start;
        this.km_end = km_end;
        //this.coDriver = coDriver;
        this.weather = weather;
        this.street_condition = street_condition;
    }

    public void setDateTime_start(String dateTime) {
        this.dateTime_start = formatDateTime(dateTime);
    }

    public void setDateTime_end(String dateTime) {
        this.dateTime_end = formatDateTime(dateTime);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeather() {
        return this.weather;
    }


    public String getName()
    {
        if (this.name != null && !this.name.isEmpty()) {
            return this.name;
        } else {
            return "Graz - Wien (Dummy)";
        }
    }

    public float getDistance()
    {
        float distance = 0;
        if ((this.km_end - this.km_start) > 0) {
            distance = this.km_end - this.km_start;
        } else {
            distance = (float) 0.0;
        }
        return distance;
    }

    public String getTimeSpan()
    {
        //TODO: Datum ausgeben
        return "02.05.2018";
    }


    public Date formatDateTime(String dateTime) {
        String pattern = "dd-mm-yyyy hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date();
        //date.setHours(0);
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
