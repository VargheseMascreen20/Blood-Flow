package com.var.bloodflow.ModelClasses;

public class AcceptedRequestsModel {
    String opNumber, donorID, recieverID;
    private String patientName;
    private String hospitaltName;
    private String blood_group;
    private String required_units;
    private String date;
    private String city;


    public AcceptedRequestsModel(String opNumber, String donorID, String recieverID, String patientName, String hospitaltName, String blood_group, String required_units, String date, String city) {
        this.donorID = donorID;
        this.recieverID = recieverID;
        this.opNumber = opNumber;
        this.patientName = patientName;
        this.hospitaltName = hospitaltName;
        this.blood_group = blood_group;
        this.required_units = required_units;
        this.date = date;
        this.city = city;
    }

    public AcceptedRequestsModel() {

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

    public String getDonorID() {
        return donorID;
    }

    public void setDonorID(String donorID) {
        this.donorID = donorID;
    }

    public String getRecieverID() {
        return recieverID;
    }

    public void setRecieverID(String recieverID) {
        this.recieverID = recieverID;
    }

    public String getOpNumber() {
        return opNumber;
    }

    public void setOpNumber(String opNumber) {
        this.opNumber = opNumber;
    }
}

