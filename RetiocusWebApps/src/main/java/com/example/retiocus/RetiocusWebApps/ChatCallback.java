package com.example.retiocus.RetiocusWebApps;

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

        try {
            ChatsOfUserServerEndpoint.broadcast(chats);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<List<Chat>> call, Throwable throwable) {

    }
}
