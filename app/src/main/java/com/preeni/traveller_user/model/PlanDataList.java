package com.preeni.traveller_user.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PlanDataList implements Parcelable {

    private String plane_code;
    private String duration;
    private String routing;
    private String description;
    private String rating;

    public PlanDataList() {
    }

    public PlanDataList(String plane_code, String duration, String routing, String description, String rating) {
        this.plane_code = plane_code;
        this.duration = duration;
        this.routing = routing;
        this.description = description;
        this.rating = rating;
    }

    protected PlanDataList(Parcel in) {
        plane_code = in.readString();
        duration = in.readString();
        routing = in.readString();
        description = in.readString();
        rating = in.readString();
    }

    public static final Creator<PlanDataList> CREATOR = new Creator<PlanDataList>() {
        @Override
        public PlanDataList createFromParcel(Parcel in) {
            return new PlanDataList(in);
        }

        @Override
        public PlanDataList[] newArray(int size) {
            return new PlanDataList[size];
        }
    };

    public String getPlane_code() {
        return plane_code;
    }

    public void setPlane_code(String plane_code) {
        this.plane_code = plane_code;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(plane_code);
        parcel.writeString(duration);
        parcel.writeString(routing);
        parcel.writeString(description);
        parcel.writeString(rating);
    }
}
