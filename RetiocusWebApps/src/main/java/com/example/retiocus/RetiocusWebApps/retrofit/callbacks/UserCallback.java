package com.example.retiocus.RetiocusWebApps.retrofit.callbacks;

import com.example.retiocus.RetiocusWebApps.entities.Chat;
import com.example.retiocus.RetiocusWebApps.retrofit.models.ChatListWithPetitioner;
import com.example.retiocus.RetiocusWebApps.retrofit.models.UserListWithPetitioner;
import com.example.retiocus.RetiocusWebApps.websocket.endpoints.ChatsOfUserServerEndpoint;
import com.example.retiocus.RetiocusWebApps.websocket.endpoints.UsersWithCommonThemesServerEndpoint;
import com.example.retiocus.RetiocusWebApps.websocket.models.ChatDetail;
import com.example.retiocus.RetiocusWebApps.websocket.models.UserDetail;
import com.google.firebase.auth.FirebaseAuthException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserCallback implements Callback<List<String>> {
    @Override
    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
        String uidSolicitante=call.request().url().pathSegments().get(call.request().url().pathSegments().size()-1);
        List<String> listado=response.body();
        List<UserDetail> userDetails= new ArrayList<>();
        UserListWithPetitioner usuarios=new UserListWithPetitioner();

        if (listado != null) {
            for(String uid:listado){
                try {
                    userDetails.add(UserDetail.fromUID(uid));
                } catch (FirebaseAuthException e) {
                    e.printStackTrace();
                }
            }


            usuarios=new UserListWithPetitioner(uidSolicitante, userDetails);
        }

        UsersWithCommonThemesServerEndpoint.broadcast(usuarios);
    }

    @Override
    public void onFailure(Call<List<String>> call, Throwable throwable) {

    }
}
