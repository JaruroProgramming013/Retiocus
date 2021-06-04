package com.example.retiocus.RetiocusWebApps.websocket.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public class UserDetail {
    private String uid,username,email;

    public UserDetail(String uid, String username, String email) {
        this.uid = uid;
        this.username = username;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserDetail fromUID(String uid) throws FirebaseAuthException {
        UserRecord record= FirebaseAuth.getInstance().getUser(uid);

        return new UserDetail(uid,record.getDisplayName(), record.getEmail());
    }
}
