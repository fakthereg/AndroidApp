package com.example.myapplication.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.User;

public class FragmentPlay extends Fragment {

    private static FragmentPlay instance;

    private ImageView imageViewAvatar;
    private TextView textViewUsername;
    private TextView textViewScore;
    private ImageView imageViewBackground1;
    private TextView textViewArtist1;
    private TextView textViewTitle1;
    private ImageView imageViewBackground2;
    private TextView textViewArtist2;
    private TextView textViewTitle2;
    private ImageView imageViewBackground3;
    private TextView textViewArtist3;
    private TextView textViewTitle3;
    private ImageButton imageButtonBack;

    public static FragmentPlay getInstance() {
        if (instance == null) {
            instance = new FragmentPlay();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        imageViewAvatar = view.findViewById(R.id.imageViewPlayAvatar);
        textViewUsername = view.findViewById(R.id.textViewPlayUsername);
        textViewScore = view.findViewById(R.id.textViewPlayScore);
        imageViewBackground1 = view.findViewById(R.id.imageViewPlaySongBackground1);
        textViewArtist1 = view.findViewById(R.id.textViewPlaySongArtist1);
        textViewTitle1 = view.findViewById(R.id.textViewPlaySongTitle1);
        imageViewBackground2 = view.findViewById(R.id.imageViewPlaySongBackground2);
        textViewArtist2 = view.findViewById(R.id.textViewPlaySongArtist2);
        textViewTitle2 = view.findViewById(R.id.textViewPlaySongTitle2);
        imageViewBackground3 = view.findViewById(R.id.imageViewPlaySongBackground3);
        textViewArtist3 = view.findViewById(R.id.textViewPlaySongArtist3);
        textViewTitle3 = view.findViewById(R.id.textViewPlaySongTitle3);
        imageButtonBack = view.findViewById(R.id.imageButtonPlayBack);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageViewAvatar.setImageResource(User.avatar);
        textViewUsername.setText(User.name);
        textViewScore.setText("" + User.score);

    /*    imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().show(FragmentPanels.getInstance()).replace(R.id.panels_container, FragmentCategory.getInstance()).commit();
            }
        });
*/
    }
}
