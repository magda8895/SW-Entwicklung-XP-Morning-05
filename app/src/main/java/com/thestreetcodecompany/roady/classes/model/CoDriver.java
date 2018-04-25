package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

/**
 * Created by Rutter on 23.03.2018.
 * Last changed by Schauberger on 24.04.2018
 */

public class CoDriver extends SugarRecord {
    //id
    private String name;
    private User user;
    boolean deleted;

    public CoDriver() {
    }

    public CoDriver(String name, User user) {
        setName(name);
        setUser(user);
    }


    // toString
    public String toString() {
        return "name: " + getName() + ", user: " + getUser().getName();
    }


    // getter
    public String getName() {
        return this.name;
    }

    public User getUser() {
        return this.user;
    }


    // setter
    public void setName(String name) {
        this.name = name;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
