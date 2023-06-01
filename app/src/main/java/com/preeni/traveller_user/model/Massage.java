package com.preeni.traveller_user.model;

public class Massage {

    private String receiverId;
    private String senderId;
    private String massage;
    private String timestamp;

    public Massage(String receiverId, String senderId, String massage, String timestamp) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.massage = massage;
        this.timestamp = timestamp;
    }

    public Massage() {
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
