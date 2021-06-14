package com.jruiz.retiocusapp.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.glide.modules.GlideApp;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChatListFragment extends Fragment {

    private ChatViewModel viewModel;
    private ImageView iconoUsuario;
    private TextView nombreUsuario;
    private Toolbar barraUsuario;
    private RecyclerView listaChats;
    private ListaChatsAdapter adaptador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.chat_list_fragment,container,false);
        listaChats=(RecyclerView) vista.findViewById(R.id.listadoChats);
        iconoUsuario=(ImageView)vista.findViewById(R.id.iconoUsuario);
        nombreUsuario=(TextView)vista.findViewById(R.id.nombreUsuarioDisplay);
        barraUsuario=(Toolbar)vista.findViewById(R.id.toolbar);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel=new ViewModelProvider(getActivity()).get(ChatViewModel.class);
        adaptador=new ListaChatsAdapter();
        listaChats.setAdapter(adaptador);
        listaChats.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        GlideApp
                .with(getContext())
                .load(viewModel.getIconoUsuarioActual())
                .error(R.drawable.retiocus_main_pfp)
                .circleCrop()
                .into(iconoUsuario);

        nombreUsuario.setText(viewModel.getNombreUsuarioActual());

        barraUsuario.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                    viewModel.seleccionar(4);
                return false;
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.closeQueryChat();
    }

    private class ListaChatsAdapter extends RecyclerView.Adapter<ListaChatsAdapter.ListaChatsHolder>{

        @NonNull
        @Override
        public ListaChatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View vista=LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_row,parent,false);
            return new ListaChatsHolder(vista);
        }

        @Override
        public void onBindViewHolder(@NonNull ListaChatsHolder holder, int position) {
            if(viewModel.getListaDetalleChat().getValue()!=null){
                GlideApp
                        .with(getContext())
                        .load(viewModel.getListaReferenciasPFPs().get(position))
                        .error(R.drawable.retiocus_main_pfp)
                        .circleCrop()
                        .into(holder.getIconoUsuario());
                holder.getNombreUsuario().setText(viewModel.getListaDetalleChat().getValue().get(position).getChat().getUsuarioSolicitado().getUsername());
                holder.getHoraEnvio().setText(viewModel.displayDeTimeInMillis(viewModel.getListaDetalleChat().getValue()
                        .get(position)
                        .getUltimoMensaje()
                        .getFechaEnvio()));
                holder.getUidUsuario().setText(viewModel.getListaDetalleChat().getValue().get(position).getChat().getUsuarioSolicitado().getUid());
                holder.getUltimoMensaje().setText(viewModel.getListaDetalleChat().getValue().get(position).getUltimoMensaje().getCuerpo());
                holder.getFilaPadre().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.seleccionarChat((String) holder.getUidUsuario().getText());
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return viewModel.getListaDetalleChat().getValue().size();
        }

        private class ListaChatsHolder extends RecyclerView.ViewHolder{
            private final ImageView iconoUsuario;
            private final RelativeLayout filaPadre;
            private final TextView nombreUsuario, ultimoMensaje, horaEnvio, uidUsuario;
            public ListaChatsHolder(@NonNull View itemView)
            {
                super(itemView);
                iconoUsuario=(ImageView)itemView.findViewById(R.id.iconoUsuarioChatRow);
                nombreUsuario=(TextView)itemView.findViewById(R.id.nombreUsuarioChatRow);
                ultimoMensaje=(TextView)itemView.findViewById(R.id.ultimoMensaje);
                horaEnvio=(TextView)itemView.findViewById(R.id.horaEnvio);
                uidUsuario=(TextView)itemView.findViewById(R.id.uidUsuarioChat);
                filaPadre=(RelativeLayout) itemView.findViewById(R.id.filaChat);
            }

            public ImageView getIconoUsuario() {
                return iconoUsuario;
            }

            public RelativeLayout getFilaPadre() {
                return filaPadre;
            }

            public TextView getNombreUsuario() {
                return nombreUsuario;
            }

            public TextView getUltimoMensaje() {
                return ultimoMensaje;
            }

            public TextView getHoraEnvio() {
                return horaEnvio;
            }

            public TextView getUidUsuario() {
                return uidUsuario;
            }
        }
    }
}
