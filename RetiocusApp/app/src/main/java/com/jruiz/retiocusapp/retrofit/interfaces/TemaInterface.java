package com.jruiz.retiocusapp.retrofit.interfaces;

import com.jruiz.retiocusapp.entities.Tema;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface TemaInterface {
    @GET("/Themes/{uidSolicitante}/sharedWith/{uidSolicitado}")
    Call<List<Tema>> temasComunesEntre(@Path("uidSolicitante}") String uidSolicitante, @Path("uidSolicitado}") String uidSolicitado);

    @GET("/Themes/{uid}")
    Call<List<Tema>> temasDe(@Path("uid") String uid);

    @POST("/Themes")
    Call<Tema> anhadirTema(@Body Tema tema);
}
