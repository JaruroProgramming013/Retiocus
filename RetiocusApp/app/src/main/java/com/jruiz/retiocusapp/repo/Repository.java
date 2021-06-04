package com.jruiz.retiocusapp.repo;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jruiz.retiocusapp.MainActivity;
import com.jruiz.retiocusapp.retrofit.models.TemasListWithPetitioner;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class Repository {
    private static FirebaseAuth autenticacion;
    private static FirebaseUser usuarioActual;
    private static TemasListWithPetitioner listadoTemasUsuarioActual=new TemasListWithPetitioner("",new ArrayList<>());
    private static final String urlApi="https://retiocusapi.azurewebsites.net/api",
    urlWebApps="https://retiocuswebapps.azurewebsites.net";

    public static String getUrlApi() {
        return urlApi;
    }

    public static String getUrlWebApps() {
        return urlWebApps;
    }

    public static TemasListWithPetitioner getListadoTemasUsuarioActual() {
        return listadoTemasUsuarioActual;
    }

    public static void setListadoTemasUsuarioActual(TemasListWithPetitioner listadoTemasUsuarioActual) {
        Repository.listadoTemasUsuarioActual = listadoTemasUsuarioActual;
    }

    public static FirebaseAuth getAutenticacion() {
        return autenticacion;
    }

    public static void setAutenticacion(FirebaseAuth autenticacion) {
        Repository.autenticacion = autenticacion;
    }

    public static FirebaseUser getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(FirebaseUser usuarioActual) {
        Repository.usuarioActual = usuarioActual;
    }

    public static void iniciaAuth(){
        setAutenticacion(FirebaseAuth.getInstance());
    }
    public static void setUsuarioAsCurrentUser(){
        setUsuarioActual(getAutenticacion().getCurrentUser());
    }

}
