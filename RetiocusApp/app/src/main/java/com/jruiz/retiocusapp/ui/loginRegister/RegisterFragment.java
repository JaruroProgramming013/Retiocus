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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.PhoneAuthProvider;
import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.activities.MainActivity;
import com.jruiz.retiocusapp.ui.chat.ChatViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener{

    private EditText campoNombre, campoEmail, campoContrasenha, campoEmailRelogin, campoContrasenhaRelogin;
    private Button botonRegistro, volverLogin, relogin;
    private TextView disclaimerRelogin;
    private ViewModelLogin viewModelLogin;
    private ChatViewModel viewModelChat;
    private boolean esRegistro;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View vistaRegistro=inflater.inflate(R.layout.fragment_register, container, false);
        campoNombre=(EditText)vistaRegistro.findViewById(R.id.campoNombreRegistro);
        campoContrasenha=(EditText)vistaRegistro.findViewById(R.id.campoContrasenhaRegistro);
        campoEmail=(EditText)vistaRegistro.findViewById(R.id.campoEmailRegistro);
        botonRegistro=(Button)vistaRegistro.findViewById(R.id.botonRegistro);
        volverLogin=(Button)vistaRegistro.findViewById(R.id.volverALogin);
        disclaimerRelogin=(TextView)vistaRegistro.findViewById(R.id.disclaimerRelogin);
        relogin=(Button)vistaRegistro.findViewById(R.id.logInCredFragment);
        campoContrasenhaRelogin=(EditText)vistaRegistro.findViewById(R.id.campoContrasenhaRelogin);
        campoEmailRelogin=(EditText)vistaRegistro.findViewById(R.id.campoEmailRelogin);
        return vistaRegistro;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        esRegistro=getActivity() instanceof MainActivity;
        final TextWatcher comprobarTodosCamposRellenos;
        if(esRegistro) {
            viewModelLogin = new ViewModelProvider(requireActivity()).get(ViewModelLogin.class);
            volverLogin.setOnClickListener(this);
            comprobarTodosCamposRellenos=new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(campoEmail.getText().toString().contains("@") &&
                            !campoEmail.getText().toString().isEmpty() &&
                            !campoContrasenha.getText().toString().isEmpty() &&
                            !campoNombre.getText().toString().isEmpty()
                    )
                        botonRegistro.setEnabled(true);
                }
            };

            campoNombre.addTextChangedListener(comprobarTodosCamposRellenos);
            campoContrasenha.addTextChangedListener(comprobarTodosCamposRellenos);
            campoEmail.addTextChangedListener(comprobarTodosCamposRellenos);
        }
        else{
            viewModelChat = new ViewModelProvider(requireActivity()).get(ChatViewModel.class);

            volverLogin.setVisibility(View.GONE);
            campoNombre.setVisibility(View.GONE);
            campoEmail.setVisibility(View.GONE);
            campoContrasenha.setVisibility(View.GONE);
            botonRegistro.setVisibility(View.GONE);

            disclaimerRelogin.setVisibility(View.VISIBLE);
            campoContrasenhaRelogin.setVisibility(View.VISIBLE);
            campoEmailRelogin.setVisibility(View.VISIBLE);
            relogin.setVisibility(View.VISIBLE);

            botonRegistro.setText(R.string.guardar_credenciales);
            comprobarTodosCamposRellenos=new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(campoEmailRelogin.getText().toString().contains("@") &&
                            !campoEmailRelogin.getText().toString().isEmpty() &&
                            !campoContrasenhaRelogin.getText().toString().isEmpty()
                    )
                        relogin.setEnabled(true);
                }
            };

            campoEmailRelogin.addTextChangedListener(comprobarTodosCamposRellenos);
            campoContrasenhaRelogin.addTextChangedListener(comprobarTodosCamposRellenos);
        }
        botonRegistro.setOnClickListener(this);
        relogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Validacion de contraseña
        //al menos un numero
        //al menos una letra mayuscula
        //entre 4 y 12 caracteres
        final String passwordVal = "^" +
                "(?=.*[0-9])" +         //al menos un numero
                "(?=.*[A-Z])" +         //al menos una letra mayuscula
                ".{4,12}" +               //entre 4 y 12 caracteres
                "$";
        if(v.getId()==botonRegistro.getId()) {
            if(campoContrasenha.getText().toString().matches(passwordVal) ||
                    (!esRegistro && campoContrasenha.getText().toString().isEmpty())
            ) {
                if(esRegistro) {
                    viewModelLogin.setNombreUsuario(campoNombre.getText().toString());
                    viewModelLogin.setContrasenha(campoContrasenha.getText().toString());
                    viewModelLogin.setEmail(campoEmail.getText().toString());

                    viewModelLogin.publicarCamposRegister();
                }else{
                    viewModelChat.cambiarContrasenha(getActivity(),campoContrasenha.getText().toString());
                    viewModelChat.cambiarCorreo(getActivity(),campoEmail.getText().toString());
                    viewModelChat.cambiarNombre(getActivity(),campoNombre.getText().toString());
                }
            }
            else {
                Toast.makeText(requireContext(), R.string.wrong_pass_format, Toast.LENGTH_SHORT).show();
                campoContrasenha.setText("");
                campoContrasenha.requestFocus();
                botonRegistro.setEnabled(false);
            }
        }
        else if(v.getId()==relogin.getId()){

            if(!campoContrasenhaRelogin.getText().toString().matches(passwordVal)){
                Toast.makeText(requireContext(), R.string.wrong_pass_format, Toast.LENGTH_SHORT).show();
                campoContrasenhaRelogin.setText("");
                campoContrasenhaRelogin.requestFocus();
                relogin.setEnabled(false);
            }else{
                Observer<Boolean> observadorExitoRelogin=new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean){
                            campoNombre.setVisibility(View.VISIBLE);
                            campoEmail.setVisibility(View.VISIBLE);
                            campoContrasenha.setVisibility(View.VISIBLE);
                            botonRegistro.setVisibility(View.VISIBLE);

                            disclaimerRelogin.setVisibility(View.GONE);
                            campoContrasenhaRelogin.setVisibility(View.GONE);
                            campoEmailRelogin.setVisibility(View.GONE);
                            relogin.setVisibility(View.GONE);
                            final TextWatcher reglasCambioCredenciales=new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable s) {
                                    if(campoEmail.getText().toString().contains("@") &&
                                            !campoEmail.getText().toString().isEmpty() ||
                                            !campoNombre.getText().toString().isEmpty() ||
                                            !campoContrasenha.getText().toString().isEmpty()
                                    )
                                        botonRegistro.setEnabled(true);
                                }
                            };

                            campoNombre.addTextChangedListener(reglasCambioCredenciales);
                            campoContrasenha.addTextChangedListener(reglasCambioCredenciales);
                            campoEmail.addTextChangedListener(reglasCambioCredenciales);
                        }
                    }
                };
                viewModelChat.getReloginExitoso().observe(requireActivity(),observadorExitoRelogin);
                viewModelChat.reautenticar(getActivity(),campoEmailRelogin.getText().toString(),campoContrasenhaRelogin.getText().toString());
            }
        }
        else
            viewModelLogin.volver(2);
    }
}