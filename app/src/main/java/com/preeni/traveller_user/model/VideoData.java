package com.preeni.traveller_user.model;

import java.util.ArrayList;

public class VideoData {

    private ArrayList<VideoDataList> videoDataLists;

    public VideoData() {
    }

    public VideoData(ArrayList<VideoDataList> videoDataLists) {
        this.videoDataLists = videoDataLists;
    }

    public ArrayList<VideoDataList> getVideoDataLists() {
        return videoDataLists;
    }

    public void setVideoDataLists(ArrayList<VideoDataList> videoDataLists) {
        this.videoDataLists = videoDataLists;
    }
}
