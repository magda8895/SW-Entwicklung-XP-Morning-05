package com.thestreetcodecompany.roady.classes.model;

import com.orm.SugarRecord;

public class FileHistory extends SugarRecord {

    private String history;
    private User user;

    public FileHistory() {}

    public FileHistory(String history, User user){
        setHistory(history);
        setUser(user);
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
