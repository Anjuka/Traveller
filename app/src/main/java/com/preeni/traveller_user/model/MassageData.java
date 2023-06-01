package com.preeni.traveller_user.model;

import java.util.ArrayList;

public class MassageData {

    private ArrayList<Massage> massageList;

    public MassageData(ArrayList<Massage> massageList) {
        this.massageList = massageList;
    }

    public MassageData() {
    }

    public ArrayList<Massage> getMassageList() {
        return massageList;
    }

    public void setMassageList(ArrayList<Massage> massageList) {
        this.massageList = massageList;
    }
}
