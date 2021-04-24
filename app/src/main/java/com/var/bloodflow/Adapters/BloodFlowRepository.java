package com.var.bloodflow.Adapters;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BloodFlowRepository {
    private BloodFlowRepository bloodFlowDao;
    private LiveData<List<OutputObject>> allServices;
    private LiveData<List<String>> allStops;
    private LiveData<List<Stop>> allCoordinates, coordinates;
    private LiveData<List<StopObject>> stopServices;
    private LiveData<List<ServiceDetailsObject>> serviceDetails;

    public BloodFlowRepository(Application application) {
        BloodFlowDatabase database = BloodFlowDatabase.getInstance(application);
        bloodFlowDao = database.bloodFlowDao();
        allStops = bloodFlowDao.getAllStops();
        allCoordinates = bloodFlowDao.getAllCoordinates();
    }

    public LiveData<List<OutputObject>> getAllServices(String start, String stop) {
        allServices = bloodFlowDao.getAllServices(start, stop);
        return allServices;
    }

    public LiveData<List<String>> getAllStops() {
        return allStops;
    }

    public LiveData<List<Stop>> getAllCoordinates() {
        return allCoordinates;
    }

    public LiveData<List<StopObject>> getStopServices(String start, int time_hr, int time_min) {
        stopServices = bloodFlowDao.getStopServices(start, time_hr, time_min);
        return stopServices;
    }

    public LiveData<List<Stop>> getCoordinates(String name) {
        coordinates = bloodFlowDao.getCoordinates(name);
        return coordinates;
    }

    public LiveData<List<ServiceDetailsObject>> getServiceDetails(int rid, int bid, String name) {
        serviceDetails = bloodFlowDao.getServiceDetails(rid, bid, name);
        return serviceDetails;
    }

}
