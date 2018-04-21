package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

/**
 * Created by Rutter on 23.03.2018.
 */

public class Achievement extends SugarRecord {
    //id
    String name;
    int type;
    float km;
    String imageLink;
    boolean reached;
    User user;
    boolean deleted;

    public Achievement() {}

    public Achievement(String name, int type, float km, String imageLink, boolean reached, User user) {
        this.name = name;
        this.type = type;
        this.km = km;
        this.imageLink = imageLink;
        this.reached = reached;
        this.user = user;
    }

    public String getName() {
        return name;
    }
}
