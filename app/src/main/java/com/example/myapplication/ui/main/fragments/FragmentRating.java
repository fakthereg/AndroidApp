package com.example.myapplication.ui.main.fragments;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.StaticData;
import com.example.myapplication.ui.main.adapters.RatingAdapter;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerViewRating = view.findViewById(R.id.recyclerViewRating);
        String userScores = "";
        JSONArray userScoresJson = new JSONArray();
        try {
            userScores = new GetUserScoresTask().execute().get();
            userScoresJson = new JSONArray(userScores);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RatingAdapter adapter = new RatingAdapter(userScoresJson);
        recyclerViewRating.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewRating.setAdapter(adapter);

    }

    private class GetUserScoresTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL(StaticData.URL_REST_BASE_USERS + StaticData.URL_GET_USER_SCORES);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = reader.readLine();
                while (line != null) {
                    result.append(line);
                    line = reader.readLine();
                }
                Log.i("mytag", "url = " + url.toString() + "\nresult = " + result.toString());
                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}

