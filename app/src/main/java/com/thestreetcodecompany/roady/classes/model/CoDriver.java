package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

/**
 * Created by Rutter on 23.03.2018.
 */

public class CoDriver extends SugarRecord {
    //id
    String name;
    User user;
    boolean deleted;

    public CoDriver() {
    }

    public CoDriver(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
