package com.var.bloodflow.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable {
    private   String name,uname,dob,phno,bldgrp,gender,password;
    public Users(String name , String uname, String dob, String phno, String bldgrp, String gender, String password) {
        this.name = name;
        this.uname = uname;
        this.dob = dob;
        this.phno = phno;
        this.bldgrp = bldgrp;
        this.gender = gender;
        this.password = password;
    }

    public Users(){

    }


    protected Users(Parcel in) {
        name = in.readString();
        uname = in.readString();
        dob = in.readString();
        phno = in.readString();
        gender = in.readString();
        password = in.readString();
    }

    public static final Parcelable.Creator<Users> CREATOR = new Parcelable.Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };


    public String getName() { return  name; }
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


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + uname + '\'' +
                ", date_of_birth='" + dob + '\'' +
                ", phone_number='" + phno + '\'' +
                ", blood_group='" + bldgrp + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(uname);
        dest.writeString(dob);
        dest.writeString(phno);
        dest.writeString(bldgrp);
        dest.writeString(gender);
        dest.writeString(password);
    }

}