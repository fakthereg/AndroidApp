package com.example.myapplication.ui.main.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.StaticData;

import java.util.HashMap;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    HashMap<Integer, Integer> categories = new HashMap<>();

    private OnCategoryClickListener onCategoryClickListener;

    public interface OnCategoryClickListener {
        void onCategoryClick(int position);
    }

    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }

    public CategoryAdapter(HashMap<Integer, Integer> categories) {
        this.categories = categories;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        System.out.println(position);
        if (StaticData.getSongsNotPlayedInCategory(position).length() > 0) {
            holder.imageViewCategory.setImageResource(categories.get(position));
        } else {
            holder.imageViewCategory.setImageResource(R.drawable.song_background_default);
        }

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCategory = itemView.findViewById(R.id.imageViewCategory);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCategoryClickListener != null) {
                        onCategoryClickListener.onCategoryClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
