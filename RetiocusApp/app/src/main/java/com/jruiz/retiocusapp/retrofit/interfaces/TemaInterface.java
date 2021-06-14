package com.jruiz.retiocusapp.retrofit.interfaces;

import com.jruiz.retiocusapp.entities.Tema;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface TemaInterface {
    @GET("/api/Themes/{uidSolicitante}/sharedWith/{uidSolicitado}")
    Call<List<Tema>> temasComunesEntre(@Path("uidSolicitante}") String uidSolicitante, @Path("uidSolicitado}") String uidSolicitado);

    @GET("/api/Themes/{uid}")
    Call<List<Tema>> temasDe(@Path("uid") String uid);

    @GET("/api/Themes")
    Call<List<Tema>> todosLosTemas();

}
