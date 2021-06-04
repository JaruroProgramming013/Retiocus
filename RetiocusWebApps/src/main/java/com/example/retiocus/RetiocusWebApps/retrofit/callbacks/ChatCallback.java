package com.example.retiocus.RetiocusWebApps.retrofit.callbacks;

import com.example.retiocus.RetiocusWebApps.entities.Chat;
import com.example.retiocus.RetiocusWebApps.websocket.endpoints.ChatsOfUserServerEndpoint;
import com.example.retiocus.RetiocusWebApps.websocket.models.ChatDetail;
import com.example.retiocus.RetiocusWebApps.retrofit.models.ChatListWithPetitioner;
import com.google.firebase.auth.FirebaseAuthException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatCallback implements Callback<List<Chat>> {

    @Override
    public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
        String uidSolicitante=call.request().url().pathSegments().get(call.request().url().pathSegments().size()-1);
        List<Chat> listado=response.body();
        List<ChatDetail> chatDetails= new ArrayList<>();
        ChatListWithPetitioner chats=new ChatListWithPetitioner();

        if (listado != null) {
            for(Chat c:listado){
                try {
                    chatDetails.add(ChatDetail.detallar(c));
                } catch (FirebaseAuthException e) {
                    e.printStackTrace();
                }
            }


            chats=new ChatListWithPetitioner(uidSolicitante, chatDetails);
        }

        ChatsOfUserServerEndpoint.broadcast(chats);
    }

    @Override
    public void onFailure(Call<List<Chat>> call, Throwable throwable) {

    }
}
