package com.jruiz.retiocusapp.signalr.models;

import java.util.List;

public class UWCTQueryPackage {
    private final List<UserDetailWCT> listadoUsuariosDetalladosWCT;

    public UWCTQueryPackage(List<UserDetailWCT> listadoUsuariosDetalladosWCT) {
        this.listadoUsuariosDetalladosWCT = listadoUsuariosDetalladosWCT;
    }

    public List<UserDetailWCT> getListadoUsuariosDetalladosWCT() {
        return listadoUsuariosDetalladosWCT;
    }
}
