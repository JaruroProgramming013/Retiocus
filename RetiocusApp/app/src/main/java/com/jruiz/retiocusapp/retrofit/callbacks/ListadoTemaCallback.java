package com.jruiz.retiocusapp.retrofit.callbacks;

import com.jruiz.retiocusapp.entities.Tema;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ListadoTemaCallback implements Callback<List<Tema>> {

    @Override
    public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {

    }

    @Override
    public void onFailure(Call<List<Tema>> call, Throwable throwable) {

    }
}
