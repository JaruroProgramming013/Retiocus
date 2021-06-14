package com.jruiz.retiocusapp.ui.newChat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.glide.modules.GlideApp;
import com.jruiz.retiocusapp.signalr.models.UserDetailWCT;

import java.util.List;
import java.util.Locale;

public class NewChatListFragment extends Fragment{

    private NewChatViewModel newChatViewModel;
    private RecyclerView listaResultado;
    private TextView noResult;
    private ListaUsuariosResultAdapter adaptador;
    private FloatingActionButton fab;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(newChatViewModel.getListaUsuariosObservable().getValue()!=null)
            newChatViewModel.getListaUsuariosObservable().getValue().clear();
            adaptador.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.user_result_list,container,false);
        listaResultado=(RecyclerView)vista.findViewById(R.id.listaUserResult);
        noResult=(TextView)vista.findViewById(R.id.noResults);
        fab=(FloatingActionButton)vista.findViewById(R.id.repetirConsulta);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newChatViewModel=new ViewModelProvider(getActivity()).get(NewChatViewModel.class);
        adaptador=new ListaUsuariosResultAdapter();
        listaResultado.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        listaResultado.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
        listaResultado.setAdapter(adaptador);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noResult.setVisibility(View.GONE);
                newChatViewModel.restartList();
                adaptador.notifyDataSetChanged();
            }
        });
        Observer<Boolean> observadorExitoResult=new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean) {
                    noResult.setVisibility(View.VISIBLE);
                    listaResultado.setVisibility(View.GONE);
                }
            }
        };
        newChatViewModel.getHayResultados().observe(requireActivity(),observadorExitoResult);
    }

    private class ListaUsuariosResultAdapter extends RecyclerView.Adapter<ListaUsuariosResultAdapter.ListaUsuariosResultHolder>{

        @NonNull
        @Override
        public ListaUsuariosResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View vista=LayoutInflater.from(parent.getContext()).inflate(R.layout.user_result_row,parent,false);
            return new ListaUsuariosResultHolder(vista);
        }

        @Override
        public void onBindViewHolder(@NonNull ListaUsuariosResultHolder holder, int position) {
            if(newChatViewModel.getListaUsuariosObservable().getValue()!=null){
                GlideApp
                        .with(getContext())
                        .load(newChatViewModel.getStorageReferenceList().get(position))
                        .error(R.drawable.retiocus_main_pfp)
                        .circleCrop()
                        .into(holder.getIconoUsuario());
                holder.getUidUsuarioResult()
                        .setText(newChatViewModel.getListaUsuariosObservable().getValue().get(position).getUsuario().getUid());
                holder.getCommonNumberOfThemes()
                        .setText(String
                                .format(Locale.getDefault()
                                        ,"%d"
                                        ,newChatViewModel.getListaUsuariosObservable().getValue().get(position).getNumeroTemas()));
                holder.getNombreUsuarioResult()
                        .setText(newChatViewModel.getListaUsuariosObservable().getValue().get(position).getUsuario().getUsername());
                holder.getLayoutPadre().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newChatViewModel.seleccionarUsuario(holder.getUidUsuarioResult().getText().toString(),holder.getNombreUsuarioResult().getText().toString());
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return newChatViewModel.getListaUsuariosObservable().getValue().size();
        }

        private class ListaUsuariosResultHolder extends RecyclerView.ViewHolder{
            private final ImageView iconoUsuario;
            private final TextView nombreUsuarioResult, commonNumberOfThemes, uidUsuarioResult;
            private final RelativeLayout layoutPadre;
            public ListaUsuariosResultHolder(@NonNull View view) {
                super(view);
                iconoUsuario=(ImageView) view.findViewById(R.id.iconoUsuarioResultRow);
                nombreUsuarioResult=(TextView) view.findViewById(R.id.nombreUsuarioResultRow);
                commonNumberOfThemes=(TextView) view.findViewById(R.id.numeroTemasComunes);
                uidUsuarioResult=(TextView) view.findViewById(R.id.uidUsuarioResult);
                layoutPadre=(RelativeLayout) view.findViewById(R.id.elementoChatNuevo);
            }

            public ImageView getIconoUsuario() {
                return iconoUsuario;
            }

            public TextView getNombreUsuarioResult() {
                return nombreUsuarioResult;
            }

            public TextView getCommonNumberOfThemes() {
                return commonNumberOfThemes;
            }

            public TextView getUidUsuarioResult() {
                return uidUsuarioResult;
            }

            public RelativeLayout getLayoutPadre() {
                return layoutPadre;
            }
        }
    }
}
