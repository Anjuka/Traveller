package com.preeni.traveller_user.model;

public class CommentContent {

    String commented_user_id;
    String commented_user_name;
    String comment;
    String time;
    String date;

    public CommentContent() {
    }

    public CommentContent(String commented_user_id, String commented_user_name, String comment, String time, String date) {
        this.commented_user_id = commented_user_id;
        this.commented_user_name = commented_user_name;
        this.comment = comment;
        this.time = time;
        this.date = date;
    }

    public String getCommented_user_id() {
        return commented_user_id;
    }

    public void setCommented_user_id(String commented_user_id) {
        this.commented_user_id = commented_user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCommented_user_name() {
        return commented_user_name;
    }

    public void setCommented_user_name(String commented_user_name) {
        this.commented_user_name = commented_user_name;
    }
}
