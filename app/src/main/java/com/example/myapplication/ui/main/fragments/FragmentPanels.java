package com.example.myapplication.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class FragmentPanels extends Fragment {
    public static FragmentPanels instance;

    public static FragmentPanels getInstance() {
        if (instance == null){
            instance = new FragmentPanels();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_panels, container, false);
        return view;

    }
}
