package com.example.retiocus.RetiocusWebApps;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ChatCallback implements Callback<List<Chat>> {

    @Override
    public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {

    }

    @Override
    public void onFailure(Call<List<Chat>> call, Throwable throwable) {

    }
}
