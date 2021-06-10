package com.k1d.wave.ui.main.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.k1d.wave.R;
import com.k1d.wave.User;

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
        TextView textViewScore = view.findViewById(R.id.top_panel_score);
        textViewScore.setText(String.valueOf(User.score));
        return view;
    }
}
