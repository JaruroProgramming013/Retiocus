package com.jruiz.retiocusapp.ui.slider;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.activities.SliderActivity;
import com.jruiz.retiocusapp.glide.modules.GlideApp;
import com.jruiz.retiocusapp.repo.Repository;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeleccionarPFPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeleccionarPFPFragment extends Fragment implements View.OnClickListener{

    private ImageButton botonPFP;
    private Button botonReiniciarPfp;
    private SliderViewModel viewModel;
    private Uri imagenPredeterminada;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SeleccionarPFPFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeleccionarPFPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeleccionarPFPFragment newInstance(String param1, String param2) {
        SeleccionarPFPFragment fragment = new SeleccionarPFPFragment();
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
        View vista=inflater.inflate(R.layout.fragment_seleccionar_p_f_p, container, false);
        botonPFP=(ImageButton)vista.findViewById(R.id.seleccionaImagen);
        botonReiniciarPfp=(Button)vista.findViewById(R.id.resetPfp);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imagenPredeterminada=new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getResources().getResourcePackageName(R.drawable.retiocus_main_pfp))
                .appendPath(getResources().getResourceTypeName(R.drawable.retiocus_main_pfp))
                .appendPath(getResources().getResourceEntryName(R.drawable.retiocus_main_pfp))
                .build();
        viewModel=new ViewModelProvider(requireActivity()).get(SliderViewModel.class);
        viewModel.setPfp(imagenPredeterminada);
        Observer<Uri> imagenObserver= uri -> botonPFP.setImageURI(uri);
        viewModel.getPfp().observe(requireActivity(),imagenObserver);
        if(!(getActivity() instanceof SliderActivity))
            GlideApp
                    .with(getContext())
                    .load(viewModel.getStorageReference()
                            .child(Repository.getUsuarioActual().getUid()+".png"))
                    .error(R.drawable.retiocus_main_pfp)
                    .into(botonPFP);
        botonPFP.setOnClickListener(this);
        botonReiniciarPfp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==botonPFP.getId()) {
            Intent intentGaleria = new Intent();
            intentGaleria.setType("image/*");
            intentGaleria.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intentGaleria, "Selecciona una imagen"), 1);
        }else{
            botonPFP.setImageURI(imagenPredeterminada);
            viewModel.setPfp(imagenPredeterminada);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1) {
            if(data!=null) {
                botonPFP.setImageURI(data.getData());
                viewModel.setPfp(data.getData());
            }
        }
    }
}