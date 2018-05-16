package com.thestreetcodecompany.roady.classes;

import android.content.Context;
import android.util.Log;

import com.orm.SchemaGenerator;
import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.thestreetcodecompany.roady.classes.model.Achievement;
import com.thestreetcodecompany.roady.classes.model.Car;
import com.thestreetcodecompany.roady.classes.model.CoDriver;
import com.thestreetcodecompany.roady.classes.model.Coordinate;
import com.thestreetcodecompany.roady.classes.model.DrivingSession;
import com.thestreetcodecompany.roady.classes.model.Push;
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.thestreetcodecompany.roady.classes.Helper.MakePush;
import static com.thestreetcodecompany.roady.classes.Helper.MakeSnackbar;
import static com.thestreetcodecompany.roady.classes.Helper.MakeToast;

/**
 * Created by Rutter on 23.03.2018.
 * Last changed by Schauberger on 24.04.2018
 */


public class DBHandler extends SugarApp {

    public DBHandler() {
        //makeDB();
    }

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
        Achievement a = new Achievement("king",1,500,"/",false, user);
        a.save();
        Car c = new Car("Bugatti","GU-123YEAH", user);
        c.save();
        Car c2 = new Car("Audi A6", "G-AUDI1", user);
        c2.save();
        Car c3 = new Car("VW Golf", "SL-234KK", user);
        c3.save();
        CoDriver cd = new CoDriver("Carlos", user);
        cd.save();
        //Driving sessions
        DrivingSession ds = new DrivingSession(true, "12-12-2012 05:21:12", 2099, user);
        ds.save();
        ds = new DrivingSession(true, "12-12-2012 05:21:12", 123, user);
        ds.save();
        ds = new DrivingSession(true, "13-12-2012 05:21:12", 203, user);
        ds.save();
        ds = new DrivingSession(true, "14-12-2012 05:21:12", 200, user);
        ds.save();
        ds = new DrivingSession(true, "15-12-2012 05:21:12", 20, user);
        ds.save();
        ds = new DrivingSession(true, "16-12-2012 05:21:12", 99, user);
        ds.save();

        Push p = new Push(Calendar.getInstance().getTimeInMillis());
        p.save();

        Coordinate cord = new Coordinate(1, 39.2300F, 15.223F, ds);
        cord.save();
    }


    public long getTimeSinceLastPush()
    {
        List<Push> list = Push.find(Push.class, "");

        if(list.size() > 0)
        {
            Push last = list.get(list.size() - 1);
            long currentTime = Calendar.getInstance().getTimeInMillis();
            return currentTime - last.getDateTime();
        }

        Log.d("PUSH", "There are no pushes yet");
        return 0;


    }

    public User getUser() {
        List<User> users = User.listAll(User.class);

        if(users.size() <= 0)
        {
            return null;
        }
        else if (users.size() >= 2)
        {
            Log.d("DBHandler","There is more than one user...");
        }
        return users.get(0);
    }

    public User getTestUser() {

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

    public List<DrivingSession> getAllDrivingSessionsTimePeriod(User user, Date start, Date end)
    {
        String [] whereArgs = {String.valueOf(user.getId()), String.valueOf(start.getTime()), String.valueOf(end.getTime())};
        return DrivingSession.find(DrivingSession.class, "user = ? and dateTime_start >= ? and dateTime_end < ? ", whereArgs);
    }


    //Logs all data from table CoDrivers
    //you can dublicate this funciton and modify it for other tables
    public void logAllCoDrivers()
    {
        List<CoDriver> list = CoDriver.find(CoDriver.class,null);

        for (CoDriver cod : list) {
            String msg = "id: " + cod.getId() + " | name: " + cod.getName() + " | ";

            //user can be null (shouldn't be)
            if(cod.getUser() != null)
            {
                msg +=  "user: " + cod.getUser().getName() + "(id: "+ cod.getUser().getId() + ")";
            }
            else
            {
                msg += "no user";
            }
            Log.d("CoDriver", msg);

        }
    }

    //Logs all data from table CoDrivers
    //you can dublicate this funciton and modify it for other tables
    public void logAllUsers()
    {
        List<User> list = User.find(User.class,null);
        Log.d("DBHandler","Users:");
        for (User user : list) {
            String msg = "id: " + user.getId() + " | " +
                         "name: " + user.getName() + " | " +
                         "driven_km: " + user.getDrivenKm() + " | " +
                         "goal_km: " + user.getGoalKm();

            Log.d("DBHandler", msg);


        }
    }

    public void logAllDrivingSessions()
    {
        List<DrivingSession> list = DrivingSession.find(DrivingSession.class,null);
        Log.d("DBHandler","Driving Sessions:");
        if(list.size() == 0)
        {
            Log.d("DBHandler","there is nothing to show");
        }

        for (DrivingSession item : list) {
            String msg = "id: " + item.getId() + " | name: " + item.getName() + " | active: " + item.getActive();

            //user can be null (shouldn't be)
            if(item.getUser() != null)
            {
                msg +=  "user: " + item.getUser().getName() + "(id: "+ item.getUser().getId() + ")";
            }
            else
            {
                msg += "no user";
            }
            Log.d("DBHandler", msg);
        }
    }
}