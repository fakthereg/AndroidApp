package com.k1d.wave.ui.main.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.k1d.wave.R;
import com.k1d.wave.ui.main.MainViewModel;

public class FragmentLogin extends Fragment {
    public static FragmentLogin instance;
    private MainViewModel mViewModel;


    public static FragmentLogin getInstance() {
        if (instance == null){
            instance = new FragmentLogin();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

}