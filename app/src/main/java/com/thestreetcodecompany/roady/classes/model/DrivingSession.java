package com.thestreetcodecompany.roady.classes.model;

import android.util.Log;

import com.google.gson.internal.LinkedHashTreeMap;
import com.orm.SugarRecord;
import com.thestreetcodecompany.roady.classes.DBHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Rutter on 23.03.2018.
 * Last changed by Schauberger on 24.04.2018
 */

public class DrivingSession extends SugarRecord {

    //id
    private boolean active = false;
    private String name;
    private long dateTime_start;
    private long dateTime_end;
    private Car car;
    private CoDriver coDriver;
    private float km_start;
    private float km_end;
    private int weather;
    private int street_condition;


    private User user;
    boolean deleted;


    public DrivingSession() {}

    public DrivingSession(boolean active, String dateTime_start, float km_start, User user) {
        setActive(active);
        setDateTimeStringStart(dateTime_start);
        setKmStart(km_start);
        setUser(user);
    }

    public DrivingSession(String name, long dateTime_start, long dateTime_end, String car, String coDriver,
                          float km_start, float km_end, int weather, int street_condition, User user) {

        setName(name);

        setDateTimeStart(dateTime_start);
        setDateTimeEnd(dateTime_end);

        setCarString(car);
        setCoDriverString(coDriver);

        setKmStart(km_start);
        setKmEnd(km_end);

        setWeather(weather);
        setStreetCondition(street_condition);

        setUser(user);
    }

    public DrivingSession(String name, long dateTime_start, long dateTime_end, Car car, CoDriver coDriver,
                          float km_start, float km_end, int weather, int street_condition, User user) {

        setName(name);

        setDateTimeStart(dateTime_start);
        setDateTimeEnd(dateTime_end);

        setCar(car);
        setCoDriver(coDriver);

        setKmStart(km_start);
        setKmEnd(km_end);

        setWeather(weather);
        setStreetCondition(street_condition);

        setUser(user);
    }







    // getter
    public boolean getActive() {
        return this.active;
    }

    public String getName()
    {
        if (this.name != null && !this.name.isEmpty()) {
            return this.name;
        } else {
            return "Graz - Wien (Dummy)";
        }
    }

    public long getDateTimeStart() {
        return this.dateTime_start;
    }

    public long getDateTimeEnd() {
        return this.dateTime_end;
    }

    public Car getCar() {
        return this.car;
    }

    public CoDriver getCoDriver() {
        return coDriver;
    }

    public float getKmStart() {
        return this.km_start;
    }

    public float getKmEnd() {
        return this.km_end;
    }

    public int getWeather() {
        return this.weather;
    }

    public int getStreetCondition() {
        return this.street_condition;
    }

    public float getDistance()
    {
        float distance = 0;
        if ((this.km_end - this.km_start) > 0) {
            distance = this.km_end - this.km_start;
        }
        return distance;
    }

    @Override
    public long save(){
        Log.v("Test","SAVE DRVINGSESSION");
        Log.v("Test","Distance: " + getDistance());
        user.addDriven_km(getDistance());
        user.save();
        Log.v("Test","Distance: " + user.getDrivenKm());
        return super.save();
    }

    public String getDateStringStart() {
        String pattern = "dd. MMM yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(getDateTimeStart());
    }

    public String getDateStringEnd() {
        String pattern = "dd. MMM yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(getDateTimeEnd());
    }
    public User getUser() {
        return user;
    }



    // setter
    public void setActive(boolean active) {
        this.active = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateTimeStart(long dateTime_start) {
        this.dateTime_start = dateTime_start;
    }

    public void setDateTimeEnd(long dateTime_end) {
        this.dateTime_end = dateTime_end;
    }

    public void setDateTimeStringStart(String dateTime) {
        this.dateTime_start = formatDateTimeTimestamp(dateTime);
    }

    public void setDateTimeStringEnd(String dateTime) {
        this.dateTime_end = formatDateTimeTimestamp(dateTime);
    }

    public void setCar(Car car) {
        this.car = car;
    }


    public void setCarString(String car) {
        List<Car> cars = Car.find(Car.class, "name = ?", car);
        if (cars.size() <= 0) {
            this.car = null;
        } else {
            this.car = cars.get(0);
        }
    }

    public void setCar(Car car){
        this.car = car;
    }

    public void setKmStart(float km_start) {
        this.km_start = km_start;
    }

    public void setKmEnd(float km_end) {
        this.km_end = km_end;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCoDriver(CoDriver coDriver) {
        this.coDriver = coDriver;
    }

    public void setCoDriverString(String coDriver) {
        List<CoDriver> coDrivers = CoDriver.find(CoDriver.class, "name = ?", coDriver);
        if (coDrivers.size() <= 0) {
            // DB Connect
            DBHandler dbh = new DBHandler();
            this.coDriver = dbh.getTestCoDriver();
        } else {
            this.coDriver = coDrivers.get(0);
        }
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public void setStreetCondition(int street_condition) {
        this.street_condition = street_condition;
    }


    // format date time
    public static long formatDateTimeTimestamp(String dateTime) {
        Date date = formatDateTimeDate(dateTime);
        return date.getTime();
    }

    // format date time
    public static Date formatDateTimeDate(String dateTime) {
        String pattern = "dd-MM-yyyy hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static List<DrivingSession> getAllDrivingSessions(User user)
    {
        return DrivingSession.find(DrivingSession.class, "user = ?", "" + user.getId());
    }

    public static List<DrivingSession> getAllDrivingSessionsTimePeriod(User user, Date start, Date end)
    {
        String [] whereArgs = {String.valueOf(user.getId()), String.valueOf(start.getTime()), String.valueOf(end.getTime())};
        return DrivingSession.find(DrivingSession.class, "user = ? and date_timestart >= ? and date_timeend < ? ", whereArgs);
    }

    public static int getWeatherConditionPercentageTimePeriod(User user, Date start, Date end, int weather_condition) {
        String [] whereArgs = {String.valueOf(user.getId()), String.valueOf(start.getTime()), String.valueOf(end.getTime())};
        List <DrivingSession> drivingSessions = DrivingSession.find(DrivingSession.class, "user = ? and date_timestart >= ? and date_timeend < ?", whereArgs);
        if(drivingSessions.isEmpty()) return 0;
        int sum = 0, sumForWeather = 0;
        for(DrivingSession session: drivingSessions) {
            if(session.weather == weather_condition) sumForWeather += session.getDistance();
            sum += session.getDistance();
        }
        return 100 * sumForWeather / sum;
    }

    public static int getStreetConditionPercentageTimePeriod(User user, Date start, Date end, int street_condition)
    {
        String [] whereArgs = {String.valueOf(user.getId()), String.valueOf(start.getTime()), String.valueOf(end.getTime())};
        List <DrivingSession> drivingSessions = DrivingSession.find(DrivingSession.class, "user = ? and date_timestart >= ? and date_timeend < ?", whereArgs);
        if(drivingSessions.isEmpty()) return 0;
        int sum = 0, sumForStreetCondition = 0;
        for(DrivingSession session: drivingSessions) {
            if(session.street_condition == street_condition) sumForStreetCondition += session.getDistance();
            sum += session.getDistance();
        }
        return 100 * sumForStreetCondition / sum;
    }

}
