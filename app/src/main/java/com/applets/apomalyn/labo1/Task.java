package com.applets.apomalyn.labo1;

import java.util.Date;

/**
 * Created by apomalyn on 06/02/18.
 */

public class Task {
    private int id;

    private String name;

    private boolean isCompleted = false;

    private Date completedDate = null;

    public Task(int id, String name, boolean isCompleted, Date completedDate) {
        this.id = id;
        this.name = name;
        this.isCompleted = isCompleted;
        this.completedDate = completedDate;
    }

    public Task(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public String toString(){
        return "";
    }
}
