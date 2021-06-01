package com.example.myapplication.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class FragmentAnswer extends Fragment {

    private static FragmentAnswer instance;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        return view;
    }

    public static FragmentAnswer getInstance() {
        if (instance == null){
            instance = new FragmentAnswer();
        }
        return instance;
    }
}
