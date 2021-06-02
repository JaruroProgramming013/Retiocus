package com.example.retiocus.RetiocusWebApps;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "chats/{uid}",
                decoders = MessageDecoder.class,
                encoders = MessageEncoder.class)
public class ChatServerEndpoint {
    private Session sesionActual;
    private static final Set<ChatServerEndpoint> endpointsServer=new CopyOnWriteArraySet<>();

    @OnOpen
    public void OnOpen(Session sesion, @PathParam("uid") String uid) throws IOException{
        sesionActual=sesion;
        endpointsServer.add(this);
    }

    @OnMessage
    public void OnMessage(Session sesion, Message mensaje) throws IOException {
        enviarMensaje(mensaje);
    }

    @OnClose
    public void OnClose(Session sesion) throws IOException{
        endpointsServer.remove(this);
    }

    private static void enviarMensaje(Message mensaje) throws IOException{
        endpointsServer.forEach(endpoint->{
            synchronized (endpoint){
                try {
                    if(endpoint.sesionActual.getId().equals(mensaje.getTo()))
                        endpoint.sesionActual.getBasicRemote().sendObject(mensaje);
                }catch (IOException | EncodeException exception){
                    exception.printStackTrace();
                }
            }
        });
    }

}
