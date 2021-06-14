package com.jruiz.retiocusapp.entities;

public class Tema {
    private int ID;
    private String Nombre;

    public Tema(int ID, String nombre) {
        this.ID = ID;
        this.Nombre = nombre;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        boolean ret=false;

        if (this == o) ret=true;
        else if (o != null && getClass() == o.getClass()) {

            Tema tema = (Tema) o;

            if (ID == tema.ID && Nombre.equals(tema.Nombre))
                ret=true;
        }
        return ret;
    }

    @Override
    public int hashCode() {
        int result = ID;
        result = 31 * result + (Nombre != null ? Nombre.hashCode() : 0);
        return result;
    }
}
