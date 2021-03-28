package com.var.bloodflow;

public class Users {
    String name,uname,dob,phno,bldgrp,gender,password;

    public Users(String name , String uname, String dob, String phno, String bldgrp, String gender, String password) {
        this.name = name;
        this.uname = uname;
        this.dob = dob;
        this.phno = phno;
        this.bldgrp = bldgrp;
        this.gender = gender;
        this.password = password;
    }


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

}
