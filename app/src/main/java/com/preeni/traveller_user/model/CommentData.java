package com.preeni.traveller_user.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CommentData {

    ArrayList<CommentContent> commented_user_id;
    String comment_count;

    public CommentData(ArrayList<CommentContent> commented_user_id, String comment_count) {
        this.commented_user_id = commented_user_id;
        this.comment_count = comment_count;
    }

    public CommentData() {
    }

    public ArrayList<CommentContent> getCommented_user_id() {
        return commented_user_id;
    }

    public void setCommented_user_id(ArrayList<CommentContent> commented_user_id) {
        this.commented_user_id = commented_user_id;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }
}
