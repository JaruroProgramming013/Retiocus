package com.jruiz.retiocusapp.ui.newChat;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.storage.StorageReference;
import com.jruiz.retiocusapp.entities.Chat;
import com.jruiz.retiocusapp.entities.Tema;
import com.jruiz.retiocusapp.repo.Repository;
import com.jruiz.retiocusapp.signalr.models.UWCTQueryPackage;
import com.jruiz.retiocusapp.signalr.models.UserDetail;
import com.jruiz.retiocusapp.signalr.models.UserDetailWCT;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class NewChatViewModel extends ViewModel {
    private final MutableLiveData<List<UserDetailWCT>> listaUsuariosObservable=new MutableLiveData<>();
    private final StorageReference storageReference = Repository.getServicioAlmacenamiento().getReference().child("userPfps");
    private final List<StorageReference> storageReferenceList=new ArrayList<>();
    private final MutableLiveData<String> usuarioSeleccionado=new MutableLiveData<>();
    private final MutableLiveData<List<String>> listadoTemasUsuarioSeleccionado=new MutableLiveData<>();
    private final MutableLiveData<Boolean> nuevoChatAnhadido=new MutableLiveData<>(),
    hayResultados=new MutableLiveData<>();

    public LiveData<String> getUsuarioSeleccionado(){
        return usuarioSeleccionado;
    }

    public MutableLiveData<Boolean> getHayResultados() {
        return hayResultados;
    }

    public void seleccionarUsuario(String uid, String nombre){
        Repository.setUsuarioDesplegadoEnDetalle(new UserDetail(uid,nombre));

        usuarioSeleccionado.setValue(uid);
    }

    public StorageReference getStorageReference() {
        return storageReference;
    }

    public List<StorageReference> getStorageReferenceList() {
        return storageReferenceList;
    }

    public void prepararReferenciasAPFPs(){
        for(int i=0;i<getListaUsuariosObservable().getValue().size();i++){
            getStorageReferenceList().add(getStorageReference().child(getListaUsuariosObservable().getValue().get(i).getUsuario().getUid()+".png"));
        }
    }

    public LiveData<List<UserDetailWCT>> getListaUsuariosObservable() {
        return listaUsuariosObservable;
    }

    public void startQueryNewChat(){
        Repository.getConexionHubUWCT().on("RecieveMessage", (listado)->{
            if(listado.getListadoUsuariosDetalladosWCT().get(0).getUsuario().getUid().equals(""))
                getHayResultados().postValue(false);
            listaUsuariosObservable.postValue(listado.getListadoUsuariosDetalladosWCT());
            Log.d(TAG,"fetchListado:success");
        } , UWCTQueryPackage.class);

        Repository.getConexionHubUWCT().start().blockingAwait();

        sendQueryNewChatPetition();
    }

    public void closeQueryNewChat(){
        Repository.getConexionHubUWCT().stop();
    }

    public void sendQueryNewChatPetition(){
        Repository.getConexionHubUWCT().send("SendMessage",Repository.getUsuarioActual().getUid());
    }

    public void restartList(){
        if(getListaUsuariosObservable().getValue()!=null)
            getListaUsuariosObservable().getValue().clear();

        sendQueryNewChatPetition();
    }

    public UserDetail getUsuarioDesplegado(){
        return Repository.getUsuarioDesplegadoEnDetalle();
    }

    public StorageReference getReferenciaFotoUsuarioDesplegado(){
        return getStorageReference().child(getUsuarioSeleccionado().getValue()+".png");
    }

    public MutableLiveData<List<String>> getListadoTemasUsuarioSeleccionado() {
        return listadoTemasUsuarioSeleccionado;
    }

    public void fechListadoTemasUsuarioSeleccionado(){
        Repository.getTemaInterface().temasDe(getUsuarioSeleccionado().getValue()).enqueue(new Callback<List<Tema>>() {
            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                List<String> listadoTemasProvisional=new ArrayList<>();

                for (Tema temaActual:response.body()) {
                    listadoTemasProvisional.add(temaActual.getNombre());
                }

                getListadoTemasUsuarioSeleccionado().setValue(listadoTemasProvisional);
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {
                List<String> listadoTemasProvisional=new ArrayList<>();
                listadoTemasProvisional.add("-");

                getListadoTemasUsuarioSeleccionado().setValue(listadoTemasProvisional);
            }
        });
    }

    public LiveData<Boolean> getNuevoChatAnhadido() {
        return nuevoChatAnhadido;
    }

    public void anhadirChat(){
        Repository.getPostInterface()
                .postChat(
                        new Chat(Repository.getUsuarioActual().getUid(),
                                getUsuarioSeleccionado().getValue()))
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code()==200){
                            Log.d(TAG,"postChat:success");
                            nuevoChatAnhadido.setValue(true);
                        }
                   }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d(TAG,"postChat:failure");
                    }
        });
    }
}
