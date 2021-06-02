package com.jruiz.retiocusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jruiz.retiocusapp.repo.Repository;
import com.jruiz.retiocusapp.ui.LoginFragment;
import com.jruiz.retiocusapp.ui.RegisterFragment;
import com.jruiz.retiocusapp.ui.ViewModelLogin;

public class MainActivity extends AppCompatActivity {

    private ViewModelLogin viewModelLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModelLogin=new ViewModelProvider(this).get(ViewModelLogin.class);
        viewModelLogin.iniciarAuth();

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentLoginRegister, LoginFragment.class,null)
                .commit();

        Observer<Integer> observadorCambioFragment= integer -> {
            if(integer==1)
                getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentLoginRegister, RegisterFragment.class, null)
                    .addToBackStack(null)
                    .commit();
            else
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentLoginRegister, LoginFragment.class, null)
                        .addToBackStack(null)
                        .commit();
        };

        Observer<Boolean> exitoRegistro=aBoolean -> {
            if(aBoolean){
                startActivity(new Intent(this,ChatActivity.class));
            }
        };

        viewModelLogin.getExitoRegistro().observe(this,exitoRegistro);
        viewModelLogin.getBotonVolverSeleccionado().observe(this,observadorCambioFragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModelLogin.iniciarUsuario();
        //Cambiar a actividad de visualizacion de chats (condicional)
    }


}