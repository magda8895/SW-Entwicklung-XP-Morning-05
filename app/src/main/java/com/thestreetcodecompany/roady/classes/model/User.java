package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Rutter on 23.03.2018.
 * Last changed by Schauberger on 24.04.2018
 */

public class User extends SugarRecord {

    //int id;
    private String name;
    private float driven_km;
    private float goal_km;
    boolean deleted;


    public User() {}

    public User(String name, float driven_km, float goal_km) {
        setName(name);
        setDrivenKm(driven_km);
        setGoalKm(goal_km);
    }


    // toString
    public String toString() {
        return "name: " + getName() + ", driven_km: " + getDrivenKm() + ", goal_km: " + getGoalKm();
    }


    // getter
    public String getName() {
        return this.name;
    }

    public float getDrivenKm() {
        return this.driven_km;
    }

    public float getGoalKm() {
        return this.goal_km;
    }


    // setter
    public void setName(String name) {
        this.name = name;
    }

    public void setDrivenKm(float driven_km) {
        this.driven_km = driven_km;
    }

    public void setGoalKm(float goal_km) {
        this.goal_km = goal_km;
    }

    public String getName() {
        return name;
    }
    public int getDriven_km() {
        return (int) driven_km;
    }

    // do not use - will be deleted
    public int getDriven_km() {
        return (int) driven_km;
    }

    public int getGoal_km() {
        return (int) goal_km;
    }
}
