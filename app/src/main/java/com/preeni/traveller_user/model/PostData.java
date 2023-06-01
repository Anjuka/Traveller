package com.preeni.traveller_user.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PostData {

    private String post_text;
    private String post_url;
    private ArrayList<LikeData> likes;
    private ArrayList<CommentData> comments;
    private String timeStamp;
    private String user_id;
    private String user_name;

    public PostData() {
    }

    public PostData(String post_text, String post_url, ArrayList<LikeData> likes, ArrayList<CommentData> comments, String timeStamp, String user_id, String user_name) {
        this.post_text = post_text;
        this.post_url = post_url;
        this.likes = likes;
        this.comments = comments;
        this.timeStamp = timeStamp;
        this.user_id = user_id;
        this.user_name = user_name;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public ArrayList<LikeData> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<LikeData> likes) {
        this.likes = likes;
    }

    public ArrayList<CommentData> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentData> comments) {
        this.comments = comments;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
