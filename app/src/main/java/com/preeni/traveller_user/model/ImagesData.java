package com.preeni.traveller_user.model;

import java.util.ArrayList;

public class ImagesData {

    private ArrayList<ImagesDataList> imagesDataLists;

    public ImagesData() {
    }

    public ImagesData(ArrayList<ImagesDataList> imagesDataLists) {
        this.imagesDataLists = imagesDataLists;
    }

    public ArrayList<ImagesDataList> getImagesDataLists() {
        return imagesDataLists;
    }

    public void setImagesDataLists(ArrayList<ImagesDataList> imagesDataLists) {
        this.imagesDataLists = imagesDataLists;
    }
}
