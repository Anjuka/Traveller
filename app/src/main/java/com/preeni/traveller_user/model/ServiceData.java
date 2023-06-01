package com.preeni.traveller_user.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ServiceData implements Parcelable {

    private String post_text;
    private String post_url;
    private String likes;
    private ArrayList<String> comments;
    private String timeStamp;
    private String user_id;
    private String user_name;
    private String service_type;

    public ServiceData() {
    }

    public ServiceData(String post_text, String post_url, String likes, ArrayList<String> comments, String timeStamp, String user_id, String user_name, String service_type) {
        this.post_text = post_text;
        this.post_url = post_url;
        this.likes = likes;
        this.comments = comments;
        this.timeStamp = timeStamp;
        this.user_id = user_id;
        this.user_name = user_name;
        this.service_type = service_type;
    }

    protected ServiceData(Parcel in) {
        post_text = in.readString();
        post_url = in.readString();
        likes = in.readString();
        comments = in.createStringArrayList();
        timeStamp = in.readString();
        user_id = in.readString();
        user_name = in.readString();
        service_type = in.readString();
    }

    public static final Creator<ServiceData> CREATOR = new Creator<ServiceData>() {
        @Override
        public ServiceData createFromParcel(Parcel in) {
            return new ServiceData(in);
        }

        @Override
        public ServiceData[] newArray(int size) {
            return new ServiceData[size];
        }
    };

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

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
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

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(post_text);
        parcel.writeString(post_url);
        parcel.writeString(likes);
        parcel.writeStringList(comments);
        parcel.writeString(timeStamp);
        parcel.writeString(user_id);
        parcel.writeString(user_name);
        parcel.writeString(service_type);
    }
}
