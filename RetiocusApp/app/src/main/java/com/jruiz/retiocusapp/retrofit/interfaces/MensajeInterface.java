package com.jruiz.retiocusapp.retrofit.interfaces;

import com.jruiz.retiocusapp.signalr.models.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MensajeInterface {
    @GET("/api/Messages/{chatID}")
    Call<List<Message>> listadoPrimerosCincuentaMensajes(@Path("chatID")int idChat);
}
