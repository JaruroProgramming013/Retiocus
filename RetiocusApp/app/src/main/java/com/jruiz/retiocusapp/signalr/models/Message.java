package com.jruiz.retiocusapp.signalr.models;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;

public class Message {
    private String remitente, cuerpo;
    private long timeInMillis;

    public Message(String remitente, String cuerpo) {
        this.remitente = remitente;
        this.cuerpo = cuerpo;
        timeInMillis=new Date().getTime();
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public long getFechaEnvio() {
        return timeInMillis;
    }

    public void setFechaEnvio(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}
