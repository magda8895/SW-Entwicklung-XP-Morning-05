package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

/**
 * Created by Rutter on 23.03.2018.
 */

public class Car extends SugarRecord {

    //int id;
    String name;
    String kfz;
    User user;
    boolean deleted;

    public Car(){}

    public Car(String name, String kfz, User user) {
        this.name = name;
        this.kfz = kfz;
        this.user = user;
    }
}
