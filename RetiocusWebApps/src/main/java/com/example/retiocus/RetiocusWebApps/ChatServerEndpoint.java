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
    public void OnOpen(Session sesion){
        sesionActual=sesion;
        endpointsServer.add(this);
    }

    @OnMessage
    public void OnMessage(Message mensaje){
        enviarMensaje(mensaje);
    }

    @OnClose
    public void OnClose(){
        endpointsServer.remove(this);
    }

    @OnError
    public void OnError(Throwable error) throws IOException{
        CloseReason razon;

        switch (error.getClass().getName()){
            case "IOException":
                razon=new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR,"Error en el flujo de entrada salida: "+error.getMessage());
                break;
            case "EncodeException":
                razon=new CloseReason(CloseReason.CloseCodes.TOO_BIG,"Mensaje demasiado grande: "+error.getMessage());
                break;
            default:
                razon=new CloseReason(CloseReason.CloseCodes.NO_STATUS_CODE,"Error desconocido: "+error.getMessage());
        }

        sesionActual.close(razon);
        endpointsServer.remove(this);
    }

    private static void enviarMensaje(Message mensaje){
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