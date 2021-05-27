package com.example.myapplication.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.HashMap;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private static final HashMap<Integer, Integer> categories = new HashMap<>();

    public CategoryAdapter() {
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
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
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        while (position < 10) {
            holder.imageViewCategory.setImageResource(categories.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCategory = itemView.findViewById(R.id.imageViewCategory);
        }
    }
}
