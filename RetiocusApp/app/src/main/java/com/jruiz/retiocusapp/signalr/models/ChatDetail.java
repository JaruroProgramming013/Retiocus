package com.jruiz.retiocusapp.signalr.models;

public class ChatDetail {
    private int ID;
    private String usuarioSolicitante;
    private UserDetail usuarioSolicitado;

    public ChatDetail(int ID, String usuarioSolicitante, UserDetail usuarioSolicitado) {
        this.ID = ID;
        this.usuarioSolicitante = usuarioSolicitante;
        this.usuarioSolicitado = usuarioSolicitado;
    }

    public int getID() {
        return ID;
    }

    public String getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public UserDetail getUsuarioSolicitado() {
        return usuarioSolicitado;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setUsuarioSolicitante(String usuarioSolicitante) {
        this.usuarioSolicitante = usuarioSolicitante;
    }

    public void setUsuarioSolicitado(UserDetail usuarioSolicitado) {
        this.usuarioSolicitado = usuarioSolicitado;
    }
}
