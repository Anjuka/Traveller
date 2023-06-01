package com.preeni.traveller_user.model;

import android.os.Parcel;
import android.os.Parcelable;

public class putPDF implements Parcelable {

    private String name;
    private String url;

    public putPDF(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public putPDF() {
    }

    protected putPDF(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<putPDF> CREATOR = new Creator<putPDF>() {
        @Override
        public putPDF createFromParcel(Parcel in) {
            return new putPDF(in);
        }

        @Override
        public putPDF[] newArray(int size) {
            return new putPDF[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(url);
    }
}
