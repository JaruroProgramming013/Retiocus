package com.example.retiocus.RetiocusWebApps;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public class UserWithDeglossedData {
    private String uid,username,email;

    public UserWithDeglossedData(String uid, String username, String email) {
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

    public static UserWithDeglossedData fromUID(String uid) throws FirebaseAuthException {
        UserRecord recordUsuario= FirebaseAuth.getInstance().getUser(uid);
        return new UserWithDeglossedData(recordUsuario.getUid(),
                recordUsuario.getDisplayName(),
                recordUsuario.getEmail());
    }
}
