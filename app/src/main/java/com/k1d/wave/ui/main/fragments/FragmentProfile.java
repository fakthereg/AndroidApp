package com.k1d.wave.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.k1d.wave.MainActivity;
import com.k1d.wave.R;
import com.k1d.wave.StaticData;
import com.k1d.wave.User;
import com.k1d.wave.ui.main.adapters.ProfileCategoryAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FragmentProfile extends Fragment {

    private ImageView avatar;
    private TextView username;
    private TextView score;
    private TextView correct;
    private TextView wrong;
    private TextView place;
    private RecyclerView recyclerViewCategories;
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
        avatar = view.findViewById(R.id.ImageViewProfileAvatar);
        username = view.findViewById(R.id.textViewProfileUsername);
        username.setText(User.name);
        avatar.setImageResource(User.avatar);
        score = view.findViewById(R.id.textViewProfileScore);
        correct = view.findViewById(R.id.textViewProfileCorrect);
        wrong = view.findViewById(R.id.textViewProfileWrong);
        place = view.findViewById(R.id.textViewProfilePlace);
        score.setText(String.valueOf(User.score));
        correct.setText(String.valueOf(User.correct));
        wrong.setText(String.valueOf(User.wrong));
        place.setText(String.valueOf(User.place)); //TODO get user rating place and parse here
        recyclerViewCategories = view.findViewById(R.id.recyclerViewProfileCategories);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> categories = new ArrayList<>();
        for (int i = 0; i < StaticData.NUMBER_OF_CATEGORIES; i++){
            categories.add(StaticData.getCategoryAsString(i));
        }
        ProfileCategoryAdapter profileCategoryAdapter = new ProfileCategoryAdapter(categories);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCategories.setAdapter(profileCategoryAdapter);
    }

}
