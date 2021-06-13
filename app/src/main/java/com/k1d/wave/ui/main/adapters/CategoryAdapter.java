package com.k1d.wave.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.k1d.wave.R;
import com.k1d.wave.StaticData;

import java.util.HashMap;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    HashMap<Integer, Integer> categories = new HashMap<>();
    HashMap<Integer, Integer> categories_bw = new HashMap<>();
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
        categories_bw.put(0, R.drawable.category_electronic_bw);
        categories_bw.put(1, R.drawable.category_russian_rock_bw);
        categories_bw.put(2, R.drawable.category_foreign_rock_bw);
        categories_bw.put(3, R.drawable.category_oldschool_bw);
        categories_bw.put(4, R.drawable.category_russian_pop_bw);
        categories_bw.put(5, R.drawable.category_espanol_bw);
        categories_bw.put(6, R.drawable.category_zashkvar_bw);
        categories_bw.put(7, R.drawable.category_foreign_pop_bw);
        categories_bw.put(8, R.drawable.category_rap_bw);
        categories_bw.put(9, R.drawable.category_eurovision_bw);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (StaticData.getSongsNotPlayedInCategory(position).length() > 0) {
            holder.imageViewCategory.setImageResource(categories.get(position));
        } else {
            holder.imageViewCategory.setImageResource(categories_bw.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewCategory;

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
