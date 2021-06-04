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

public class FragmentProfile extends Fragment {

    public static FragmentProfile instance;

    public static FragmentProfile getInstance() {
        if (instance == null){
            instance = new FragmentProfile();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView avatar = view.findViewById(R.id.profile_avatar);
        TextView username = view.findViewById(R.id.profile_username);
        username.setText(User.name);
        avatar.setImageResource(User.avatar);
        TextView score = view.findViewById(R.id.profile_score);
        TextView correct = view.findViewById(R.id.profile_correct);
        TextView wrong = view.findViewById(R.id.profile_wrong);
        TextView place = view.findViewById(R.id.profile_place);
        score.setText(String.valueOf(User.score));
        correct.setText(String.valueOf(User.correct));
        wrong.setText(String.valueOf(User.wrong));
        place.setText(String.valueOf(User.place)); //TODO get user rating place and parse here

        return view;

    }
}
