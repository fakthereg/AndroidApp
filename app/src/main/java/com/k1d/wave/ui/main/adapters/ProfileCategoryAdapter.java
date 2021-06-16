package com.k1d.wave.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.k1d.wave.R;
import com.k1d.wave.StaticData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProfileCategoryAdapter extends RecyclerView.Adapter<ProfileCategoryAdapter.ProfileCategoryViewHolder> {

    private ArrayList<String> categories;

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public ProfileCategoryAdapter(ArrayList<String> categories) {
        this.categories = categories;
    }

    @NonNull
    @NotNull
    @Override
    public ProfileCategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_category_item, parent, false);
        return new ProfileCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProfileCategoryViewHolder holder, int position) {
        String category = categories.get(position) + ":";
        String count = StaticData.getCorrectSongsInCategoryCount(position) + "/" + StaticData.getAnsweredSongsInCategory(position).length();
        holder.textViewCategory.setText(category);
        holder.textViewCount.setText(count);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ProfileCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCategory;
        TextView textViewCount;

        public ProfileCategoryViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.textViewProfileCategoryItemCategory);
            textViewCount = itemView.findViewById(R.id.textViewProfileCategoryItemCount);
        }
    }

}
