package com.jruiz.retiocusapp.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jruiz.retiocusapp.R;
import com.jruiz.retiocusapp.ui.chatting.ChattingViewModel;
import com.jruiz.retiocusapp.ui.newChat.DetailFragment;

public class ChattingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        ChattingViewModel viewModel=new ViewModelProvider(this).get(ChattingViewModel.class);



        Observer<Boolean> observadorVerDetalles=new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentChattingContainer, DetailFragment.class,null)
                        .addToBackStack(null)
                        .commit();
            }
        };
        viewModel.getVerDetalles().observe(this,observadorVerDetalles);
    }

}
