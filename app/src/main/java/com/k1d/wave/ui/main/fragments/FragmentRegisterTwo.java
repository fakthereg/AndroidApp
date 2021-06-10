package com.k1d.wave.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.k1d.wave.R;
import com.k1d.wave.User;

public class FragmentRegisterTwo extends Fragment {
    public static FragmentRegisterTwo instance;

    public static FragmentRegisterTwo getInstance() {
        if (instance == null) {
            instance = new FragmentRegisterTwo();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_two, container, false);
        TextView hello_username = (TextView) view.findViewById(R.id.register2_header);
        String settext = "Привет, " + User.name;
        hello_username.setText(settext);
        return view;
    }

}

