package com.k1d.wave.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.k1d.wave.R;
import com.k1d.wave.ui.main.adapters.RatingAdapter;
import com.k1d.wave.utils.NetworkUtils;

import org.json.JSONArray;

public class FragmentRating extends Fragment {

    private static FragmentRating instance;

    public static FragmentRating getInstance() {
        if (instance == null) {
            instance = new FragmentRating();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rating, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerViewRating = view.findViewById(R.id.recyclerViewRating);
        String userScores = "";
        JSONArray userScoresJson = new JSONArray();
        try {
            userScores = new NetworkUtils.GetUserScoresTask().execute().get();
            userScoresJson = new JSONArray(userScores);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RatingAdapter adapter = new RatingAdapter(userScoresJson);
        recyclerViewRating.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewRating.setAdapter(adapter);

    }


}

