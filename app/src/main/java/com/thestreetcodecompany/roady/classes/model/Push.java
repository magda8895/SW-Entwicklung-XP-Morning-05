package com.thestreetcodecompany.roady.classes.model;

import android.util.Log;

import com.orm.SugarRecord;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Rutter on 23.03.2018.
 */

public class Push extends SugarRecord {



    //id
    private long dateTime;
    boolean deleted;
    public Push() {}
    public Push(long dateTime) {
        this.dateTime = dateTime;
    }

    public long getDateTime() {
        return dateTime;
    }
}
