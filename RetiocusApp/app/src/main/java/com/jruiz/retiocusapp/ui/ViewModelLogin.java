package com.jruiz.retiocusapp.ui;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.jruiz.retiocusapp.repo.Repository;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class ViewModelLogin extends ViewModel {
    //1 para volver del login al registro y 2 para ir del registro al login.
    private final MutableLiveData<Integer> botonVolverSeleccionado=new MutableLiveData<Integer>();
    //Solo se usa
    private String email;
    private String nombreUsuario;
    private String contrasenha;
    private String numTelefono;
    private final MutableLiveData<Boolean> exitoLogin= new MutableLiveData<>(), exitoRegistro= new MutableLiveData<>();

    public MutableLiveData<Boolean> getExitoLogin() {
        return exitoLogin;
    }

    public MutableLiveData<Boolean> getExitoRegistro() {
        return exitoRegistro;
    }

    public void setExitoRegistro(boolean exito) {
        this.exitoRegistro.setValue(exito);
    }

    public void setExitoLogin(boolean exito) {
        this.exitoLogin.setValue(exito);
    }

    public void iniciarAuth(){
        Repository.iniciaAuth();
    }

    public void iniciarUsuario(){
        Repository.setUsuarioAsCurrentUser();
    }

    public LiveData<Integer> getBotonVolverSeleccionado(){
        return botonVolverSeleccionado;
    }

    public void volver(int botonVolver) {
        botonVolverSeleccionado.setValue(botonVolver);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public void validarTfno(Activity actividadRegistro, PhoneAuthProvider.OnVerificationStateChangedCallbacks callback, String numero){
        PhoneAuthOptions opciones= PhoneAuthOptions.newBuilder(Repository.getAutenticacion())
                .setPhoneNumber(numero)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(actividadRegistro)
                .setCallbacks(callback)
                .build();

        Repository.getAutenticacion().setLanguageCode("es");

        PhoneAuthProvider.verifyPhoneNumber(opciones);
    }

    public void asignarNumeroTelefono(Activity actividadRegistro, PhoneAuthCredential credencialTelefono){
        Repository.getUsuarioActual().updatePhoneNumber(credencialTelefono).addOnCompleteListener(actividadRegistro, task -> {
            if(task.isSuccessful()){
                Log.d(TAG,"assignPhoneNumber:success");
                setExitoRegistro(true);
            }else{
                Log.d(TAG,"assignPhoneNumber:failure",task.getException());
                setExitoRegistro(false);
            }
        });
    }

    public void cambiarNombreUsuario(Activity actividadRegistro){
        UserProfileChangeRequest peticion= new UserProfileChangeRequest.Builder().setDisplayName(getNombreUsuario()).build();

        Repository.getUsuarioActual().updateProfile(peticion).addOnCompleteListener(actividadRegistro,task -> {
            if(task.isSuccessful()){
                Log.d(TAG,"changeDisplayName:success");
            }else{
                Log.w(TAG,"changeDisplayName:failure",task.getException());
            }
        });
    }

    //TODO: Registro Firebase
    public void registrarUsuario(Activity actividadRegistro, PhoneAuthProvider.OnVerificationStateChangedCallbacks callbackTelefono){
        Repository.getAutenticacion().createUserWithEmailAndPassword(getEmail(),getContrasenha()).addOnCompleteListener(actividadRegistro,task -> {
           if(task.isSuccessful()){
               Log.d(TAG, "registerWithEmail:success");
               Repository.setUsuarioAsCurrentUser();
               cambiarNombreUsuario(actividadRegistro);
               validarTfno(actividadRegistro,callbackTelefono,getNumTelefono());
           }else{
               Log.w(TAG, "registerWithEmail:failure", task.getException());
               Toast.makeText(actividadRegistro.getApplicationContext(), "Registro fallido.",
                       Toast.LENGTH_SHORT).show();
           }
        });
    }

    //TODO: Inicio sesion Firebase
    public void iniciarSesion(Activity actividadInicioSesion){
        Repository.getAutenticacion().signInWithEmailAndPassword(getEmail(),getContrasenha()).addOnCompleteListener(actividadInicioSesion,task -> {
            if(task.isSuccessful()){
                Log.d(TAG, "signInWithEmail:success");
                Repository.setUsuarioAsCurrentUser();
                setExitoLogin(true);
            }else{
                Log.w(TAG, "signInWithEmail:failure", task.getException());
                Toast.makeText(actividadInicioSesion.getApplicationContext(), "Authenticacion fallida.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
