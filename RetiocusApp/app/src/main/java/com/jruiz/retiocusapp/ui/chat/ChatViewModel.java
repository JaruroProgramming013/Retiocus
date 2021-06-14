package com.jruiz.retiocusapp.ui.chat;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.jruiz.retiocusapp.entities.Tema;
import com.jruiz.retiocusapp.repo.Repository;
import com.jruiz.retiocusapp.signalr.models.CWLMQueryPackage;
import com.jruiz.retiocusapp.signalr.models.ChatDetailWLM;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ChatViewModel extends ViewModel {
    private final String uidUsuarioActual=Repository.getUsuarioActual().getUid();
    private final StorageReference baseReferencia=Repository.getServicioAlmacenamiento().getReference()
            .child("userPfps");
    private final StorageReference iconoUsuarioActual=baseReferencia
            .child(uidUsuarioActual+".png");
    private final MutableLiveData<Integer> botonSeleccionado=new MutableLiveData<>();
    private final MutableLiveData<String> uidSeleccionado=new MutableLiveData<>();
    private final MutableLiveData<List<String>> listadoTemasUsuarioActual=new MutableLiveData<>();
    private final MutableLiveData<Boolean> reloginExitoso=new MutableLiveData<>(), hayChats=new MutableLiveData<>();
    private final MutableLiveData<List<ChatDetailWLM>> listaDetalleChat=new MutableLiveData<>();
    private final List<StorageReference> listaReferenciasPFPs=new ArrayList<>();
    public MutableLiveData<List<String>> getListadoTemasUsuarioActual() {
        return listadoTemasUsuarioActual;
    }

    public MutableLiveData<List<ChatDetailWLM>> getListaDetalleChat() {
        return listaDetalleChat;
    }

    public LiveData<Boolean> getReloginExitoso() {
        return reloginExitoso;
    }

    public LiveData<Boolean> getHayChats(){return hayChats;}

    public StorageReference getIconoUsuarioActual() {
        return iconoUsuarioActual;
    }

    public String getNombreUsuarioActual(){
        return Repository.getUsuarioActual().getDisplayName();
    }

    public StorageReference getBaseReferencia() {
        return baseReferencia;
    }

    public List<StorageReference> getListaReferenciasPFPs() {
        return listaReferenciasPFPs;
    }

    public String getCorreoUsuarioActual(){
        return Repository.getUsuarioActual().getEmail();
    }

    public LiveData<Integer> getBotonSeleccionado() {
        return botonSeleccionado;
    }

    public void seleccionar(int opcion){
        botonSeleccionado.setValue(opcion);
    }

    public MutableLiveData<String> getUidSeleccionado() {
        return uidSeleccionado;
    }

    public void seleccionarChat(String uid){
        uidSeleccionado.setValue(uid);
    }

    public void fechListadoTemasUsuarioSeleccionado(){
        Repository.getTemaInterface().temasDe(uidUsuarioActual).enqueue(new Callback<List<Tema>>() {
            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                List<String> listadoTemasProvisional=new ArrayList<>();

                for (Tema temaActual:response.body()) {
                    listadoTemasProvisional.add(temaActual.getNombre());
                }

                getListadoTemasUsuarioActual().setValue(listadoTemasProvisional);
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {
                List<String> listadoTemasProvisional=new ArrayList<>();
                listadoTemasProvisional.add("-");

                getListadoTemasUsuarioActual().setValue(listadoTemasProvisional);
            }
        });
    }

    public void cambiarNombre(Activity actividadChat, String nombreNuevo){
        if(!(nombreNuevo.equals(getNombreUsuarioActual())) && !nombreNuevo.isEmpty()){
            UserProfileChangeRequest peticionCambioNombre= new UserProfileChangeRequest
                    .Builder()
                    .setDisplayName(nombreNuevo)
                    .build();
            Repository.getUsuarioActual()
                    .updateProfile(peticionCambioNombre)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "changeName:success");
                            Toast.makeText(actividadChat.getApplicationContext(),"Nombre cambiado con exito",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(actividadChat.getApplicationContext(),"Este nombre de usuario ya estaba cogido.",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void cambiarCorreo(Activity actividadChat,String correo){
        if(!(correo.equals(getCorreoUsuarioActual())) && !correo.isEmpty()){
            Repository.getUsuarioActual()
                    .updateEmail(correo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "changeCorreo:success");
                            Toast.makeText(actividadChat.getApplicationContext(),"Correo cambiado con exito",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(actividadChat.getApplicationContext(),"Este correo ya estaba cogido.",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void cambiarContrasenha(Activity actividadChat,String contrasenha){
        if(!contrasenha.isEmpty())
        Repository.getUsuarioActual()
                    .updatePassword(contrasenha)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "changeContrasenha:success");
                            Toast.makeText(actividadChat.getApplicationContext(),"Contrasenha cambiada con exito",Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    public void reautenticar(Activity actividadChat,String correo, String contrasenha){
        AuthCredential credential= EmailAuthProvider.getCredential(correo,contrasenha);
        Repository.getUsuarioActual()
                .reauthenticate(credential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User re-authenticated.");
                        reloginExitoso.setValue(true);
                        Toast.makeText(actividadChat.getApplicationContext(),"Login exitoso. Permiso concedido.",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "User not re-authenticated.");
                        Toast.makeText(actividadChat.getApplicationContext(),"Login fallido. Permiso denegado.",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void startQueryChats(){
        Repository.getConexionHubCOU().on("RecieveMessage", (listado)->{
            CWLMQueryPackage cwlmQueryPackage=new Gson().fromJson(listado,CWLMQueryPackage.class);

            if(cwlmQueryPackage.getListadoChats().get(0).getChat().getUsuarioSolicitante().equals(""))
                hayChats.postValue(false);
            listaDetalleChat.postValue(cwlmQueryPackage.getListadoChats());
                    Log.d(TAG,"fetchListado:success");
            }, String.class);

        Repository.getConexionHubCOU().start().blockingAwait();

        sendQueryChatPetition();
    }

    public void closeQueryChat(){
        Repository.getConexionHubCOU().stop();
    }

    public void sendQueryChatPetition(){
        Repository.getConexionHubCOU().send("SendMessage",Repository.getUsuarioActual().getUid());
    }

    public void prepararReferenciasAPFPs(){
        for(int i=0;i<getListaDetalleChat().getValue().size();i++){
            listaReferenciasPFPs.add(getBaseReferencia().child(getListaDetalleChat().getValue().get(i).getChat().getUsuarioSolicitado().getUid()+".png"));
        }
    }

    public String displayDeTimeInMillis(long timeInMillis){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(timeInMillis);

        int dia=calendar.get(Calendar.DAY_OF_MONTH);
        int mes=calendar.get(Calendar.MONTH)+1;
        int hora=calendar.get(Calendar.HOUR_OF_DAY);
        int minuto=calendar.get(Calendar.MINUTE);

        return dia+"/"+mes+" "+hora+":"+minuto;
    }
}
