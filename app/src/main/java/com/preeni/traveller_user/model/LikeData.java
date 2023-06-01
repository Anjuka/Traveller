package com.preeni.traveller_user.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class LikeData {

    ArrayList<String> liked_user_id;
    String like_count;

    public LikeData() {
    }

    public LikeData(ArrayList<String> liked_user_id, String like_count) {
        this.liked_user_id = liked_user_id;
        this.like_count = like_count;
    }

    protected LikeData(Parcel in) {
        liked_user_id = in.createStringArrayList();
        like_count = in.readString();
    }


    public ArrayList<String> getLiked_user_id() {
        return liked_user_id;
    }

    public void setLiked_user_id(ArrayList<String> liked_user_id) {
        this.liked_user_id = liked_user_id;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }
}
