package com.jruiz.retiocusapp.ui.slider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.StorageReference;
import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.activities.ChatActivity;
import com.jruiz.retiocusapp.activities.SliderActivity;
import com.jruiz.retiocusapp.entities.Tema;
import com.jruiz.retiocusapp.repo.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class    SliderViewModel extends ViewModel {
    private final MutableLiveData<List<Tema>> listadoTemas = new MutableLiveData<>();
    private final MutableLiveData<List<Tema>> listadoTemasUsuario = new MutableLiveData<>();
    private final List<Tema>listadoTemasUsuarioRenovado = new ArrayList<>();
    private final StorageReference storageReference = Repository.getServicioAlmacenamiento().getReference().child("userPfps");
    private final MutableLiveData<Uri> imagenSeleccionada = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successSetup =new MutableLiveData<>();
    private boolean imagenCompletada=false, temasCompletados=false;

    public LiveData<Boolean> getSuccessSetup() {
        return successSetup;
    }

    public void recuperarTemas() {
        Repository.getTemaInterface().todosLosTemas().enqueue(new Callback<List<Tema>>() {
            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                if (response.body() != null && call.request().url().pathSize() == 2) {
                    listadoTemas.setValue(new ArrayList<>(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {
            }
        });
    }

    public void finalizarSetup(boolean success){
        successSetup.setValue(success);
    }

    public StorageReference getStorageReference() {
        return storageReference;
    }

    public LiveData<Uri> getPfp() {
        return imagenSeleccionada;
    }

    public void setPfp(Uri imagenNueva) {
        this.imagenSeleccionada.setValue(imagenNueva);
    }

    public void recuperarTemasUsuario() {
        String uidUsuarioActual = Repository.getUsuarioActual().getUid();

        Repository.getTemaInterface().temasDe(uidUsuarioActual).enqueue(new Callback<List<Tema>>() {
            @Override
            public void onResponse(Call<List<Tema>> call, Response<List<Tema>> response) {
                List<Tema> listadoProvisional=new ArrayList<>();
                if (response.body() != null && call.request().url().pathSize() > 2) {
                    listadoProvisional.addAll(response.body());
                }
                listadoTemasUsuario.setValue(listadoProvisional);
            }

            @Override
            public void onFailure(Call<List<Tema>> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Tema>> getListadoTemas() {
        return listadoTemas;
    }

    public LiveData<List<Tema>> getListadoTemasUsuario() {
        return listadoTemasUsuario;
    }

    public List<Tema> getListadoTemasUsuarioRenovado() {
        return listadoTemasUsuarioRenovado;
    }


    public void subirDatosYCambiarAChat(Activity actividadSlider) {
        try {
            if (!getListadoTemasUsuarioRenovado().isEmpty()) {
                if (actividadSlider instanceof SliderActivity)
                    registrarUsuario(actividadSlider);
                else {
                    StorageReference referencePFPCurrentUser = getStorageReference().child(Repository.getUsuarioActual().getUid() + ".png");
                    subirImagen(referencePFPCurrentUser, actividadSlider);
                    subirListadoTemas(Repository.getUsuarioActual().getUid(),actividadSlider);
                    actividadSlider.startActivity(new Intent(actividadSlider, ChatActivity.class));
                }

            } else {
                Log.w(TAG, "newPreferencesListIsEmpty");
                Toast.makeText(actividadSlider.getApplicationContext(), "Por favor, marca al menos una preferencia.", Toast.LENGTH_SHORT).show();
                finalizarSetup(false);
            }
        } catch (FileNotFoundException exceptionFNF) {
            Log.wtf(TAG, "fnfExceptionOnPFP", exceptionFNF);
            Toast.makeText(actividadSlider.getApplicationContext(), "No se pudo encontrar la imagen. Seleccione otra.", Toast.LENGTH_LONG).show();
            finalizarSetup(false);
        } catch (IOException exceptionIO) {
            Log.wtf(TAG, "IOExceptionOnPFP", exceptionIO);
            Toast.makeText(actividadSlider.getApplicationContext(), "Error comprimiendo la imagen. Seleccione otra.", Toast.LENGTH_LONG).show();
            finalizarSetup(false);
        }
    }

    public void cambiarNombreUsuario(Activity actividadRegistro) {
        UserProfileChangeRequest peticion = new UserProfileChangeRequest.Builder().setDisplayName(Repository.getCampoNombreRegister()).build();

        Repository.getUsuarioActual().updateProfile(peticion).addOnCompleteListener(actividadRegistro, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "changeDisplayName:success");
            } else {
                Log.w(TAG, "changeDisplayName:failure", task.getException());
            }
        });
    }

    public void registrarUsuario(Activity actividadRegistro) {
        Repository.getAutenticacion().createUserWithEmailAndPassword(Repository.getCampoEmailRegister(), Repository.getCampoPasswordRegister()).addOnCompleteListener(actividadRegistro, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "registerWithEmail:success");
                Repository.setUsuarioAsCurrentUser();
                cambiarNombreUsuario(actividadRegistro);
                String currentUserUid = Repository.getUsuarioActual().getUid();
                Repository.getPostInterface().postUser(currentUserUid).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(actividadRegistro.getApplicationContext(), "Registro completado.",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.w(TAG, "registerWithEmail:failure:api", task.getException());
                        Toast.makeText(actividadRegistro.getApplicationContext(), "Registro fallido. Este correo electrónico esta en uso.",
                                Toast.LENGTH_SHORT).show();
                        finalizarSetup(false);
                    }
                });

                StorageReference referencePFPCurrentUser = getStorageReference().child(currentUserUid + ".png");

                subirListadoTemas(currentUserUid,actividadRegistro);
                try {
                    subirImagen(referencePFPCurrentUser, actividadRegistro);
                } catch (IOException exception) {
                    Log.wtf(TAG, "IOExceptionOnPFP", exception);
                    Toast.makeText(actividadRegistro.getApplicationContext(), "Error comprimiendo la imagen. Seleccione otra.", Toast.LENGTH_LONG).show();
                }
            } else {
                Log.w(TAG, "registerWithEmail:failure:firebase", task.getException());
                Toast.makeText(actividadRegistro.getApplicationContext(), "Registro fallido. Ya existe un usuario con ese correo.",
                        Toast.LENGTH_SHORT).show();
                finalizarSetup(false);
            }
        });
    }

    public void subirImagen(StorageReference reference, Activity actividadImagen) throws IOException {
        Uri imagenPredeterminada = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(actividadImagen.getResources().getResourcePackageName(R.drawable.retiocus_main_pfp))
                .appendPath(actividadImagen.getResources().getResourceTypeName(R.drawable.retiocus_main_pfp))
                .appendPath(actividadImagen.getResources().getResourceEntryName(R.drawable.retiocus_main_pfp))
                .build();
        Bitmap bitmap;
        File archivo;
        if (!getPfp().getValue().getPath().equals(imagenPredeterminada.getPath())) {
            bitmap=MediaStore.Images.Media.getBitmap(actividadImagen.getContentResolver(), getPfp().getValue());
            archivo = new File(actividadImagen.getFilesDir() + Repository.getUsuarioActual().getUid());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(archivo));
            reference.putFile(Uri.fromFile(archivo)).addOnSuccessListener(taskSnapshot -> {
                Log.d(TAG, "cambiarPFP:success");
                Toast.makeText(actividadImagen.getApplicationContext(), "Foto cambiada", Toast.LENGTH_SHORT).show();
                imagenCompletada=true;
                if(temasCompletados)
                    finalizarSetup(true);
            }).addOnFailureListener(e -> {
                Log.e(TAG, "cambiarPFP:failure", e);
                Toast.makeText(actividadImagen.getApplicationContext(), "Error en el servidor de cambiar la imagen. Intentalo de nuevo mast arde.", Toast.LENGTH_LONG).show();
            });
        }else{
            imagenCompletada=true;
            if(temasCompletados)
                finalizarSetup(true);
        }
    }

    public void subirListadoTemas(String uid,Activity actividadTemas){
        List<Integer> listadoTemasPrefPost = new ArrayList<>();
        List<Integer> listadoTemasPrefDelete = new ArrayList<>();

        if(getListadoTemasUsuario().getValue()!=null) {
            for (Tema temaMarcado : getListadoTemasUsuarioRenovado()) {
                if (!getListadoTemasUsuario().getValue().contains(temaMarcado)) {
                    listadoTemasPrefPost.add(temaMarcado.getID());
                }
            }
            for (Tema temaOriginalUsuario : getListadoTemasUsuario().getValue()) {
                if (!getListadoTemasUsuarioRenovado().contains(temaOriginalUsuario)) {
                    listadoTemasPrefDelete.add(temaOriginalUsuario.getID());
                }
            }
        }
        else
            for (Tema temaMarcado : getListadoTemasUsuarioRenovado()) {
                listadoTemasPrefPost.add(temaMarcado.getID());
            }

        if (!listadoTemasPrefPost.isEmpty())
            Repository.getPostInterface().postPreferencias(uid, listadoTemasPrefPost).enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200) {
                        Log.d(TAG, "postPreferences:success");
                        Toast.makeText(actividadTemas.getApplicationContext(), "Los temas que marcaste han sido añadidos a tus preferencias", Toast.LENGTH_SHORT).show();
                        temasCompletados=true;
                        if(imagenCompletada)
                            finalizarSetup(true);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "postPreferences:failure", t);
                    Toast.makeText(actividadTemas.getApplicationContext(), "Error al añadir temas", Toast.LENGTH_SHORT).show();
                }

            });

        if (!listadoTemasPrefDelete.isEmpty())
            Repository.getPreferenciaInterface().deletePreferenciasDe(uid, listadoTemasPrefDelete).enqueue(new Callback<Void>() {

                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200) {
                        Log.d(TAG, "deletePreferences:success");
                        Toast.makeText(actividadTemas.getApplicationContext(), "Los temas que desmarcaste han sido eliminados de tus preferencias", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(TAG, "deletePreferences:failure", t);
                    Toast.makeText(actividadTemas.getApplicationContext(), "Error al eliminar temas", Toast.LENGTH_SHORT).show();
                }

            });
    }

}
