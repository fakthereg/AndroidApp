package com.k1d.wave.ui.main.fragments;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.k1d.wave.MainActivity;
import com.k1d.wave.R;
import com.k1d.wave.StaticData;
import com.k1d.wave.User;
import com.k1d.wave.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentPlay extends Fragment implements View.OnClickListener {

    private static FragmentPlay instance;

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

    private JSONArray notPlayedSongs;
    private JSONArray allSongs;
    private JSONObject songToGuess;
    private int correctIndex;
    private int correctPosition;
    ArrayList<TextView> artists = new ArrayList<>();
    ArrayList<TextView> titles = new ArrayList<>();
    ArrayList<ImageView> backgrounds = new ArrayList<>();
    CountDownTimer timer;
    private TextView textViewTimer;
    private ProgressBar progressBarLoading;

    private MediaPlayer mediaPlayer;
    private String stringUrlToSong;
    private boolean isPlaying = false;


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
        imageButtonBack.setOnClickListener(this);
        imageViewBackground1.setOnClickListener(this);
        imageViewBackground2.setOnClickListener(this);
        imageViewBackground3.setOnClickListener(this);
        artists.add(textViewArtist1);
        artists.add(textViewArtist2);
        artists.add(textViewArtist3);
        titles.add(textViewTitle1);
        titles.add(textViewTitle2);
        titles.add(textViewTitle3);
        backgrounds.add(imageViewBackground1);
        backgrounds.add(imageViewBackground2);
        backgrounds.add(imageViewBackground3);
        textViewTimer = view.findViewById(R.id.textViewPlayTimer);
        progressBarLoading = view.findViewById(R.id.progressBarLoading);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageViewAvatar.setImageResource(User.avatar);
        textViewUsername.setText(User.name);
        textViewScore.setText(String.valueOf(User.score));
        textViewCategory.setText(StaticData.getChosenCategory().toUpperCase());
        notPlayedSongs = StaticData.getSongsNotPlayedInCategory(StaticData.chosenCategory);
        allSongs = StaticData.getAllSongsInCategory(StaticData.chosenCategory);
        try {
            correctIndex = (int) (Math.random() * StaticData.songsLeftInChosenCategory);
            songToGuess = notPlayedSongs.getJSONObject(correctIndex);
            play();
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished / 1000;
                textViewTimer.setText(String.valueOf(seconds));
                if (seconds > 25) {
                    StaticData.scoreGain = 100;
                } else if (seconds <= 25 && seconds > 15) {
                    StaticData.scoreGain = 50 + (seconds - 15) * 5;
                } else {
                    StaticData.scoreGain = 50;
                }
            }

            @Override
            public void onFinish() {
                StaticData.answered = false;
                try {
                    StaticData.chosenSongArtist = songToGuess.getString("artist");
                    StaticData.chosenSongTitle = songToGuess.getString("title");
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

                mediaPlayer.stop();
                isPlaying = false;
                postSongToPlayed();
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentAnswer()).commit();
            }
        };

        try {
            stringUrlToSong = String.format(StaticData.URL_DOWNLOAD_FILE, StaticData.chosenCategory, songToGuess.getString("filename"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(stringUrlToSong);
            Log.i("mytag", "mediaplayer data source set to " + stringUrlToSong);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDisplay(null);
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBarLoading.setVisibility(View.INVISIBLE);
                textViewTimer.setVisibility(View.VISIBLE);
                timer.start();
                mp.start();
                isPlaying = true;
            }
        });
    }

    private void play() throws JSONException {
        correctPosition = (int) (Math.random() * 3);
        int usedWrongIndex = -1;
        for (int i = 0; i < 3; i++) {
            Log.i("mytag", "i = " + i);
            if (i == correctPosition) {
                artists.get(i).setText(songToGuess.getString("artist"));
                titles.get(i).setText(songToGuess.getString("title"));
                Log.i("mytag", "correct index = " + correctIndex + "\ncorrect set to " + songToGuess.toString());
  //              Toast.makeText(getContext(), songToGuess.getString("filename"), Toast.LENGTH_SHORT).show();
            } else {
                int wrongIndex;
                do {
                    wrongIndex = (int) (Math.random() * allSongs.length());
                } while (allSongs.getJSONObject(wrongIndex).getString("filename").equals(songToGuess.getString("filename")) ||
                        (usedWrongIndex != -1 && allSongs.getJSONObject(wrongIndex).getString("filename").equals(allSongs.getJSONObject(usedWrongIndex).getString("filename"))));
                usedWrongIndex = wrongIndex;
                Log.i("mytag", "wron index = " + wrongIndex + "\nwrong set to " + allSongs.get(wrongIndex).toString());
                artists.get(i).setText(allSongs.getJSONObject(wrongIndex).getString("artist"));
                titles.get(i).setText(allSongs.getJSONObject(wrongIndex).getString("title"));
            }
        }

    }


    @Override
    public void onClick(View view) {
        MainActivity.playButtonClickSound();
        timer.cancel();
        mediaPlayer.stop();
        isPlaying = false;
        if (view.getId() == R.id.imageButtonPlayBack) {
            MainActivity.playMainTheme(true);
            getFragmentManager().beginTransaction().replace(R.id.container, FragmentPanels.getInstance()).replace(R.id.panels_container, new FragmentCategory()).commit();
        }
        if (view.getId() != R.id.imageButtonPlayBack) {
            postSongToPlayed();
            StaticData.answered = true;
        }
        //TODO     maybe wait 1 sec? Thread.sleep not working, must not be used
        if (view.getId() == R.id.imageViewPlaySongBackground1) {
            StaticData.chosenSongArtist = textViewArtist1.getText().toString();
            StaticData.chosenSongTitle = textViewTitle1.getText().toString();
            if (correctPosition == 0) {
                answerCorrect(view);
            } else {
                answerWrong(view);
            }
        } else if (view.getId() == R.id.imageViewPlaySongBackground2) {
            StaticData.chosenSongArtist = textViewArtist2.getText().toString();
            StaticData.chosenSongTitle = textViewTitle2.getText().toString();
            if (correctPosition == 1) {
                answerCorrect(view);
            } else {
                answerWrong(view);
            }
        } else if (view.getId() == R.id.imageViewPlaySongBackground3) {
            StaticData.chosenSongArtist = textViewArtist3.getText().toString();
            StaticData.chosenSongTitle = textViewTitle3.getText().toString();
            if (correctPosition == 2) {
                answerCorrect(view);
            } else {
                answerWrong(view);
            }
        }
    }

    private void answerWrong(View view) {
        StaticData.currentAnswer = false;
        User.wrong++;
        ImageView imageView = (ImageView) view;
        imageView.setImageResource(R.drawable.song_background_wrong);
        postUserData();
        MainActivity.playAnswerWrong();
        getFragmentManager().beginTransaction().replace(R.id.container, new FragmentAnswer()).commit();
    }

    private void answerCorrect(View view) {
        StaticData.currentAnswer = true;
        User.score += StaticData.scoreGain;
        User.correct++;
        ImageView imageView = (ImageView) view;
        imageView.setImageResource(R.drawable.song_background_correct);
        postUserData();
        MainActivity.playAnswerCorrect();
        getFragmentManager().beginTransaction().replace(R.id.container, new FragmentAnswer()).commit();
    }

    private void postUserData() {
        NetworkUtils.ConnectPostTask setData = new NetworkUtils.ConnectPostTask();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("score", User.score);
            jsonObject.put("correct", User.correct);
            jsonObject.put("wrong", User.wrong);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setData.execute(String.format(StaticData.URL_SET_USER_DATA, User.name), jsonObject.toString());
    }

    private void postSongToPlayed() {
        StaticData.playedSongs.put(songToGuess);
        NetworkUtils.ConnectPostTask addToPlayed = new NetworkUtils.ConnectPostTask();
        try {
            addToPlayed.execute(String.format(StaticData.URL_USER_ADD_TO_PLAYED, User.name), songToGuess.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying() && isPlaying){
            mediaPlayer.pause();
        }
        //TODO остановка таймера и музыки при блокировке экрана. Swing timer например, или нафиг это все
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isPlaying){
            mediaPlayer.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
