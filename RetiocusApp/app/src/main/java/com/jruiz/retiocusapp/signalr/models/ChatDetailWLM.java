package com.jruiz.retiocusapp.signalr.models;

public class ChatDetailWLM {
    private ChatDetail chat;
    private Message ultimoMensaje;

    public ChatDetailWLM(ChatDetail chat, Message ultimoMensaje) {
        this.chat = chat;
        this.ultimoMensaje = ultimoMensaje;
    }

    public ChatDetail getChat() {
        return chat;
    }

    public void setChat(ChatDetail chat) {
        this.chat = chat;
    }

    public Message getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(Message ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }
}
