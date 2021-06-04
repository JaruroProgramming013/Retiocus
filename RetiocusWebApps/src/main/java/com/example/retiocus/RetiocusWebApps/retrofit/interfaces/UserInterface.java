package com.example.retiocus.RetiocusWebApps.retrofit.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;

import javax.websocket.server.PathParam;
import java.util.List;

//Solo se usa para la query gorda de un nuevo chat
public interface UserInterface {
    @GET("/Users/{uid}")
    Call<List<String>> usuariosConTemasComunesCon(@PathParam("uid") String uid);
}
