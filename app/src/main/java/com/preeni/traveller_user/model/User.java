package com.preeni.traveller_user.model;

import java.util.ArrayList;

public class User {

    private String user_id;
    private String email;
    private String name;
    private String tp;
    private String country;
    private String bio;
    private String fcmToken;
    private ArrayList<UserFriendList> userFriendListArrayList;
    private ArrayList<UserPostList> userPostArrayList;
    private ArrayList<UserChatList> userChatListArrayList;

    public User(String user_id, String email, String name, String tp, String country, String bio, ArrayList<UserFriendList> userFriendListArrayList, ArrayList<UserPostList> userPostArrayList, ArrayList<UserChatList> userChatListArrayList) {
        this.user_id = user_id;
        this.email = email;
        this.name = name;
        this.tp = tp;
        this.country = country;
        this.bio = bio;
        this.userFriendListArrayList = userFriendListArrayList;
        this.userPostArrayList = userPostArrayList;
        this.userChatListArrayList = userChatListArrayList;
    }

    public User(String user_id, String email, String name, String tp, String country, String bio, String fcmToken, ArrayList<UserFriendList> userFriendListArrayList, ArrayList<UserPostList> userPostArrayList, ArrayList<UserChatList> userChatListArrayList) {
        this.user_id = user_id;
        this.email = email;
        this.name = name;
        this.tp = tp;
        this.country = country;
        this.bio = bio;
        this.fcmToken = fcmToken;
        this.userFriendListArrayList = userFriendListArrayList;
        this.userPostArrayList = userPostArrayList;
        this.userChatListArrayList = userChatListArrayList;
    }

    public User() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ArrayList<UserFriendList> getUserFriendListArrayList() {
        return userFriendListArrayList;
    }

    public void setUserFriendListArrayList(ArrayList<UserFriendList> userFriendListArrayList) {
        this.userFriendListArrayList = userFriendListArrayList;
    }

    public ArrayList<UserPostList> getUserPostArrayList() {
        return userPostArrayList;
    }

    public void setUserPostArrayList(ArrayList<UserPostList> userPostArrayList) {
        this.userPostArrayList = userPostArrayList;
    }

    public ArrayList<UserChatList> getUserChatListArrayList() {
        return userChatListArrayList;
    }

    public void setUserChatListArrayList(ArrayList<UserChatList> userChatListArrayList) {
        this.userChatListArrayList = userChatListArrayList;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
