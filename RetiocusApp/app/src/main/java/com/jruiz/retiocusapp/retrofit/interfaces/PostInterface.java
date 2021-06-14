package com.jruiz.retiocusapp.retrofit.interfaces;

import com.jruiz.retiocusapp.entities.Chat;
import com.jruiz.retiocusapp.entities.Tema;
import com.jruiz.retiocusapp.signalr.models.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostInterface {
    @POST("/api/Users")
    Call<Void> postUser(@Body String string);

    @POST("/api/Chats")
    Call<Void> postChat(@Body Chat chat);

    @POST("/api/Themes")
    Call<Void> postTema(@Body Tema tema);

    @POST("/api/Preferences/{uid}")
    Call<Void> postPreferencias(@Path("uid") String uid, @Body List<Integer> idTemas);

    @POST("/api/Messages/{chatID}")
    Call<Void> postMensaje(@Path("chatID") int id, @Body Message message);
}
