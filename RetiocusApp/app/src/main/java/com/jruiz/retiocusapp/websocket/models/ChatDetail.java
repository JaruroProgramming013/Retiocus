package com.jruiz.retiocusapp.websocket.models;

public class ChatDetail {
    private int ID;
    private UserDetail usuarioUno, usuarioDos;

    public ChatDetail(int ID, UserDetail usuarioUno, UserDetail usuarioDos) {
        this.ID = ID;
        this.usuarioUno = usuarioUno;
        this.usuarioDos = usuarioDos;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public UserDetail getUsuarioUno() {
        return usuarioUno;
    }

    public void setUsuarioUno(UserDetail usuarioUno) {
        this.usuarioUno = usuarioUno;
    }

    public UserDetail getUsuarioDos() {
        return usuarioDos;
    }

    public void setUsuarioDos(UserDetail usuarioDos) {
        this.usuarioDos = usuarioDos;
    }

}
