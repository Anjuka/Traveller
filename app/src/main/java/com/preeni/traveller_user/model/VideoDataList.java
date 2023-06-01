package com.preeni.traveller_user.model;

public class VideoDataList {

    private String vd_name;
    private String vd_url;

    public VideoDataList() {
    }

    public VideoDataList(String vd_name, String vd_url) {
        this.vd_name = vd_name;
        this.vd_url = vd_url;
    }

    public String getVd_name() {
        return vd_name;
    }

    public void setVd_name(String vd_name) {
        this.vd_name = vd_name;
    }

    public String getVd_url() {
        return vd_url;
    }

    public void setVd_url(String vd_url) {
        this.vd_url = vd_url;
    }
}
