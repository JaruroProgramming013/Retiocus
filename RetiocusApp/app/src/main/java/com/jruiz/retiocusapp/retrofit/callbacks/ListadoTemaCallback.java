package com.jruiz.retiocusapp.retrofit.callbacks;
import com.jruiz.retiocusapp.entities.Tema;
import com.jruiz.retiocusapp.repo.Repository;
import com.jruiz.retiocusapp.retrofit.models.TemasListWithPetitioner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ListadoTemaCallback implements Callback<List<Tema>> {

    @Override
    public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
        if(call.request().url().pathSize()==2){
            Repository.setListadoTemasUsuarioActual(new TemasListWithPetitioner(call.request().url().pathSegments().get(2),response.body()));
        }
    }

    @Override
    public void onFailure(Call<List<Tema>> call, Throwable throwable) {

    }
}
