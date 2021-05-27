package com.example.myapplication.ui.main.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.User;

public class FragmentLoggedIn extends Fragment {
    public static FragmentLoggedIn instance;

    public static FragmentLoggedIn getInstance() {
        if (instance == null) {
            instance = new FragmentLoggedIn();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logged_in, container, false);
        ImageView avatar = (ImageView) view.findViewById(R.id.logged_in_avatar);
        TextView hello_username = (TextView) view.findViewById(R.id.logged_in_hello);
        String settext = "Привет, " + User.name + "!";
        avatar.setImageResource(User.avatar);
        hello_username.setText(settext);
        return view;

    }
}
