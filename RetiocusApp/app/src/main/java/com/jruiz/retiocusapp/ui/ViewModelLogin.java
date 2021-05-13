package com.jruiz.retiocusapp.ui;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ViewModelLogin extends ViewModel {
    private FirebaseAuth autenticacion;
    private FirebaseUser usuarioActual;
    //1 para login, 2 para registro.
    private Integer botonSeleccionado;
    //Solo se usa
    private String emailRegistro;
    private String nombreUsuario;
    private String contrasenha;
    private String numTelefono;

    public FirebaseUser getUser(){
        return usuarioActual;
    }

    public FirebaseAuth getAuth(){
        return autenticacion;
    }

    public void setUser(FirebaseUser usuario){
        usuarioActual=usuario;
    }

    public void setAuth(FirebaseAuth instancia){
        autenticacion=instancia;
    }
}
