package com.jruiz.retiocusapp.repo.queries.models;

import com.jruiz.retiocusapp.entities.Tema;
import com.jruiz.retiocusapp.websocket.models.UserDetail;

import java.util.ArrayList;
import java.util.List;

public class NewChatResultQueryRow {
    private UserDetail usuario;
    private List<Tema> temas;

    public NewChatResultQueryRow() {
        usuario=new UserDetail("","","");
        temas=new ArrayList<>();
    }

    public NewChatResultQueryRow(UserDetail usuario, List<Tema> temas) {
        this.usuario = usuario;
        this.temas = temas;
    }

    public UserDetail getUsuario() {
        return usuario;
    }

    public void setUsuario(UserDetail usuario) {
        this.usuario = usuario;
    }

    public List<Tema> getTemas() {
        return temas;
    }

    public void setTemas(List<Tema> temas) {
        this.temas = temas;
    }

    public String getSolicitante(){
        return usuario.getUid();
    }
}
