package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

/**
 * Created by Rutter on 23.03.2018.
 */

public class User extends SugarRecord {

    //int id;
    String name;
    float driven_km;
    float goal_km;


    public int getDriven_km() {
        return (int) driven_km;
    }

    public int getGoal_km() {
        return (int) goal_km;
    }

    boolean deleted;


    public User(){
    }

    public User( String name, float driven_km, float goal_km) {

        this.name = name;
        this.driven_km = driven_km;
        this.goal_km = goal_km;
    }




}
