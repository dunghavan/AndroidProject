package com.example.dung.demo_recyclerview.model;

import android.hardware.camera2.params.Face;

import com.facebook.FacebookBroadcastReceiver;

/**
 * Created by Dung on 12/16/2017.
 */

public class Facebook_Profile {
    private String name = "";
    private String id;
    private String gender;
    private String birthday;
    private String email;
    private String user_mobile_phone;

    public Facebook_Profile(){
        this.name = "";
        this.id = "";
        this.gender = "";
        this.birthday = "";
        this.email = "";
        this.user_mobile_phone = "";
    }

    public String getName() {
        return " " + name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_mobile_phone() {
        return user_mobile_phone;
    }

    public void setUser_mobile_phone(String user_mobile_phone) {
        this.user_mobile_phone = user_mobile_phone;
    }
}
