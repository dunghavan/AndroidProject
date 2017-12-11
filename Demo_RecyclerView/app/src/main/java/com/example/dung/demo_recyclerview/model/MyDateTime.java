package com.example.dung.demo_recyclerview.model;

/**
 * Created by Dung on 12/10/2017.
 */

public class MyDateTime {
    private String date;
    private String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String toString(){
        if(date == null && time == null)
            return "";
        return date + "T" + time;
    }
}
