package com.jruiz.retiocusapp.ui.chatting;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.storage.StorageReference;
import com.jruiz.retiocusapp.entities.Tema;
import com.jruiz.retiocusapp.repo.Repository;
import com.jruiz.retiocusapp.signalr.models.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChattingViewModel extends ViewModel {
    private final MutableLiveData<List<Message>> listadoMensajes=new MutableLiveData<>();
    private final MutableLiveData<String> uidSeleccionado=new MutableLiveData<>();
    private final StorageReference iconoOtro= Repository.getServicioAlmacenamiento().getReference()
            .child("userPfps").child(uidSeleccionado.getValue()+".png");
    private final MutableLiveData<Boolean> verDetalles=new MutableLiveData<>();
    private final MutableLiveData<List<String>> listadoTemasOtro=new MutableLiveData<>();

    public MutableLiveData<List<String>> getListadoTemasOtro() {
        return listadoTemasOtro;
    }

    public MutableLiveData<Boolean> getVerDetalles() {
        return verDetalles;
    }

    public void verDetalles(){
        verDetalles.setValue(true);
    }

    public MutableLiveData<List<Message>> getListadoMensajes() {
        return listadoMensajes;
    }

    public StorageReference getIconoOtro() {
        return iconoOtro;
    }

    public MutableLiveData<String> getUidSeleccionado() {
        return uidSeleccionado;
    }

    public void fechListadoTemasUsuarioSeleccionado(){
        Repository.getTemaInterface().temasDe(getUidSeleccionado().getValue()).enqueue(new Callback<List<Tema>>() {
            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                List<String> listadoTemasProvisional=new ArrayList<>();

                for (Tema temaActual:response.body()) {
                    listadoTemasProvisional.add(temaActual.getNombre());
                }

                getListadoTemasOtro().setValue(listadoTemasProvisional);
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {
                List<String> listadoTemasProvisional=new ArrayList<>();
                listadoTemasProvisional.add("-");

                getListadoTemasOtro().setValue(listadoTemasProvisional);
            }
        });
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
