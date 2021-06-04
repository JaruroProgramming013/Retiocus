package com.example.retiocus.RetiocusWebApps.websocket.endpoints;

import com.example.retiocus.RetiocusWebApps.retrofit.callbacks.ChatCallback;
import com.example.retiocus.RetiocusWebApps.retrofit.interfaces.ChatInterface;
import com.example.retiocus.RetiocusWebApps.retrofit.models.ChatListWithPetitioner;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "chats/{uid}/allChats")
public class ChatsOfUserServerEndpoint {
    private Session sesionActual;
    private final Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://retiocusapi.azurewebsites.net/api")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static final Set<ChatsOfUserServerEndpoint> couEndpoints=new CopyOnWriteArraySet<>();

    @OnOpen
    public void OnOpen(Session sesion, @PathParam("uid") String uid){
        sesionActual=sesion;
        couEndpoints.add(this);

        ChatCallback chatCallback=new ChatCallback();
        ChatInterface chatInterface=retrofit.create(ChatInterface.class);

        chatInterface.chatsDe(uid).enqueue(chatCallback);
    }

    @OnClose
    public void OnClose(){
        couEndpoints.remove(this);
    }

    @OnError
    public void OnError(Throwable error) throws IOException{
        CloseReason razon;

        switch (error.getClass().getName()){
            case "IOException":
                razon=new CloseReason(CloseReason.CloseCodes.PROTOCOL_ERROR,"Error en el flujo de entrada salida: "+error.getMessage());
                break;
            case "EncodeException":
                razon=new CloseReason(CloseReason.CloseCodes.TOO_BIG,"Listado demasiado grande: "+error.getMessage());
                break;
            default:
                razon=new CloseReason(CloseReason.CloseCodes.NO_STATUS_CODE,"Error desconocido: "+error.getMessage());
        }

        sesionActual.close(razon);
        couEndpoints.remove(this);
    }

    public static void broadcast(ChatListWithPetitioner chatListWithPetitioner){
        couEndpoints.forEach(endpoint->{
            synchronized (endpoint){
                if(endpoint.sesionActual.getId().equals(chatListWithPetitioner.getUid())) {
                    try {
                        endpoint.sesionActual.getBasicRemote().sendObject(chatListWithPetitioner);
                        endpoint.sesionActual.close();
                    } catch (IOException | EncodeException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
