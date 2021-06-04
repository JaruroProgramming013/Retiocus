package com.example.retiocus.RetiocusWebApps.retrofit.models;

import com.example.retiocus.RetiocusWebApps.websocket.models.ChatDetail;

import java.util.ArrayList;
import java.util.List;

public class ChatListWithPetitioner {
    private String uid;
    private List<ChatDetail> listadoChats;

    public ChatListWithPetitioner(){
        uid="";
        listadoChats=new ArrayList<>();
    }

    public ChatListWithPetitioner(String uid, List<ChatDetail> listadoChats) {
        this.uid = uid;
        this.listadoChats = listadoChats;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<ChatDetail> getListadoChats() {
        return listadoChats;
    }

    public void setListadoChats(List<ChatDetail> listadoChats) {
        this.listadoChats = listadoChats;
    }
}
