package com.jruiz.retiocusapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.ui.loginRegister.LoginFragment;
import com.jruiz.retiocusapp.ui.loginRegister.RegisterFragment;
import com.jruiz.retiocusapp.ui.loginRegister.ViewModelLogin;

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
                        .commit();
        };

        Observer<Boolean> exitoRegistro=aBoolean -> {
            if(aBoolean){
                aOtraActividad(SliderActivity.class);
            }
        }, exitoLogin=aBoolean -> {
            if(aBoolean){
                aOtraActividad(ChatActivity.class);
            }
        };

        viewModelLogin.getExitoLogin().observe(this,exitoLogin);
        viewModelLogin.getExitoRegistro().observe(this,exitoRegistro);
        viewModelLogin.getBotonVolverSeleccionado().observe(this,observadorCambioFragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(viewModelLogin.checkUserOnline())
            startActivity(new Intent(this,ChatActivity.class));
    }

    public void aOtraActividad(Class<?> actividadDestino){
        startActivity(new Intent(this,actividadDestino));
    }


}