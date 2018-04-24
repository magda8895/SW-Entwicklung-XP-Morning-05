package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

/**
 * Created by Rutter on 23.03.2018.
 */

public class Coordinate extends SugarRecord {
    //id
    String name;
    int type;
    float latitude;
    float longitude;
    DrivingSession drivingSession;
    boolean deleted;

    public Coordinate() {}

    public Coordinate(int type, float latitude, float longitude, DrivingSession drivingSession) {
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.drivingSession = drivingSession;
    }

    public Coordinate(String name, int type, float latitude, float longitude, DrivingSession drivingSession) {
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.drivingSession = drivingSession;
    }
}
