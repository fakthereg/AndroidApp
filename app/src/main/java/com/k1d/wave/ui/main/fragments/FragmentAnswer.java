package com.k1d.wave.ui.main.fragments;

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

import com.airbnb.lottie.LottieAnimationView;
import com.k1d.wave.MainActivity;
import com.k1d.wave.R;
import com.k1d.wave.StaticData;
import com.k1d.wave.User;

public class FragmentAnswer extends Fragment implements View.OnClickListener {

    private static FragmentAnswer instance;

    private TextView textViewCategory;
    private TextView textViewUsername;
    private TextView textViewScore;
    private ImageView imageViewAvatar;
    private ImageView imageViewSongBackgroundCorrect;
    private ImageView imageViewSongBackgroundWrong;
    private TextView textViewArtistCorrect;
    private TextView textViewTitleCorrect;
    private TextView textViewArtistWrong;
    private TextView textViewTitleWrong;
    private ImageView imageViewCup;
    private TextView textViewScoreGain;
    private ImageView imageViewCoin;
    private ImageButton imageButtonBack;
    private ImageButton imageButtonNext;
    private LottieAnimationView lottieAnimationViewSerp;
    private LottieAnimationView lottieAnimationViewSerpRain;
    private LottieAnimationView lottieAnimationViewRain;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer, container, false);
        textViewCategory = view.findViewById(R.id.textViewAnswerCategory);
        textViewUsername = view.findViewById(R.id.textViewAnswerUsername);
        textViewScore = view.findViewById(R.id.textViewAnswerScore);
        textViewArtistCorrect = view.findViewById(R.id.textViewAnswerArtistCorrect);
        textViewTitleCorrect = view.findViewById(R.id.textViewAnswerTitleCorrect);
        textViewArtistWrong = view.findViewById(R.id.textViewAnswerArtistWrong);
        textViewTitleWrong = view.findViewById(R.id.textViewAnswerTitleWrong);
        imageViewAvatar = view.findViewById(R.id.imageViewAnswerAvatar);
        imageViewSongBackgroundCorrect = view.findViewById(R.id.imageViewAnswerSongBackgroundCorrect);
        imageViewSongBackgroundWrong = view.findViewById(R.id.imageViewAnswerSongBackgroundWrong);
        imageViewCup = view.findViewById(R.id.imageViewAnswerCup);
        textViewScoreGain = view.findViewById(R.id.textViewScoreGain);
        imageViewCoin = view.findViewById(R.id.imageViewAnswerCoin);
        imageButtonBack = view.findViewById(R.id.imageButtonAnswerBack);
        imageButtonNext = view.findViewById(R.id.imageButtonAnswerNext);
        lottieAnimationViewSerp = view.findViewById(R.id.lottieAnimationViewSerp);
        lottieAnimationViewSerpRain = view.findViewById(R.id.lottieAnimationViewSerpRain);
        lottieAnimationViewRain = view.findViewById(R.id.lottieAnimationViewRain);
        return view;
    }

    public static FragmentAnswer getInstance() {
        if (instance == null) {
            instance = new FragmentAnswer();
        }
        return instance;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageButtonNext.setOnClickListener(this);
        imageButtonBack.setOnClickListener(this);
        imageViewAvatar.setImageResource(User.avatar);
        textViewUsername.setText(User.name);
        textViewCategory.setText(StaticData.getCategoryAsString(StaticData.chosenCategory).toUpperCase());
        textViewScore.setText(String.valueOf(User.score));
        try{
        textViewArtistCorrect.setText(StaticData.songToGuess.getString("artist"));
        textViewTitleCorrect.setText(StaticData.songToGuess.getString("title"));}
        catch (Exception ex){
            ex.printStackTrace();
        }
        if (StaticData.answered) {
            if (StaticData.answerIsCorrect) {
                lottieAnimationViewSerp.setVisibility(View.VISIBLE);
                lottieAnimationViewSerpRain.setVisibility(View.VISIBLE);
                imageViewCup.setImageResource(R.drawable.cup_win);
                imageViewSongBackgroundCorrect.setImageResource(R.drawable.song_background_win);
                imageViewCoin.setVisibility(View.VISIBLE);
                textViewScoreGain.setVisibility(View.VISIBLE);
                textViewScoreGain.setText(String.valueOf(StaticData.scoreGain));
            } else {
                imageViewSongBackgroundWrong.setVisibility(View.VISIBLE);
                textViewArtistWrong.setText(StaticData.chosenSongArtist);
                textViewTitleWrong.setText(StaticData.chosenSongTitle);
                imageViewCup.setImageResource(R.drawable.cup_lose);
                lottieAnimationViewRain.setVisibility(View.VISIBLE);
                imageViewSongBackgroundCorrect.setImageResource(R.drawable.song_background_win);
                imageViewCoin.setVisibility(View.INVISIBLE);
                textViewScoreGain.setVisibility(View.INVISIBLE);
            }
        } else {
            imageViewCup.setImageResource(R.drawable.cup_lose);
            lottieAnimationViewRain.setVisibility(View.VISIBLE);
            imageViewSongBackgroundCorrect.setImageResource(R.drawable.song_background_neutral);
            imageViewCoin.setVisibility(View.INVISIBLE);
            textViewScoreGain.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        MainActivity.stopPlayingSounds();
        MainActivity.playButtonClickSound();
        if (v.getId() == R.id.imageButtonAnswerNext && StaticData.songsLeftInChosenCategory > 1) {
            getFragmentManager().beginTransaction().replace(R.id.container, new FragmentPlay()).commit();
        } else {
            MainActivity.playMainTheme(true);
            getFragmentManager().beginTransaction().replace(R.id.container, FragmentPanels.getInstance()).replace(R.id.panels_container, new FragmentCategory()).commit();
        }
    }
}
