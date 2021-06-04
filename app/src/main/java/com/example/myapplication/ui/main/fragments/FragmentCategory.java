package com.example.myapplication.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.User;
import com.example.myapplication.utils.NetworkUtils;
import com.example.myapplication.R;
import com.example.myapplication.StaticData;
import com.example.myapplication.ui.main.adapters.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class FragmentCategory extends Fragment {

    private static FragmentCategory instance;
    HashMap<Integer, Integer> categories = new HashMap<>();
    CategoryAdapter adapter = new CategoryAdapter(categories);

    public static FragmentCategory getInstance() {
        if (instance == null) {
            instance = new FragmentCategory();
        }
        return instance;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categories.put(0, R.drawable.category_electronic);
        categories.put(1, R.drawable.category_russian_rock);
        categories.put(2, R.drawable.category_foreign_rock);
        categories.put(3, R.drawable.category_oldschool);
        categories.put(4, R.drawable.category_russian_pop);
        categories.put(5, R.drawable.category_latino);
        categories.put(6, R.drawable.category_zashkvar);
        categories.put(7, R.drawable.category_foreign_pop);
        categories.put(8, R.drawable.category_rap);
        categories.put(9, R.drawable.category_eurovision);
        RecyclerView recyclerViewCategories = this.getView().findViewById(R.id.recyclerViewCategory);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCategories.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnCategoryClickListener(new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(int position) {
                MainActivity.playButtonClickSound();
                NetworkUtils.ConnectGetTask getSongsByCategory = new NetworkUtils.ConnectGetTask();
                NetworkUtils.ConnectGetTask getPlayedSongsByCategory = new NetworkUtils.ConnectGetTask();
                try {
                    StaticData.songsPlayedInCategory = new JSONArray(getPlayedSongsByCategory.execute(String.format(StaticData.URL_GET_PLAYED_BY_USER_CATEGORY, User.name, position)).get());
                    StaticData.allSongsInCategory  = new JSONArray(getSongsByCategory.execute(String.format(StaticData.URL_GET_FILES_BY_CATEGORY, position)).get());
                    StaticData.chosenCategory = position;
                    //TODO find distinct songs
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (StaticData.getSongsNotPlayedInCategory().length() > 0) {
                    MainActivity.playMainTheme(false);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new FragmentPlay()).addToBackStack(null).show(FragmentPlay.getInstance()).commit();
                } else{
                    Toast.makeText(getContext(), "В этой категории пусто!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
