package com.jruiz.retiocusapp.signalr.models;

public class UserDetail {
    private String uid;
    private String username;

    public UserDetail() {
        uid="";
        username="";
    }

    public UserDetail(String uid, String username) {
        this.uid = uid;
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
