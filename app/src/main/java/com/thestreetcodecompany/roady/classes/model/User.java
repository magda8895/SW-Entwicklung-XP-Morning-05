package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

import java.util.List;
import java.util.logging.Logger;

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
        return driven_km;
    }

    public float getGoalKm() {
        return this.goal_km;
    }

    public void addDriven_km(float distance)
    {
        driven_km += distance;
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

    // additional
    public List<Car> getCars() {
        List<Car> cars = Car.find(Car.class, "user = ?", "" + getId());
        return cars;
    }

    public List<CoDriver> getCoDrivers() {
        List<CoDriver> coDrivers = CoDriver.find(CoDriver.class, "user = ?", "" + getId());
        return coDrivers;
    }

    public List<Achievement> getAchievements() {
        List<Achievement> achievements = Achievement.find(Achievement.class, "user = ?", "" + getId());
        return achievements;
    }

    public static User getTestUser() {

        List<User> users = User.listAll(User.class);

        if(users.size() <= 0)
        {
            new Exception();
        }

        return users.get(0);
    }



}
