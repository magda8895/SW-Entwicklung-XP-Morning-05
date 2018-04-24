package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

/**
 * Created by Rutter on 23.03.2018.
 * Last changed by Schauberger on 24.04.2018
 */

public class Car extends SugarRecord {

    //int id;
    private String name;
    private String kfz;
    private User user;
    boolean deleted;

    public Car() {}

    public Car(String name, String kfz, User user) {
        setName(name);
        setKfz(kfz);
        setUser(user);
    }


    // toString
    public String toString() {
        return "name: " + getName() + ", kfz: " + getKfz() + ", user: " + getUser().getName();
    }


    // getter
    public String getName() {
        return this.name;
    }

    public String getKfz() {
        return this.kfz;
    }

    public User getUser() {
        return this.user;
    }


    // setter
    public void setName(String name) {
        this.name = name;
    }

    public void setKfz(String kfz) {
        this.kfz = kfz;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
