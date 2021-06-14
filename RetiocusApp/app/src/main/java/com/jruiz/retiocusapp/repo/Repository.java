package com.jruiz.retiocusapp.repo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.jruiz.retiocusapp.retrofit.interfaces.PostInterface;
import com.jruiz.retiocusapp.retrofit.interfaces.PreferenciaInterface;
import com.jruiz.retiocusapp.retrofit.interfaces.TemaInterface;
import com.jruiz.retiocusapp.signalr.models.UserDetail;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Repository {
    private static FirebaseAuth autenticacion;
    private static FirebaseUser usuarioActual;
    private static final FirebaseStorage servicioAlmacenamiento=FirebaseStorage.getInstance();
    private static final String urlApi="https://retiocusapi.azurewebsites.net/", urlWebUtils="https://retiocuswebutils.azurewebsites.net/";
    private static final Retrofit retrofit=new Retrofit.Builder().baseUrl(urlApi).addConverterFactory(GsonConverterFactory.create()).build();
    private static final PostInterface postInterface=retrofit.create(PostInterface.class);
    private static final TemaInterface temaInterface=retrofit.create(TemaInterface.class);
    private static final PreferenciaInterface preferenciaInterface=retrofit.create(PreferenciaInterface.class);
    private static String campoNombreRegister="", campoEmailRegister="", campoPasswordRegister="";
    private static final HubConnection conexionHubServicioChats= HubConnectionBuilder.create(urlWebUtils+"chatHub").build(),
            conexionHubUWCT=HubConnectionBuilder.create(urlWebUtils+"uwctHub").build(),
            conexionHubCOU=HubConnectionBuilder.create(urlWebUtils+"couHub").build();
    private static UserDetail usuarioDesplegadoEnDetalle=new UserDetail();
    private static String uidUsuarioChatting="";

    public static PostInterface getPostInterface() {
        return postInterface;
    }

    public static TemaInterface getTemaInterface() {
        return temaInterface;
    }

    public static FirebaseAuth getAutenticacion() {
        return autenticacion;
    }

    private static void setAutenticacion(FirebaseAuth autenticacion) {
        Repository.autenticacion = autenticacion;
    }

    public static FirebaseUser getUsuarioActual() {
        return usuarioActual;
    }

    private static void setUsuarioActual(FirebaseUser usuarioActual) {
        Repository.usuarioActual = usuarioActual;
    }

    public static String getCampoNombreRegister() {
        return campoNombreRegister;
    }

    public static void setCampoNombreRegister(String campoNombreRegister) {
        Repository.campoNombreRegister = campoNombreRegister;
    }

    public static String getCampoEmailRegister() {
        return campoEmailRegister;
    }

    public static void setCampoEmailRegister(String campoEmailRegister) {
        Repository.campoEmailRegister = campoEmailRegister;
    }

    public static String getCampoPasswordRegister() {
        return campoPasswordRegister;
    }

    public static void setCampoPasswordRegister(String campoPasswordRegister) {
        Repository.campoPasswordRegister = campoPasswordRegister;
    }

    public static void signOutCurrentUser(){
        usuarioActual=null;
    }

    public static FirebaseStorage getServicioAlmacenamiento() {
        return servicioAlmacenamiento;
    }

    public static PreferenciaInterface getPreferenciaInterface() {
        return preferenciaInterface;
    }



    public static void iniciaAuth(){
        setAutenticacion(FirebaseAuth.getInstance());
    }

    public static void setUsuarioAsCurrentUser(){
        setUsuarioActual(getAutenticacion().getCurrentUser());
    }

    public static HubConnection getConexionHubServicioChats() {
        return conexionHubServicioChats;
    }

    public static HubConnection getConexionHubUWCT() {
        return conexionHubUWCT;
    }

    public static HubConnection getConexionHubCOU() {
        return conexionHubCOU;
    }

    public static UserDetail getUsuarioDesplegadoEnDetalle() {
        return usuarioDesplegadoEnDetalle;
    }

    public static void setUsuarioDesplegadoEnDetalle(UserDetail usuarioDesplegadoEnDetalle) {
        Repository.usuarioDesplegadoEnDetalle = usuarioDesplegadoEnDetalle;
    }
}
