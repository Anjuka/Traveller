package com.preeni.traveller_user.model;

import java.util.ArrayList;

public class Service {

    private ArrayList<ServiceData> serviceDataArrayList;

    public Service() {
    }

    public Service(ArrayList<ServiceData> serviceDataArrayList) {
        this.serviceDataArrayList = serviceDataArrayList;
    }

    public ArrayList<ServiceData> getServiceDataArrayList() {
        return serviceDataArrayList;
    }

    public void setServiceDataArrayList(ArrayList<ServiceData> serviceDataArrayList) {
        this.serviceDataArrayList = serviceDataArrayList;
    }
}
