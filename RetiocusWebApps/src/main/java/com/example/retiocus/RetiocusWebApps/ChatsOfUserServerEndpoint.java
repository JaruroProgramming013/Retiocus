package com.example.retiocus.RetiocusWebApps;

import com.google.api.client.json.gson.GsonFactory;
import retrofit2    .Retrofit;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "chats/{uid}/allChats")
public class ChatsOfUserServerEndpoint {
    private Session sesionActual;

    @OnOpen
    public void OnOpen(Session sesion, @PathParam("uid") String uid){
        sesionActual=sesion;

    }
}
