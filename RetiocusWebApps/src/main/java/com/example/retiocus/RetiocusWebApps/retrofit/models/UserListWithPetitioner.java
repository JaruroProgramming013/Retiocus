package com.example.retiocus.RetiocusWebApps.retrofit.models;

import com.example.retiocus.RetiocusWebApps.websocket.models.UserDetail;

import java.util.ArrayList;
import java.util.List;

public class UserListWithPetitioner {
    private String uidSolicitante;
    private List<UserDetail> usuarios;

    public UserListWithPetitioner() {
        uidSolicitante="";
        usuarios=new ArrayList<>();
    }

    public UserListWithPetitioner(String uidSolicitante, List<UserDetail> usuarios) {
        this.uidSolicitante = uidSolicitante;
        this.usuarios = usuarios;
    }

    public String getUidSolicitante() {
        return uidSolicitante;
    }

    public void setUidSolicitante(String uidSolicitante) {
        this.uidSolicitante = uidSolicitante;
    }

    public List<UserDetail> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UserDetail> usuarios) {
        this.usuarios = usuarios;
    }
}
