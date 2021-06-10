package com.k1d.wave.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.k1d.wave.R;
import com.k1d.wave.User;

import org.json.JSONArray;
import org.json.JSONException;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingViewHolder> {

    JSONArray users = new JSONArray();

    public RatingAdapter(JSONArray users) {
        this.users = users;
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_item, parent, false);
        return new RatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        if (position == 0) {
            holder.imageViewCrown.setVisibility(View.VISIBLE);
        }
        try {
            holder.textViewPlayer.setText(users.getJSONObject(position).getString("name"));
            holder.textViewScore.setText(users.getJSONObject(position).getString("score"));
            holder.imageViewAvatar.setImageResource(User.getAvatarResourceId(users.getJSONObject(position).getInt("avatar")));
            if (User.name.equals(users.getJSONObject((position)).getString("name"))){
                User.place = position;
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return users.length();
    }

    public class RatingViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewPlayer;
        private final TextView textViewScore;
        private final ImageView imageViewCrown;
        private final ImageView imageViewAvatar;
        private final ImageView imageViewBackground;

        public RatingViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPlayer = itemView.findViewById(R.id.textViewRatingItemPlayer);
            textViewScore = itemView.findViewById(R.id.textViewRatingItemScore);
            imageViewCrown = itemView.findViewById(R.id.imageViewRatingCrown);
            imageViewAvatar = itemView.findViewById(R.id.imageViewRatingItemAvatar);
            imageViewBackground = itemView.findViewById(R.id.imageViewRatingItemBackground);
        }
    }
}
