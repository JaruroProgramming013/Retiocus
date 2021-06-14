package com.jruiz.retiocusapp.ui.newChat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.activities.ChatActivity;
import com.jruiz.retiocusapp.activities.NewChatActivity;
import com.jruiz.retiocusapp.glide.modules.GlideApp;
import com.jruiz.retiocusapp.repo.Repository;
import com.jruiz.retiocusapp.ui.chat.ChatViewModel;
import com.jruiz.retiocusapp.ui.chatting.ChattingViewModel;

import java.util.List;

public class DetailFragment extends Fragment {

    private ImageView icono;
    private TextView nombreUsuario, correoCurrentUser, coleccionTemas, uidUsuario;
    private Button anhadirChat;
    private ChatViewModel chatViewModel;
    private ChattingViewModel chattingViewModel;
    private NewChatViewModel newChatViewModel;

    @Override
    public void onResume() {
        super.onResume();
        if(coleccionTemas.getText()!=null){
            coleccionTemas.setText("");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_detail,container,false);
        icono=(ImageView) view.findViewById(R.id.iconoUsuarioDetail);
        nombreUsuario=(TextView) view.findViewById(R.id.nombreUsuarioDetail);
        correoCurrentUser=(TextView) view.findViewById(R.id.correoCurrentUser);
        coleccionTemas=(TextView) view.findViewById(R.id.coleccionTemas);
        uidUsuario=(TextView) view.findViewById(R.id.uidUsuarioDetail);
        anhadirChat=(Button) view.findViewById(R.id.botonAnhadirChat);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getActivity() instanceof NewChatActivity){
            newChatViewModel=new ViewModelProvider(getActivity()).get(NewChatViewModel.class);
            anhadirChat.setVisibility(View.VISIBLE);
            nombreUsuario.setText((String) newChatViewModel.getUsuarioDesplegado().getUsername());
            GlideApp.with(getContext()).load(newChatViewModel.getReferenciaFotoUsuarioDesplegado()).error(R.drawable.retiocus_main_pfp).circleCrop().into(icono);
            uidUsuario.setText(newChatViewModel.getUsuarioSeleccionado().getValue());
            Observer<List<String>> observadorTemas=new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    for(String nombreTema:strings){
                        if(strings.size()-strings.indexOf(nombreTema)==1)
                            nombreTema=nombreTema+".";
                        else
                            nombreTema=nombreTema+", ";
                        coleccionTemas.setText(coleccionTemas.getText().toString()+nombreTema);
                    }
                }
            };
            newChatViewModel.getListadoTemasUsuarioSeleccionado().observe(requireActivity(),observadorTemas);
            newChatViewModel.fechListadoTemasUsuarioSeleccionado();
            anhadirChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newChatViewModel.anhadirChat();
                }
            });
        }else if(getActivity() instanceof ChatActivity){
            chatViewModel=new ViewModelProvider(getActivity()).get(ChatViewModel.class);
            nombreUsuario.setText(chatViewModel.getNombreUsuarioActual());
            GlideApp.with(getContext()).load(chatViewModel.getIconoUsuarioActual()).error(R.drawable.retiocus_main_pfp).circleCrop().into(icono);
            correoCurrentUser.setText(chatViewModel.getCorreoUsuarioActual());
            correoCurrentUser.setVisibility(View.VISIBLE);
            Observer<List<String>> observadorTemas=new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    for(String nombreTema:strings){
                        if(strings.size()-strings.indexOf(nombreTema)==1)
                            nombreTema=nombreTema+".";
                        else
                            nombreTema=nombreTema+", ";
                        coleccionTemas.setText(coleccionTemas.getText().toString()+nombreTema);
                    }
                }
            };
            chatViewModel.getListadoTemasUsuarioActual().observe(requireActivity(),observadorTemas);
            chatViewModel.fechListadoTemasUsuarioSeleccionado();
        }else{
            chattingViewModel=new ViewModelProvider(getActivity()).get(ChattingViewModel.class);
            nombreUsuario.setText(Repository.getUsuarioDesplegadoEnDetalle().getUsername());
            GlideApp.with(getContext()).load(chattingViewModel.getIconoOtro()).error(R.drawable.retiocus_main_pfp).circleCrop().into(icono);
            Observer<List<String>> observadorTemas=new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    for(String nombreTema:strings){
                        if(strings.size()-strings.indexOf(nombreTema)==1)
                            nombreTema=nombreTema+".";
                        else
                            nombreTema=nombreTema+", ";
                        coleccionTemas.setText(coleccionTemas.getText().toString()+nombreTema);
                    }
                }
            };
            chattingViewModel.getListadoTemasOtro().observe(requireActivity(),observadorTemas);
            chattingViewModel.fechListadoTemasUsuarioSeleccionado();
        }
    }
}
