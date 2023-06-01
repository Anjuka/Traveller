package com.preeni.traveller_user.model;

import java.util.ArrayList;

public class GuidelineData {

    private ArrayList<putPDF> guidelineDataList;

    public GuidelineData(ArrayList<putPDF> guidelineDataList) {
        this.guidelineDataList = guidelineDataList;
    }

    public ArrayList<putPDF> getGuidelineDataList() {
        return guidelineDataList;
    }

    public void setGuidelineDataList(ArrayList<putPDF> guidelineDataList) {
        this.guidelineDataList = guidelineDataList;
    }
}
