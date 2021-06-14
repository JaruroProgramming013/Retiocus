package com.jruiz.retiocusapp.ui.loginRegister;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jruiz.retiocusapp.repo.Repository;

import static android.content.ContentValues.TAG;

public class ViewModelLogin extends ViewModel {
    //1 para volver del login al registro y 2 para ir del registro al login.
    private final MutableLiveData<Integer> botonVolverSeleccionado=new MutableLiveData<>();
    //Solo se usa
    private String email;
    private String nombreUsuario;
    private String contrasenha;
    private final MutableLiveData<Boolean> exitoLogin= new MutableLiveData<>(), exitoRegistro= new MutableLiveData<>();
    private final MutableLiveData<Boolean> cargando=new MutableLiveData<>();

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

    public boolean checkUserOnline(){
        return Repository.getUsuarioActual()!=null;
    }

    public LiveData<Boolean> getCargando(){
        return cargando;
    }

    public void comprobarCargando(boolean cargando){
        this.cargando.setValue(cargando);
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

    public void iniciarSesion(Activity actividadInicioSesion){
        Repository.getAutenticacion().signInWithEmailAndPassword(getEmail(),getContrasenha()).addOnCompleteListener(actividadInicioSesion,task -> {
            if(task.isSuccessful()){
                Log.d(TAG, "signInWithEmail:success");
                Repository.setUsuarioAsCurrentUser();
                setExitoLogin(true);
                comprobarCargando(false);
            }else{
                Log.w(TAG, "signInWithEmail:failure", task.getException());
                Toast.makeText(actividadInicioSesion.getApplicationContext(), "Autenticacion fallida.",
                        Toast.LENGTH_SHORT).show();
                comprobarCargando(false);
            }
        });
    }

    public void publicarCamposRegister(){
        Repository.setCampoEmailRegister(getEmail());
        Repository.setCampoNombreRegister(getNombreUsuario());
        Repository.setCampoPasswordRegister(getContrasenha());

        setExitoRegistro(true);
    }
}
