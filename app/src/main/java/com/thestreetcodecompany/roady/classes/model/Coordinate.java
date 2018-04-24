package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

/**
 * Created by Rutter on 23.03.2018.
 * Last changed by Schauberger on 24.04.2018
 */

public class Coordinate extends SugarRecord {
    //id
    private String name;
    private int type;
    private float latitude;
    private float longitude;
    private DrivingSession drivingSession;
    boolean deleted;


    public Coordinate() {}

    public Coordinate(int type, float latitude, float longitude, DrivingSession drivingSession) {
        setType(type);
        setLatitude(latitude);
        setLongitude(longitude);
        setDrivingSession(drivingSession);
    }

    public Coordinate(String name, int type, float latitude, float longitude, DrivingSession drivingSession) {
        setName(name);
        setType(type);
        setLatitude(latitude);
        setLongitude(longitude);
        setDrivingSession(drivingSession);
    }


    // toString
    public String toString() {
        return "name: " + getName() + ", type: " + getType() + ", latitude: " + getLatitude() + ", longitude: " + getLongitude() + ", drivingSession: " + drivingSession.getName();
    }


    // getter
    public String getName() {
        return this.name;
    }

    public int getType() {
        return this.type;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public DrivingSession getDrivingSession() {
        return drivingSession;
    }


    // setter
    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setDrivingSession(DrivingSession drivingSession) {
        this.drivingSession = drivingSession;
    }
}
