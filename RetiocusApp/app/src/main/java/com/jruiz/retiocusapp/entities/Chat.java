package com.jruiz.retiocusapp.entities;

public class Chat {
    private int ID;

    private String UIDUno;
    private String UIDDos;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUIDUno() {
        return UIDUno;
    }

    public void setUIDUno(String UIDUno) {
        this.UIDUno = UIDUno;
    }

    public String getUIDDos() {
        return UIDDos;
    }

    public void setUIDDos(String UIDDos) {
        this.UIDDos = UIDDos;
    }

    public Chat() {
        ID=0;
        UIDUno="";
        UIDDos="";
    }

    public Chat(String UIDUno, String UIDDos) {
        this.ID = 0;
        this.UIDUno = UIDUno;
        this.UIDDos = UIDDos;
    }
}
