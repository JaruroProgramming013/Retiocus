package com.jruiz.retiocusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jruiz.retiocusapp.ui.ViewModelLogin;

public class MainActivity extends AppCompatActivity {

    private ViewModelLogin viewModelLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModelLogin=new ViewModelProvider(this).get(ViewModelLogin.class);
        viewModelLogin.setAuth(FirebaseAuth.getInstance());
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModelLogin.setUser(viewModelLogin.getAuth().getCurrentUser());
        //Cambiar a actividad de visualizacion de chats (condicional)
    }

    public void iniciarSesion(String email, String contrasenha){
        viewModelLogin.getAuth().signInWithEmailAndPassword(email, contrasenha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Si funca
                        if(task.isSuccessful()){
                            Log.d("MainActivity","Sesion iniciada");
                            viewModelLogin.setUser(viewModelLogin.getAuth().getCurrentUser());
                            //Cambiar a actividad de visualizacion de chats
                            //Si no funca
                        }else{
                            Log.w("MainActivity","Fallo en el inicio de sesion",task.getException());
                            Toast.makeText(MainActivity.this,"Fallo en el inicio de sesion",Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    public void registrarUsuario(){
        viewModelLogin.getUser().
        viewModelLogin.getAuth().createUserWithEmailAndPassword()
    }


}