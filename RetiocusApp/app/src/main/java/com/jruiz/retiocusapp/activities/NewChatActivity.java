package com.jruiz.retiocusapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.signalr.models.UserDetailWCT;
import com.jruiz.retiocusapp.ui.newChat.DetailFragment;
import com.jruiz.retiocusapp.ui.newChat.NewChatListFragment;
import com.jruiz.retiocusapp.ui.newChat.NewChatViewModel;

import java.util.List;

public class NewChatActivity extends AppCompatActivity {

    private NewChatViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        viewModel=new ViewModelProvider(this).get(NewChatViewModel.class);
        Observer<String> observerUsuarioSeleccionado= new Observer<String>() {
            @Override
            public void onChanged(String s) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainerNewChat, DetailFragment.class,null)
                        .addToBackStack(null)
                        .commit();
            }
        };
        Observer<List<UserDetailWCT>> listObserver=new Observer<List<UserDetailWCT>>() {
            @Override
            public void onChanged(List<UserDetailWCT> userDetailWCTS) {
                viewModel.prepararReferenciasAPFPs();
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragmentContainerNewChat, NewChatListFragment.class,null)
                        .commit();
            }
        };

        Observer<Boolean> chatAnhadido=new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    startActivity(new Intent(getApplicationContext(),ChatActivity.class));
            }
        };
        viewModel.getNuevoChatAnhadido().observe(this,chatAnhadido);
        viewModel.getListaUsuariosObservable().observe(this,listObserver);
        viewModel.getUsuarioSeleccionado().observe(this,observerUsuarioSeleccionado);
        viewModel.startQueryNewChat();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.closeQueryNewChat();
    }
}