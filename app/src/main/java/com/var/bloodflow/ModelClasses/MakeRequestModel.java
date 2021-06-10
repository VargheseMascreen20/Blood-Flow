package com.var.bloodflow.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class MakeRequestModel {

    private String user_id;
    private String patientName;
    private String hospitaltName;
    private String blood_group;
    private String required_units;
    private String op_number;
    private String date;
    private String city;

    public MakeRequestModel(String user_id, String patientName, String hospitaltName, String blood_group, String required_units, String op_number, String date, String city) {
        this.user_id = user_id;
        this.patientName = patientName;
        this.hospitaltName = hospitaltName;
        this.blood_group = blood_group;
        this.required_units = required_units;
        this.op_number = op_number;
        this.date = date;
        this.city = city;
    }

    public MakeRequestModel() {

    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getHospitaltName() {
        return hospitaltName;
    }

    public void setHospitaltName(String hospitaltName) {
        this.hospitaltName = hospitaltName;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getRequired_units() {
        return required_units;
    }

    public void setRequired_units(String required_units) {
        this.required_units = required_units;
    }

    public String getOp_number() {
        return op_number;
    }

    public void setOp_number(String op_number) {
        this.op_number = op_number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
