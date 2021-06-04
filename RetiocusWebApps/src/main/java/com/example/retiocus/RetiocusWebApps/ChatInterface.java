package com.example.retiocus.RetiocusWebApps;

import retrofit2.Call;
import retrofit2.http.GET;

import javax.websocket.server.PathParam;
import java.util.List;

public interface ChatInterface {
    @GET("/chats/{uid}")
    Call<List<Chat>> chatsDe(@PathParam("uid") String uid);
}
