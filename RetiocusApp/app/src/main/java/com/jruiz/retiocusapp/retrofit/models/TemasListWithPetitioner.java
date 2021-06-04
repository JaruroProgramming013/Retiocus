package com.jruiz.retiocusapp.retrofit.models;

import com.jruiz.retiocusapp.entities.Tema;

import java.util.List;

public class TemasListWithPetitioner {
    private String uidSolicitante;
    private List<Tema> listadoTemas;
    private String uidSolicitado;

    public TemasListWithPetitioner(String uidSolicitante, List<Tema> listadoTemas) {
        this.uidSolicitante = uidSolicitante;
        this.listadoTemas = listadoTemas;
        this.uidSolicitado="";
    }

    public String getUidSolicitante() {
        return uidSolicitante;
    }

    public void setUidSolicitante(String uidSolicitante) {
        this.uidSolicitante = uidSolicitante;
    }

    public List<Tema> getListadoTemas() {
        return listadoTemas;
    }

    public String getUidSolicitado() {
        return uidSolicitado;
    }

    public void setUidSolicitado(String uidSolicitado) {
        this.uidSolicitado = uidSolicitado;
    }

    public void setListadoTemas(List<Tema> listadoTemas) {
        this.listadoTemas = listadoTemas;
    }
}
