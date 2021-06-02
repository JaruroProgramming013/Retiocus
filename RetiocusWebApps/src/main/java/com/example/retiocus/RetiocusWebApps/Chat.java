package com.example.retiocus.RetiocusWebApps;

public class Chat {
    private int ID;
    private String UIDUno, UIDDos;

    public Chat(int ID, String UIDUno, String UIDDos) {
        this.ID = ID;
        this.UIDUno = UIDUno;
        this.UIDDos = UIDDos;
    }

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
}
