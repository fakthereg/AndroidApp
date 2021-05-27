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
import com.example.myapplication.R;
import com.example.myapplication.StaticData;
import com.example.myapplication.ui.main.adapters.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class FragmentCategory extends Fragment {

    private static FragmentCategory instance;

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
        RecyclerView recyclerViewCategories = view.findViewById(R.id.recyclerViewCategory);
        HashMap<Integer, Integer> categories = new HashMap<>();
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
        CategoryAdapter adapter = new CategoryAdapter(categories);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewCategories.setAdapter(adapter);
        adapter.setOnCategoryClickListener(new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(int position) {
                Toast.makeText(getContext(), "clicked: " + position, Toast.LENGTH_SHORT).show();
                MainActivity.ConnectGetTask getSongsByCategory = new MainActivity.ConnectGetTask();
                JSONArray songs = new JSONArray();
                try {
                    songs = new JSONArray(getSongsByCategory.execute(StaticData.URL_REST_BASE_FILES + position + "/").get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
                Toast.makeText(getContext(), "songs found: " + songs.length(), Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, FragmentPlay.getInstance()).addToBackStack(null).show(FragmentPlay.getInstance()).commit();
            }
        });
    }


}
