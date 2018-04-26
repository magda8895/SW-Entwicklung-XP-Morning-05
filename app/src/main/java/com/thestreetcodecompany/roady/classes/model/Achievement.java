package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

/**
 * Created by Rutter on 23.03.2018.
 */

public class Achievement extends SugarRecord {
    //id
    private String title;
    private String description;
    private int type;
    private float value;
    private String imageLink;
    private boolean reached;
    private User user;
    boolean deleted;

    public Achievement() {}

    public Achievement(String title, String description, int type, float value, String imageLink, boolean reached, User user) {
        setTitle(title);
        setDescription(description);
        setType(type);
        setValue(value);
        setImage(imageLink);
        setReached(reached);
        setUser(user);
    }

    // getter
    public String getTitle() { return this.title; }

    public String getDescription() { return this.description; }

    public int getType() { return this.type; }

    public float getValue() { return this.value; }

    public String getImage() { return this.imageLink; }

    public boolean getReached() { return this.reached; }

    public User getUser() { return this.user; }


    // setter
    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setType(int type) { this.type = type; }

    public void setValue(float value) { this.value = value; }

    public void setImage(String imageLink) { this.imageLink = imageLink; }

    public void setReached(boolean reached) { this.reached = reached; }

    public void setUser(User user) { this.user = user; }
}
