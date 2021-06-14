package com.jruiz.retiocusapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.glide.modules.GlideApp;
import com.jruiz.retiocusapp.repo.Repository;
import com.jruiz.retiocusapp.signalr.models.ChatDetailWLM;
import com.jruiz.retiocusapp.ui.chat.ChatListFragment;
import com.jruiz.retiocusapp.ui.chat.ChatViewModel;
import com.jruiz.retiocusapp.ui.chat.ChooserFragment;
import com.jruiz.retiocusapp.ui.loginRegister.RegisterFragment;
import com.jruiz.retiocusapp.ui.newChat.DetailFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ChatViewModel viewModelChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        viewModelChat=new ViewModelProvider(this).get(ChatViewModel.class);
        FloatingActionButton logOut = (FloatingActionButton) findViewById(R.id.logOut);
        FloatingActionButton anhadirChat=(FloatingActionButton)findViewById(R.id.anhadirChat);

        Context context=this;
        logOut.setOnClickListener(listener -> {
            volverALogin();
        });

        anhadirChat.setOnClickListener(listener -> {
            startActivity(new Intent(context,NewChatActivity.class));
        });

        Observer<List<ChatDetailWLM>> observerListadoChats=new Observer<List<ChatDetailWLM>>() {
            @Override
            public void onChanged(List<ChatDetailWLM> chatDetailWLMS) {
                anhadirChat.setEnabled(true);
                viewModelChat.prepararReferenciasAPFPs();
                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.containerFragmentChat, ChatListFragment.class,null)
                        .commit();
            }
        };

        Observer<Integer> observadorBotonSeleccionado=new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.containerFragmentChat, DetailFragment.class,null)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 2:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.containerFragmentChat, RegisterFragment.class, null)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 3:
                        startActivity(new Intent(context,SliderEditActivity.class));
                        break;
                    case 4:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.containerFragmentChat, ChooserFragment.class, null)
                                .addToBackStack(null)
                                .commit();
                }
            }
        };

        Observer<String> uidUsuarioSeleccionado=new Observer<String>() {
            @Override
            public void onChanged(String s) {
                startActivity(new Intent(context,ChattingActivity.class));
            }
        };
        viewModelChat.getListaDetalleChat().observe(this,observerListadoChats);
        viewModelChat.getBotonSeleccionado().observe(this,observadorBotonSeleccionado);
        anhadirChat.setEnabled(false);
        viewModelChat.startQueryChats();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Repository.getAutenticacion()!=null)
            Repository.getAutenticacion().signOut();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentById(R.id.containerFragmentChat).getClass()==ChatListFragment.class)
            volverALogin();
        super.onBackPressed();
    }

    public void volverALogin(){
        Repository.signOutCurrentUser();
        Toast.makeText(this,"Sesión cerrada",Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int currentFragment=0;
        if((getSupportFragmentManager().findFragmentById(R.id.containerFragmentChat)).getClass()==ChooserFragment.class)
            currentFragment=1;
        else if((getSupportFragmentManager().findFragmentById(R.id.containerFragmentChat)).getClass()==DetailFragment.class)
            currentFragment=2;
        else if((getSupportFragmentManager().findFragmentById(R.id.containerFragmentChat)).getClass()==RegisterFragment.class)
            currentFragment=3;

        outState.putInt("CURRENT_FRAGMENT_STATE", currentFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        switch (savedInstanceState.getInt("CURRENT_FRAGMENT_STATE")){
            case 0:
                viewModelChat.sendQueryChatPetition();

                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.containerFragmentChat, ChatListFragment.class,null)
                        .commit();
                break;
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.containerFragmentChat, ChooserFragment.class, null)
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.containerFragmentChat, DetailFragment.class,null)
                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.containerFragmentChat, RegisterFragment.class, null)
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}