package com.example.myapplication.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class FragmentRegisterOne extends Fragment {
    public static FragmentRegisterOne instance;

    public static FragmentRegisterOne getInstance() {
        if (instance == null){
            instance = new FragmentRegisterOne();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_one, container, false);
        return view;
    }


    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
