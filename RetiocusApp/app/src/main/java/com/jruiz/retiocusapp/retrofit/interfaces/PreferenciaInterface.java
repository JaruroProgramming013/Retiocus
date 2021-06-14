package com.jruiz.retiocusapp.retrofit.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface PreferenciaInterface {

    @DELETE(value = "/api/Preferences/{uid}")
    Call<Void> deletePreferenciasDe(@Path("uid")String uidSolicitante, @Body List<Integer> idsTemas);
}
