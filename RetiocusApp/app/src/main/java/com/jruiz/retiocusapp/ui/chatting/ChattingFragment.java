package com.jruiz.retiocusapp.ui.chatting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.glide.modules.GlideApp;

public class ChattingFragment extends Fragment {
    private EditText textoMensaje;
    private Button botonEnviar;
    private RecyclerView listaMensajes;
    private ImageView iconoOtroUsuario;
    private Toolbar barraOtroUsuario;
    private ChattingViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.chatting_fragment,container,false);
        textoMensaje=view.findViewById(R.id.textoMensaje);
        botonEnviar=view.findViewById(R.id.botonEnviar);
        listaMensajes=view.findViewById(R.id.listaMensajes);
        iconoOtroUsuario=view.findViewById(R.id.iconoOtroUsuario);
        barraOtroUsuario=view.findViewById(R.id.toolbarChatting);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel=new ViewModelProvider(getActivity()).get(ChattingViewModel.class);
        AdaptadorMensaje adaptadorMensaje=new AdaptadorMensaje();
        listaMensajes.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true));
        listaMensajes.setAdapter(adaptadorMensaje);
        GlideApp.with(getContext()).load(viewModel.getIconoOtro()).error(R.drawable.retiocus_main_pfp).circleCrop().into(iconoOtroUsuario);
        botonEnviar.setEnabled(false);
        final TextWatcher observadorTexto=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if( textoMensaje.getText().toString().isEmpty() || textoMensaje.getText().toString().trim().isEmpty()){
                    botonEnviar.setEnabled(true);
                }else{
                    botonEnviar.setEnabled(false);
                }
            }
        };
        textoMensaje.addTextChangedListener(observadorTexto);
        barraOtroUsuario.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                viewModel.verDetalles();
                return false;
            }
        });
    }

    private class AdaptadorMensaje extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public int getItemCount() {
            return viewModel.getListadoMensajes().getValue().size();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            RecyclerView.ViewHolder viewHolder;
            switch (viewType){
                case 1:
                    View vistaUsuario=inflater.inflate(R.layout.mensaje_usuario,parent,false);
                    viewHolder=new HolderMensajeUsuario(vistaUsuario);
                    break;
                case 2:
                    View vistaOtro=inflater.inflate(R.layout.mensaje_otro,parent,false);
                    viewHolder=new HolderMensajeOtro(vistaOtro);
                    break;
                default:
                    View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                    viewHolder = new RecyclerViewSimpleTextViewHolder(v);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                switch (holder.getItemViewType()){
                    case 1:
                        HolderMensajeUsuario holderMensajeUsuario=(HolderMensajeUsuario)holder;
                        configurarHolderUsuario(holderMensajeUsuario, position);
                        break;
                    case 2:
                        HolderMensajeOtro holderMensajeOtro=(HolderMensajeOtro)holder;
                        configurarHolderOtro(holderMensajeOtro, position);
                        break;
                    default:
                        RecyclerViewSimpleTextViewHolder vh=(RecyclerViewSimpleTextViewHolder) holder;
                        configureDefaultViewHolder(vh,position);
                        break;
                }
        }

        private void configurarHolderUsuario(HolderMensajeUsuario vh, int position) {
            vh.getTexto().setText((CharSequence) viewModel.getListadoMensajes().getValue().get(position).getCuerpo());
            vh.getFechaHoraEnvio().setText((CharSequence)viewModel.displayDeTimeInMillis(viewModel.getListadoMensajes().getValue().get(position).getFechaEnvio()));
        }

        private void configurarHolderOtro(HolderMensajeOtro vh, int position) {
            vh.getTexto().setText((CharSequence) viewModel.getListadoMensajes().getValue().get(position).getCuerpo());
            vh.getFechaHoraEnvio().setText((CharSequence)viewModel.displayDeTimeInMillis(viewModel.getListadoMensajes().getValue().get(position).getFechaEnvio()));
        }

        private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position) {
            vh.getLabel().setText((CharSequence) viewModel.getListadoMensajes().getValue().get(position).getCuerpo());
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }

        private class HolderMensajeUsuario extends RecyclerView.ViewHolder{
            private final EditText fechaHoraEnvio, texto;
            public HolderMensajeUsuario(@NonNull View itemView) {
                super(itemView);
                fechaHoraEnvio=itemView.findViewById(R.id.fechaHoraEnvioMensajeUsuario);
                texto=itemView.findViewById(R.id.cuerpoMensajeUsuario);
            }

            public EditText getFechaHoraEnvio() {
                return fechaHoraEnvio;
            }

            public EditText getTexto() {
                return texto;
            }
        }

        private class HolderMensajeOtro extends RecyclerView.ViewHolder{
            private final EditText fechaHoraEnvio, texto;

            public HolderMensajeOtro(@NonNull View itemView) {
                super(itemView);
                fechaHoraEnvio=itemView.findViewById(R.id.fechaHoraEnvioMensajeUsuario);
                texto=itemView.findViewById(R.id.cuerpoMensajeUsuario);
            }

            public EditText getFechaHoraEnvio() {
                return fechaHoraEnvio;
            }

            public EditText getTexto() {
                return texto;
            }
        }

        private class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder {

            private TextView label1;


            public RecyclerViewSimpleTextViewHolder(View v) {
                super(v);
                label1 = (TextView) v.findViewById(android.R.id.text1);
            }

            public TextView getLabel() {
                return label1;
            }

            public void setLabel1(TextView label1) {
                this.label1 = label1;
            }
        }
    }
}
