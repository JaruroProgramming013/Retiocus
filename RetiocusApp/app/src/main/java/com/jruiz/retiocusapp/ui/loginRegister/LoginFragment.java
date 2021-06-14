package com.jruiz.retiocusapp.ui.loginRegister;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jruiz.retiocusapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText campoEmail, campoContrasenha;
    private Button botonLogin, aRegistro;
    private ViewModelLogin viewModelLogin;
    private ProgressBar circulito;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View vistaRegistro=inflater.inflate(R.layout.fragment_login, container, false);
        campoEmail=(EditText)vistaRegistro.findViewById(R.id.campoEmailLogin);
        campoContrasenha=(EditText)vistaRegistro.findViewById(R.id.campoContrasenhaLogin);
        botonLogin=(Button)vistaRegistro.findViewById(R.id.botonLogin);
        aRegistro=(Button)vistaRegistro.findViewById(R.id.botonARegistro);
        circulito=(ProgressBar)vistaRegistro.findViewById(R.id.loadingLogin);

        return vistaRegistro;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelLogin=new ViewModelProvider(requireActivity()).get(ViewModelLogin.class);
        botonLogin.setOnClickListener(this);
        aRegistro.setOnClickListener(this);
        viewModelLogin.comprobarCargando(false);
        Observer<Boolean> cargando= aBoolean -> {
            if(!aBoolean)
                circulito.setVisibility(View.GONE);
            else
                circulito.setVisibility(View.VISIBLE);
        };
        final TextWatcher comprobarTodosCamposRellenos=new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!campoEmail.getText().toString().isEmpty() &&
                        campoEmail.getText().toString().contains("@")
                        && !campoContrasenha.getText().toString().isEmpty())
                    botonLogin.setEnabled(true);
            }
        };
        viewModelLogin.getCargando().observe(getViewLifecycleOwner(),cargando);
        campoEmail.addTextChangedListener(comprobarTodosCamposRellenos);
        campoContrasenha.addTextChangedListener(comprobarTodosCamposRellenos);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==botonLogin.getId()){
            circulito.setVisibility(View.VISIBLE);
            //Validacion de contraseña
            //al menos un numero
            //al menos una letra mayuscula
            //entre 4 y 12 caracteres
            final String passwordVal = "^" +
                    "(?=.*[0-9])" +         //al menos un numero
                    "(?=.*[A-Z])" +         //al menos una letra mayuscula
                    ".{4,12}" +               //entre 4 y 12 caracteres
                    "$";
            if(!campoContrasenha.getText().toString().matches(passwordVal)) {
                Toast.makeText(requireContext(), R.string.wrong_pass_format, Toast.LENGTH_SHORT).show();
                campoContrasenha.setText("");
                campoContrasenha.requestFocus();
                botonLogin.setEnabled(false);
                circulito.setVisibility(View.GONE);
            }else{
                viewModelLogin.setEmail(campoEmail.getText().toString());
                viewModelLogin.setContrasenha(campoContrasenha.getText().toString());

                viewModelLogin.iniciarSesion(getActivity());
            }
        }else{
            viewModelLogin.volver(1);
        }
    }
}