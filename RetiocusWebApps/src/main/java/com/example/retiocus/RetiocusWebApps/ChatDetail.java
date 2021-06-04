package com.example.retiocus.RetiocusWebApps;

import com.google.firebase.auth.FirebaseAuthException;

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

    public static ChatDetail detallar(Chat chat) throws FirebaseAuthException {
        UserDetail usuarioUno=UserDetail.fromUID(chat.getUIDUno()),
        usuarioDos=UserDetail.fromUID(chat.getUIDDos());

        return new ChatDetail(chat.getID(),usuarioUno,usuarioDos);
    }
}
