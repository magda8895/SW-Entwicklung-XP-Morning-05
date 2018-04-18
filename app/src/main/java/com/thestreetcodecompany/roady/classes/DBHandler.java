package com.thestreetcodecompany.roady.classes;

import android.content.Context;

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
import com.thestreetcodecompany.roady.classes.model.User;

import java.util.List;

/**
 * Created by Rutter on 23.03.2018.
 */


public class DBHandler extends SugarApp {

    public DBHandler(){}

    public void makeDB()
    {
        User user = new User();
        user.save();
        Achievement a = new Achievement();
        user.save();
        Car c = new Car();
        user.save();
        CoDriver cd = new CoDriver();
        cd.save();
        Coordinate cord = new Coordinate();
        cord.save();
        DrivingSession ds = new DrivingSession();
        ds.save();

    }

    public void makeTestData()
    {
        User user = new User("Karl Heinz", 14,1000);
        user.save();
        Achievement a = new Achievement("king",1,500,"/",false,user);
        user.save();
        Car c = new Car("Bugatti","GU-123YEAH",user);
        user.save();
        CoDriver cd = new CoDriver("Carlos",user);
        cd.save();
        DrivingSession ds = new DrivingSession(true,"12-12-2012 05:21:12",2099,user);
        ds.save();
        Coordinate cord = new Coordinate(1,39.2300F ,15.223F ,ds);
        cord.save();
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

    public List<DrivingSession> getAllDrivingSessions(User user)
    {
       return DrivingSession.find(DrivingSession.class, "user = ?", "" + user.getId());
    }


    /* vorher: DBBackend
        DBHandler dbh = new DBHandler();
        dbh.makeDB();
        dbh.makeTestData();

        String pattern = "dd-mm-yyyy hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = format.parse("12-12-2012 05:21:12");
            System.out.println("Date is: "+date);
            MakeToast(date.toString(),getApplicationContext());
        } catch (ParseException e) {
            e.printStackTrace();
        }
     */
}
