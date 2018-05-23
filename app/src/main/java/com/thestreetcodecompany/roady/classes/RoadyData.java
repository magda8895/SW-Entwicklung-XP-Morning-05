package com.thestreetcodecompany.roady.classes;

import com.thestreetcodecompany.roady.classes.model.User;

/**
 * Created by Rutter on 05.05.2018.
 */

public class RoadyData {
    private static final RoadyData ourInstance = new RoadyData();
    public User user;

    public static RoadyData getInstance() {
        return ourInstance;
    }

    public User getUser()
    {
        return this.user;
    }


    private RoadyData() {

    }
}
