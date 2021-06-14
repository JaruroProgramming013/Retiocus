package com.jruiz.retiocusapp.signalr.models;

import java.util.List;

public class CWLMQueryPackage {
    private List<ChatDetailWLM> listadoChats;

    public CWLMQueryPackage(List<ChatDetailWLM> listadoChats) {
        this.listadoChats = listadoChats;
    }

    public List<ChatDetailWLM> getListadoChats() {
        return listadoChats;
    }

    public void setListadoChats(List<ChatDetailWLM> listadoChats) {
        this.listadoChats = listadoChats;
    }
}
