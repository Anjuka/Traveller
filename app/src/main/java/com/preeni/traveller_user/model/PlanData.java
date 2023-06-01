package com.preeni.traveller_user.model;

import java.util.ArrayList;

public class PlanData {

    private ArrayList<PlanDataList> planDataLists;

    public PlanData(ArrayList<PlanDataList> planDataLists) {
        this.planDataLists = planDataLists;
    }

    public ArrayList<PlanDataList> getPlanDataLists() {
        return planDataLists;
    }

    public void setPlanDataLists(ArrayList<PlanDataList> planDataLists) {
        this.planDataLists = planDataLists;
    }
}
