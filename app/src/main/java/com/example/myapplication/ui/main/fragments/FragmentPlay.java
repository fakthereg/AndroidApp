package com.example.myapplication.ui.main.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.StaticData;
import com.example.myapplication.User;
import com.example.myapplication.ui.main.MainViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class FragmentPlay extends Fragment implements View.OnClickListener {

    private static FragmentPlay instance;
    private MainViewModel viewModel;

    private ImageView imageViewAvatar;
    private TextView textViewUsername;
    private TextView textViewScore;
    private TextView textViewCategory;
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

    private JSONArray songs;
    private JSONObject songToGuess;
    private int correctIndex;
    ArrayList<TextView> artists = new ArrayList<>();
    ArrayList<TextView> titles = new ArrayList<>();
    ArrayList<ImageView> backgrounds = new ArrayList<>();

    public static FragmentPlay getInstance() {
        if (instance == null) {
            instance = new FragmentPlay();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        imageViewAvatar = view.findViewById(R.id.imageViewPlayAvatar);
        textViewUsername = view.findViewById(R.id.textViewPlayUsername);
        textViewScore = view.findViewById(R.id.textViewPlayScore);
        textViewCategory = view.findViewById(R.id.textViewPlayCategory);
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
        artists.add(textViewArtist1);
        artists.add(textViewArtist2);
        artists.add(textViewArtist3);
        titles.add(textViewTitle1);
        titles.add(textViewTitle2);
        titles.add(textViewTitle3);
        backgrounds.add(imageViewBackground1);
        backgrounds.add(imageViewBackground2);
        backgrounds.add(imageViewBackground3);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageViewAvatar.setImageResource(User.avatar);
        textViewUsername.setText(User.name);
        textViewScore.setText(String.valueOf(User.score));
        textViewCategory.setText(StaticData.getCategory());
        songs = StaticData.songsInCategory;
        try {
            correctIndex = (int) (Math.random() * songs.length());
            songToGuess = songs.getJSONObject(correctIndex);
            play();
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }


    private void play() throws JSONException {
        int correctPosition = (int) (Math.random() * 3);
        int usedWrongIndex = -1;
        for (int i = 0; i < 3; i++) {
            if (i == correctPosition) {
                artists.get(i).setText(songToGuess.getString("artist"));
                titles.get(i).setText(songToGuess.getString("title"));
                Log.i("mytag", "correct index = " + correctIndex + "\ncorrect set to " + songToGuess.toString());
            } else {
                int wrongIndex;
                do {
                    wrongIndex = (int) (Math.random() * songs.length());
                } while (wrongIndex == correctIndex || usedWrongIndex == wrongIndex);
                usedWrongIndex = wrongIndex;
                artists.get(i).setText(songs.getJSONObject(wrongIndex).getString("artist"));
                titles.get(i).setText(songs.getJSONObject(wrongIndex).getString("title"));


            }
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageViewPlaySongBackground1) {
            if (correctIndex == 0) {
                //add scores
                //paint background to green, play youwin sound, wait 2 sec,  ShowWinScreen
                answerCorrect(v);
            } else {
                //paint background to red, play youlose sound, wait 2 sec,  ShowLoseScreen
            }
        } else if (v.getId() == R.id.imageViewPlaySongBackground2) {
            if (correctIndex == 1) {

            } else {

            }
        } else if (v.getId() == R.id.imageViewPlaySongBackground3) {
            if (correctIndex == 2) {

            } else {

            }
        }
    }

    private void answerCorrect(View view){
        User.correct++;
        ImageView imageView = (ImageView) view;
        imageView.setImageResource(R.drawable.song_background_correct);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getFragmentManager().beginTransaction().replace(R.id.container, FragmentPanels.getInstance()).replace(R.id.panels_container, FragmentAnswer.getInstance()).commit();
    }
}
