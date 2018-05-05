package com.thestreetcodecompany.roady.classes;

import android.content.Context;

import com.orm.SchemaGenerator;
import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.thestreetcodecompany.roady.R;
import com.thestreetcodecompany.roady.classes.model.Achievement;
import com.thestreetcodecompany.roady.classes.model.Car;
import com.thestreetcodecompany.roady.classes.model.CoDriver;
import com.thestreetcodecompany.roady.classes.model.Coordinate;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Rutter on 23.03.2018.
 * Last changed by Schauberger on 24.04.2018
 */


public class DBHandler extends SugarApp {

    public DBHandler() {}

    public void makeDB()
    {
        /*User user = new User();
        user.save();
        Achievement achievement = new Achievement();
        achievement.save();
        Car car = new Car();
        car.save();
        CoDriver cd = new CoDriver();
        cd.save();
        Coordinate cord = new Coordinate();
        cord.save();
        DrivingSession ds = new DrivingSession();
        ds.save();*/

    }

    public void makeTestData()
    {
        User user = new User("Karl Heinz", 67, 1000);
        user.save();

        Date date = new Date();

        Achievement a1 = new Achievement("Rain", "Drive while it's raining", 0, 0, R.drawable.ic_rain, date, user);
        a1.save();
        Achievement a2 = new Achievement("Snow", "Drive while it's snowing", 1, 0, R.drawable.ic_snow, "", user);
        a2.save();
        Achievement a3 = new Achievement("Ice", "Drive while the road is icy", 2, 0, R.drawable.ic_ice, "", user);
        a3.save();
        Achievement a4 = new Achievement("Night", "Dive at night", 3, 0, R.drawable.ic_night, "", user);
        a4.save();

        Achievement a5 = new Achievement("Came a long way (Bronze)", "Drive 1000 km", 4, 1000, R.drawable.ic_distance, date, user);
        a5.save();
        Achievement a6 = new Achievement("Came a long way (Silver)", "Drive 2000 km", 4, 2000, R.drawable.ic_distance, "", user);
        a6.save();
        Achievement a7 = new Achievement("Came a long way (Gold)", "Drive 3000 km", 4, 3000, R.drawable.ic_distance, "", user);
        a7.save();
        Achievement a8 = new Achievement("Came a long way (Platinum)", "Drive 5000 km", 4, 5000, R.drawable.ic_distance, "", user);
        a8.save();

        Achievement a9 = new Achievement("Everyday is driving day (Bronze)", "Drive 2 days in a row", 5, 2, R.drawable.ic_streak, "", user);
        a9.save();
        Achievement a10 = new Achievement("Everyday is driving day (Silver)", "Drive 3 days in a row", 5, 3, R.drawable.ic_streak, "", user);
        a10.save();
        Achievement a11 = new Achievement("Everyday is driving day (Gold)", "Drive 4 days in a row", 5, 4, R.drawable.ic_streak, "", user);
        a11.save();
        Achievement a12 = new Achievement("Everyday is driving day (Platinum)", "Drive 7 days in a row", 5, 7, R.drawable.ic_streak, "", user);
        a12.save();

        Achievement a13 = new Achievement("All day long (Bronze)", "Drive 1h", 6, 1, R.drawable.ic_stop_watch, date, user);
        a13.save();
        Achievement a14 = new Achievement("All day long (Silver)", "Drive 2h", 6, 2, R.drawable.ic_stop_watch, date, user);
        a14.save();
        Achievement a15 = new Achievement("All day long (Gold)", "Drive 3h", 6, 3, R.drawable.ic_stop_watch, "", user);
        a15.save();
        Achievement a16 = new Achievement("All day long (Platinum)", "Drive 5h", 6, 5, R.drawable.ic_stop_watch, "", user);
        a16.save();

        Achievement a17 = new Achievement("Fast & Furious", "You are the street king", 7, 1, R.drawable.ic_stars, "", user);
        a17.save();
        Achievement a18 = new Achievement("2 Fast & 2 Furious", "Not a king, a true god", 7, 2, R.drawable.ic_stars, "", user);
        a18.save();

        Car c1 = new Car("Bugatti","GU-123YEAH", user);
        c1.save();
        Car c2 = new Car("Audi A6", "G-AUDI1", user);
        c2.save();
        Car c3 = new Car("VW Golf", "SL-234KK", user);
        c3.save();

        CoDriver cd = new CoDriver("Carlos", user);
        cd.save();

        DrivingSession ds = new DrivingSession(true, "12-12-2012 05:21:12", 2099, user);
        ds.save();

        Coordinate cord = new Coordinate(1, 39.2300F, 15.223F, ds);
        cord.save();
    }

    public User getTestUser() {

        User.deleteAll(User.class);
        List<User> users = User.listAll(User.class);

        if(users.size() <= 0)
        {
            makeTestData();
            users = User.listAll(User.class);
        }

        return users.get(0);
    }


    public CoDriver getTestCoDriver() {

        List<CoDriver> coDrivers = CoDriver.listAll(CoDriver.class);

        if(coDrivers.size() <= 0)
        {
            makeTestData();
            coDrivers = CoDriver.listAll(CoDriver.class);
        }

        return coDrivers.get(coDrivers.size() - 1);
    }



    public List<DrivingSession> getAllDrivingSessions(User user)
    {
        return DrivingSession.find(DrivingSession.class, "user = ?", "" + user.getId());
    }

//    public List<Car> getAllCars(User user)
//    {
//        System.out.println("in get all cars ");
//        //return Car.find(Car.class, "user = ?", "" + user.getId());
//         return Car.findWithQuery(Car.class, "Select * from Car where user = ?", user.getName());
//    }

/*
    public List<Car> getCarsByUser(User user) {
        List<Car> cars = Car.find(Car.class, "user = ?", "" + user.getId());

        if (cars.size() <= 0) {
            makeTestData();
            cars = Car.listAll(Car.class);
        }

        return cars;
    }
    public List<CoDriver> getAllCoDrivers() {
        List<CoDriver> coDrivers = CoDriver.listAll(CoDriver.class);

        if (coDrivers.size() <= 0) {
            makeTestData();
            coDrivers = CoDriver.listAll(CoDriver.class);
        }

        return coDrivers;
    }
*/




}