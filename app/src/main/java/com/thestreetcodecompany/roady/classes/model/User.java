package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Rutter on 23.03.2018.
 */

public class User extends SugarRecord{

    //int id;
    String name;
    float driven_km;
    float goal_km;
    boolean deleted;


    public User(){
    }

    public User( String name, float driven_km, float goal_km) {

        this.name = name;
        this.driven_km = driven_km;
        this.goal_km = goal_km;
    }

    public String getName() {
        return name;
    }
    public int getDriven_km() {
        return (int) driven_km;
    }

    public int getGoal_km() {
        return (int) goal_km;
    }

    public List<Car> getAllCars() {
                List<Car> cars = Car.find(Car.class, "user = ?", this.getId().toString());
                return cars;
    }

    public List<CoDriver> getAllCoDrivers() {
        return CoDriver.find(CoDriver.class, "user = ?", this.getId().toString());
    }

    public List<Achievement> getAllAchievments() {
        return Achievement.find(Achievement.class, "user = ?", this.getId().toString());
    }

}
