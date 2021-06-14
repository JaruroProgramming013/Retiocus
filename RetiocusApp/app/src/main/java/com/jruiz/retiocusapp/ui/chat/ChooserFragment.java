package com.jruiz.retiocusapp.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jruiz.retiocusapp.R;

public class ChooserFragment extends Fragment implements View.OnClickListener {
    private Button mirarInfo, editarCredenciales, editarNombreTemas;
    private ChatViewModel chatViewModel;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.edit_current_user_choice,container,false);
        mirarInfo=(Button) vista.findViewById(R.id.peekCurrentUserButton);
        editarCredenciales=(Button) vista.findViewById(R.id.editCredentialsButton);
        editarNombreTemas=(Button) vista.findViewById(R.id.editPhotoThemesButton);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chatViewModel=new ViewModelProvider(getActivity()).get(ChatViewModel.class);
        mirarInfo.setOnClickListener(this);
        editarCredenciales.setOnClickListener(this);
        editarNombreTemas.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==mirarInfo.getId()){
            chatViewModel.seleccionar(1);
        }else if(v.getId()==editarCredenciales.getId()){
            chatViewModel.seleccionar(2);
        }else{
            chatViewModel.seleccionar(3);
        }
    }
}
