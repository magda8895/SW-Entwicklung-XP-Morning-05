package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rutter on 23.03.2018.
 * Last changed by Schauberger on 26.04.2018
 */

public class Achievement extends SugarRecord {
    //id
    private String title;
    private String description;
    private int type;
    private float value;
    private int imageLink;
    private String reached;
    private User user;
    boolean deleted;


    public Achievement() {}

    public Achievement(String title, String description, int type, float value, int imageLink, String reached, User user) {
        setTitle(title);
        setDescription(description);
        setType(type);
        setValue(value);
        setImage(imageLink);
        setReached(reached);
        setUser(user);
    }

    public Achievement(String title, String description, int type, float value, int imageLink, Date reached, User user) {
        setTitle(title);
        setDescription(description);
        setType(type);
        setValue(value);
        setImage(imageLink);
        setReachedDate(reached);
        setUser(user);
    }


    // getter
    public String getTitle() { return this.title; }

    public String getDescription() { return this.description; }

    public int getType() { return this.type; }

    public float getValue() { return this.value; }

    public int getImage() { return this.imageLink; }

    public String getReachedString() { return this.reached; }

    public String getReachedStringFormated() {
        String pattern = "dd. MMM yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return (String) format.format(getReachedDate());
    }

    public Date getReachedDate() {
        String pattern = "dd-MM-yyyy hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = new Date();

        try {
            date = format.parse(getReachedString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public boolean getReached() {
        if (getReachedString().isEmpty() || getReachedString() == null) {
            return false;
        } else {
            return true;
        }
    }

    public User getUser() { return this.user; }


    // setter
    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setType(int type) { this.type = type; }

    public void setValue(float value) { this.value = value; }

    public void setImage(int imageLink) { this.imageLink = imageLink; }

    public void setReached(String reached) { this.reached = reached; }

    public void setReachedDate(Date reached) {
        String pattern = "dd-MM-yyyy hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        this.reached = format.format(reached);
    }

    public void setUser(User user) { this.user = user; }
}
