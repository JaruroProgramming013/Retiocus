package com.jruiz.retiocusapp.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.entities.Tema;
import com.jruiz.retiocusapp.repo.Repository;
import com.jruiz.retiocusapp.ui.slider.Bienvenida;
import com.jruiz.retiocusapp.ui.slider.PickerTemasFragment;
import com.jruiz.retiocusapp.ui.slider.SeleccionarPFPFragment;
import com.jruiz.retiocusapp.ui.slider.SliderFinalFragment;
import com.jruiz.retiocusapp.ui.slider.SliderViewModel;

import java.util.List;


public class SliderActivity extends FragmentActivity {

    private ViewPager2 paginador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        FragmentActivity actividad=this;
        paginador = findViewById(R.id.paginador);
        SliderViewModel viewModel = new ViewModelProvider(this).get(SliderViewModel.class);
        Observer<List<Tema>> observadorLista= temas -> {
            FragmentStateAdapter adaptadorPaginador = new AdaptadorSlider(actividad);
            paginador.setAdapter(adaptadorPaginador);
        };
        Observer<Boolean> registroCompletado= aBoolean -> {
            if(aBoolean)
                startActivity(new Intent(this,ChatActivity.class));
        };
        viewModel.getSuccessSetup().observe(this,registroCompletado);
        viewModel.getListadoTemas().observe(this,observadorLista);
        viewModel.recuperarTemas();
    }
    private static class AdaptadorSlider extends FragmentStateAdapter{

        public AdaptadorSlider(FragmentActivity actividad){
            super(actividad);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment=new Fragment();
                switch (position){
                    case 0:
                        fragment=new Bienvenida();
                        break;
                    case 1:
                        fragment=new SeleccionarPFPFragment();
                        break;
                    case 2:
                        fragment=new PickerTemasFragment();
                        break;
                    case 3:
                        fragment=new SliderFinalFragment();
                        break;
                }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

    @Override
    public void onBackPressed() {
        if (paginador.getCurrentItem() > 0)
            paginador.setCurrentItem(paginador.getCurrentItem() - 1);
        else{
            startActivity(new Intent(this,MainActivity.class));
        }

    }
}