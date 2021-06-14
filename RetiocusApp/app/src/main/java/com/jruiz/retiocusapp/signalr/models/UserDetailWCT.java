package com.jruiz.retiocusapp.signalr.models;

public class UserDetailWCT {
    private int numeroTemas;
    private UserDetail usuario;

    public UserDetailWCT(int numeroTemas, UserDetail usuario) {
        this.numeroTemas = numeroTemas;
        this.usuario = usuario;
    }

    public int getNumeroTemas() {
        return numeroTemas;
    }

    public void setNumeroTemas(int numeroTemas) {
        this.numeroTemas = numeroTemas;
    }

    public UserDetail getUsuario() {
        return usuario;
    }

    public void setUsuario(UserDetail usuario) {
        this.usuario = usuario;
    }
}
