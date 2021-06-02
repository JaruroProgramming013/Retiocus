package com.jruiz.retiocusapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.jruiz.retiocusapp.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener{

    private EditText campoNombre, campoEmail, campoTelefono, campoContrasenha;
    private Button botonRegistro, volverLogin;
    private ViewModelLogin viewModelLogin;

    private PhoneAuthProvider.ForceResendingToken reenvioToken;
    private String idVerificacionTelefonica;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

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
        campoTelefono=(EditText)vistaRegistro.findViewById(R.id.campoTelefonoRegistro);
        botonRegistro=(Button)vistaRegistro.findViewById(R.id.botonRegistro);
        volverLogin=(Button)vistaRegistro.findViewById(R.id.volverALogin);
        return vistaRegistro;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelLogin=new ViewModelProvider(requireActivity()).get(ViewModelLogin.class);
        botonRegistro.setOnClickListener(this);
        volverLogin.setOnClickListener(this);

        callbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);

                viewModelLogin.asignarNumeroTelefono(requireActivity(),phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(requireContext(),"Verificacion incorrecta",Toast.LENGTH_LONG).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(requireContext(),"La cuota de SMS ha sido excedida. Contacte con un programador.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Log.d(TAG, "onCodeSent:" + s);
            }
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
                if(campoEmail.getText().toString().contains("@") &&
                        !campoEmail.getText().toString().isEmpty() &&
                        !campoContrasenha.getText().toString().isEmpty() &&
                        !campoNombre.getText().toString().isEmpty() &&
                        !campoTelefono.getText().toString().isEmpty()
                )
                    botonRegistro.setEnabled(true);
            }
        };
        campoTelefono.addTextChangedListener(comprobarTodosCamposRellenos);
        campoNombre.addTextChangedListener(comprobarTodosCamposRellenos);
        campoContrasenha.addTextChangedListener(comprobarTodosCamposRellenos);
        campoEmail.addTextChangedListener(comprobarTodosCamposRellenos);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==botonRegistro.getId()) {
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
                botonRegistro.setEnabled(false);
            }
            else {

                viewModelLogin.setNombreUsuario(campoNombre.getText().toString());
                viewModelLogin.setContrasenha(campoContrasenha.getText().toString());
                viewModelLogin.setEmail(campoEmail.getText().toString());
                viewModelLogin.setNumTelefono(campoTelefono.getText().toString());

                viewModelLogin.registrarUsuario(requireActivity(),callbacks);
            }
        }
        else
            viewModelLogin.volver(2);
    }
}