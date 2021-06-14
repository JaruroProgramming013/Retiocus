package com.jruiz.retiocusapp.ui.slider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.activities.SliderActivity;
import com.jruiz.retiocusapp.entities.Tema;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickerTemasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickerTemasFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener{

    private SliderViewModel viewModel;

    private SearchView searchView;
    private RecyclerView listaTemas;
    private Button botonBorrar;
    private ListaTemasAdapter adaptador;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PickerTemasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PickerTemasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PickerTemasFragment newInstance(String param1, String param2) {
        PickerTemasFragment fragment = new PickerTemasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_picker_temas, container, false);
        viewModel=new ViewModelProvider(requireActivity()).get(SliderViewModel.class);
        botonBorrar=(Button)vista.findViewById(R.id.borrarSeleccionTemas);
        searchView=(SearchView)vista.findViewById(R.id.buscaTemas);
        listaTemas=(RecyclerView) vista.findViewById(R.id.listaTemas);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adaptador=new ListaTemasAdapter();
        listaTemas.setLayoutManager(new LinearLayoutManager(requireContext()));
        listaTemas.setAdapter(adaptador);
        if(viewModel.getListadoTemasUsuario().getValue()!=null) {
            adaptador.marcarTemasPreviosDelUsuario();
        }
        botonBorrar.setOnClickListener(this);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onClick(View v) {
        adaptador.desmarcarTodo();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText){
        listaFiltrada(newText);
        return false;
    }

    public void listaFiltrada(String texto){
        List<Tema> listadoFiltrado=new ArrayList<>();
        for(Tema tema:viewModel.getListadoTemas().getValue()){
            if(tema.getNombre().toLowerCase().contains(texto.toLowerCase())){
                listadoFiltrado.add(tema);
            }
        }
        if(!listadoFiltrado.isEmpty())
            adaptador.filtrarLista(listadoFiltrado);
    }

    private class ListaTemasAdapter extends RecyclerView.Adapter<ListaTemasAdapter.ListaTemasViewHolder>{

        private List<Tema> listaFiltrada;

        public ListaTemasAdapter(){
            listaFiltrada=viewModel.getListadoTemas().getValue();
        }

        @NonNull
        @Override
        public ListaTemasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View vista=LayoutInflater.from(parent.getContext()).inflate(R.layout.temas_row,parent,false);

            return new ListaTemasViewHolder(vista);
        }

        @Override
        public void onBindViewHolder(@NonNull ListaTemasViewHolder holder, int position) {
            holder.getTema().setText(listaFiltrada.get(position).getNombre());
            holder.getIdTema().setText(String.format(Locale.getDefault(),"%d",listaFiltrada.get(position).getID()));
            holder.getTema().setCheckMarkDrawable(android.R.drawable.star_off);
            Tema temaHolderActual=new Tema(Integer.parseInt((String) holder.getIdTema().getText()),(String) holder.getTema().getText());

            if(!viewModel.getListadoTemasUsuarioRenovado().isEmpty()) {
                if (listaFiltrada.contains(temaHolderActual) &&
                        viewModel.getListadoTemasUsuarioRenovado().contains(temaHolderActual)){
                    holder.getTema().setCheckMarkDrawable(android.R.drawable.star_on);
                    holder.getTema().setChecked(true);
                } else {
                    holder.getTema().setChecked(false);
                }
            }

            holder.getTema().setOnClickListener(v -> {
                boolean chequeadoAntes=holder.getTema().isChecked();

                if(chequeadoAntes){
                    viewModel.getListadoTemasUsuarioRenovado().remove(temaHolderActual);
                    holder.getTema().setCheckMarkDrawable(android.R.drawable.star_off);
                    holder.getTema().setChecked(false);
                }else{
                    viewModel.getListadoTemasUsuarioRenovado().add(temaHolderActual);
                    holder.getTema().setCheckMarkDrawable(android.R.drawable.star_on);
                    holder.getTema().setChecked(true);
                }

                notifyItemChanged(position);
            });
        }

        @Override
        public int getItemCount() {
            return listaFiltrada.size();
        }

        private class ListaTemasViewHolder extends RecyclerView.ViewHolder{
            private final CheckedTextView tema;
            private final TextView idTema;

            public ListaTemasViewHolder(@NonNull View itemView) {
                super(itemView);

                tema=(CheckedTextView)itemView.findViewById(R.id.checkbox);
                idTema=(TextView)itemView.findViewById(R.id.dummyIDTema);
            }

            public CheckedTextView getTema(){
                return tema;
            }

            public TextView getIdTema() {
                return idTema;
            }
        }

        public void desmarcarTodo(){
            List<Tema> checksActivadosCopia=new ArrayList<>(viewModel.getListadoTemasUsuarioRenovado());
            for(Tema temaMarcado:checksActivadosCopia){
                viewModel.getListadoTemasUsuarioRenovado().remove(temaMarcado);
                notifyItemChanged(listaFiltrada.indexOf(temaMarcado));
            }
        }

        public void marcarTemasPreviosDelUsuario(){
            for(Tema temaUsuario:viewModel.getListadoTemasUsuario().getValue()){
                viewModel.getListadoTemasUsuarioRenovado().add(temaUsuario);
                notifyItemChanged(listaFiltrada.indexOf(temaUsuario));
            }
        }

        public void filtrarLista(List<Tema> listaFiltrada){
            this.listaFiltrada=listaFiltrada;
            notifyDataSetChanged();
        }
    }
}