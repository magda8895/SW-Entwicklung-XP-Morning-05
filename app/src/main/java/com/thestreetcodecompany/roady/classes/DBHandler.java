package com.thestreetcodecompany.roady.classes;

import android.util.Log;
import android.content.Context;
import android.database.DatabaseUtils;
import android.support.annotation.NonNull;

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
import com.thestreetcodecompany.roady.classes.model.FileHistory;
import com.thestreetcodecompany.roady.classes.model.Push;
import com.thestreetcodecompany.roady.classes.model.User;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.thestreetcodecompany.roady.classes.Helper.MakePush;
import static com.thestreetcodecompany.roady.classes.model.DrivingSession.formatDateTimeTimestamp;

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

    public User makeTestDataForExport()
    {
        User user = new User("Export User", 88, 1000);
        user.save();

        Car c1 = new Car("Bugatti","GU-123YEAH", user);
        c1.save();

        CoDriver coDriver = new CoDriver("Export CoDriver", user);


        DrivingSession ds = new DrivingSession("Graz - Feldkirchen", (new Date()).getTime(), (new Date()).getTime(), c1.getName(), coDriver.getName(), 0f, 350f, 0, 0, user);
        ds.save();
        ds = new DrivingSession("Feldkirchen - Villach", (new Date()).getTime(), (new Date()).getTime(), c1.getName(), coDriver.getName(), 350f, 370f, 1, 1, user);
        ds.save();

        return user;
    }

    public void makeTestData()
    {
        RoadyData rd = RoadyData.getInstance();
        Date date = new Date();

        User user = new User("Export User", 88, 1000);
        user.save();

        Car c1 = new Car("Bugatti","GU-123YEAH", rd.user);
        c1.save();
        Car c2 = new Car("Audi A6", "G-AUDI1", rd.user);
        c2.save();
        Car c3 = new Car("VW Golf", "SL-234KK", rd.user);
        c3.save();
        CoDriver cd = new CoDriver("Carlos", rd.user);
        cd.save();

        //Driving sessions
        DrivingSession ds = new DrivingSession("Wien-Graz",formatDateTimeTimestamp("30-10-2018 05:21:10"),formatDateTimeTimestamp("12-12-2012 05:21:10"),c3.getName(),cd.getName(),209f,300f,3,3,rd.user);

        ds.save();
        ds = new DrivingSession("Salzburg-Graz",formatDateTimeTimestamp("15-11-2017 05:21:10"),formatDateTimeTimestamp("12-12-2012 05:21:10"),c3.getName(),cd.getName(),300f,400f,2,3,rd.user);
        ds.save();
        ds = new DrivingSession("Moskau-Graz",formatDateTimeTimestamp("01-01-2018 05:21:10"),formatDateTimeTimestamp("12-12-2012 05:21:10"),c2.getName(),cd.getName(),400f,450f,0,3,rd.user);
        ds.save();
        ds = new DrivingSession("London-Liverpool",formatDateTimeTimestamp("05-16-2015 05:21:10"),formatDateTimeTimestamp("12-12-2012 05:21:10"),c1.getName(),cd.getName(),450f,600f,1,3,rd.user);
        ds.save();
        //ds = new DrivingSession("London-Liverpool",formatDateTimeTimestamp("05-16-2015 05:21:10"),formatDateTimeTimestamp("12-12-2012 05:21:10"),null,cd.getName(),450f,600f,1,3,rd.user);
        //ds.save();

        //ds = new DrivingSession(true, (new Date()).toString(), 400f, rd.user);
        //ds.save();

        String fileHistory = "File: Test.csv\nCreated on: " + new Date();
        FileHistory fh = new FileHistory(fileHistory, rd.user);
        fh.save();
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

    public void createAchievements()
    {
        RoadyData rd = RoadyData.getInstance();

        Date date = new Date();
        Achievement ac = new Achievement("Rain", "Drive while it's raining", 0, 0, R.drawable.ic_rain, "", rd.user);
        ac.save();
        ac = new Achievement("Snow", "Drive while it's snowing", 1, 0, R.drawable.ic_snow, "", rd.user);
        ac.save();
        ac = new Achievement("Ice", "Drive while the road is icy", 2, 0, R.drawable.ic_ice, "", rd.user);
        ac.save();
        ac = new Achievement("Night", "Dive at night", 3, 0, R.drawable.ic_night, "", rd.user);
        ac.save();

        ac = new Achievement("Everyday is driving day (Bronze)", "Drive 2 days in a row", 4, 2, R.drawable.ic_streak_bronze, "", rd.user);
        ac.save();
        ac = new Achievement("Everyday is driving day (Silver)", "Drive 3 days in a row", 4, 3, R.drawable.ic_streak_silver, "", rd.user);
        ac.save();
        ac = new Achievement("Everyday is driving day (Gold)", "Drive 4 days in a row", 4, 4, R.drawable.ic_streak_gold, "", rd.user);
        ac.save();
        ac = new Achievement("Everyday is driving day (Platinum)", "Drive 7 days in a row", 4, 7, R.drawable.ic_streak_platinum, "", rd.user);
        ac.save();

        ac = new Achievement("Came a long way (Bronze)", "Drive 1000 km", 5, 1000, R.drawable.ic_distance_bronze, "", rd.user);
        ac.save();
        ac = new Achievement("Came a long way (Silver)", "Drive 2000 km", 5, 2000, R.drawable.ic_distance_silver, "", rd.user);
        ac.save();
        ac = new Achievement("Came a long way (Gold)", "Drive 3000 km", 5, 3000, R.drawable.ic_distance_gold, "", rd.user);
        ac.save();
        ac = new Achievement("Came a long way (Platinum)", "Drive 5000 km", 5, 5000, R.drawable.ic_distance_platinum, "", rd.user);
        ac.save();

        ac = new Achievement("All day long (Bronze)", "Drive 1h", 6, 1, R.drawable.ic_time_bronze, "", rd.user);
        ac.save();
        ac = new Achievement("All day long (Silver)", "Drive 2h", 6, 2, R.drawable.ic_time_silver, "", rd.user);
        ac.save();
        ac = new Achievement("All day long (Gold)", "Drive 3h", 6, 3, R.drawable.ic_time_gold, "", rd.user);
        ac.save();
        ac = new Achievement("All day long (Platinum)", "Drive 5h", 6, 5, R.drawable.ic_time_platinum, "", rd.user);
        ac.save();

        ac = new Achievement("Fast & Furious", "You are the street king", 7, 1, R.drawable.ic_fast_and_furious, "", rd.user);
        ac.save();
        ac = new Achievement("2 Fast & 2 Furious", "Not a king, a true god", 7, 2, R.drawable.ic_fast_and_furious, "", rd.user);
        ac.save();

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

    /*public User getTestUser() {

        User.deleteAll(User.class);
        List<User> users = User.listAll(User.class);

        if(users.size() <= 0)
        {
            //makeTestData();
            users = User.listAll(User.class);
        }

        return users.get(0);
    }*/


    public CoDriver getTestCoDriver() {

        List<CoDriver> coDrivers = CoDriver.listAll(CoDriver.class);

        if(coDrivers.size() <= 0)
        {
            //makeTestData();
            coDrivers = CoDriver.listAll(CoDriver.class);
        }

        return coDrivers.get(coDrivers.size() - 1);
    }

    public List<DrivingSession> getAllDrivingSessions(User user)
    {
        return DrivingSession.findWithQuery(DrivingSession.class, "SELECT * FROM driving_session ORDER BY date_timestart DESC");
    }

    public List<DrivingSession> getAllDrivingSessionsTimePeriod(User user, Date start, Date end)
    {
        String [] whereArgs = {String.valueOf(user.getId()), String.valueOf(start.getTime()), String.valueOf(end.getTime())};
        return DrivingSession.find(DrivingSession.class, "user = ? and dateTime_start >= ? and dateTime_end < ? ", whereArgs);
    }


//    public List<Car> getAllCars(User user)
//    {
//        System.out.println("in get all cars ");
//        //return Car.find(Car.class, "user = ?", "" + user.getId());
//         return Car.findWithQuery(Car.class, "Select * from Car where user = ?", user.getName());
//    }


    //Logs all data from table CoDrivers
    //you can dublicate this funciton and modify it for other tables
    public void logAllCoDrivers()
    {
        List<CoDriver> list = CoDriver.find(CoDriver.class,null);

        for (CoDriver cod : list) {
            String msg = "id: " + cod.getId() + " | name: " + cod.getName() + " | ";

            //user can be null (shouldn't be)
            if (cod.getUser() != null) {
                msg += "user: " + cod.getUser().getName() + "(id: " + cod.getUser().getId() + ")";
            } else {
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