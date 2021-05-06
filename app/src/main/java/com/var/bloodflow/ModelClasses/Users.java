package com.var.bloodflow.ModelClasses;

import android.os.Parcel;

public class Users {

    private String user_id, name, uname, dob, phno, bldgrp, gender, password, image, place;

    public Users(String user_id, String name, String uname, String dob, String phno, String bldgrp, String gender, String password, String image, String place) {
        this.user_id = user_id;
        this.name = name;
        this.uname = uname;
        this.dob = dob;
        this.phno = phno;
        this.bldgrp = bldgrp;
        this.gender = gender;
        this.password = password;
        this.image = image;
        this.place = place;
    }


    public Users() {

    }

    protected Users(Parcel in) {
        user_id = in.readString();
        name = in.readString();
        uname = in.readString();
        dob = in.readString();
        phno = in.readString();
        gender = in.readString();
        password = in.readString();
        image = in.readString();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getBldgrp() {
        return bldgrp;
    }

    public void setBldgrp(String bldgrp) {
        this.bldgrp = bldgrp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
