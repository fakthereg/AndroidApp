package com.k1d.wave;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.k1d.wave.ui.main.fragments.FragmentCategory;
import com.k1d.wave.ui.main.fragments.FragmentLoggedIn;
import com.k1d.wave.ui.main.fragments.FragmentLogin;
import com.k1d.wave.ui.main.fragments.FragmentPanels;
import com.k1d.wave.ui.main.fragments.FragmentProfile;
import com.k1d.wave.ui.main.fragments.FragmentRating;
import com.k1d.wave.ui.main.fragments.FragmentRegisterOne;
import com.k1d.wave.ui.main.fragments.FragmentRegisterTwo;
import com.k1d.wave.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity /*implements WampInterface*/ {
    //   WebSocketClient client;
    private static SoundPool soundPool;
    private static MediaPlayer mediaPlayer;
    private static int soundIdButtonClick;
    private static int soundIdCorrect;
    private static int soundIdWrong;
    private boolean isPlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Objects.requireNonNull(getSupportActionBar()).hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.container);
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        soundIdButtonClick = soundPool.load(this, R.raw.button_click, 1);
        soundIdCorrect = soundPool.load(this, R.raw.answer_correct, 1);
        soundIdWrong = soundPool.load(this, R.raw.answer_wrong, 1);
        mediaPlayer = MediaPlayer.create(this, R.raw.main_theme);
        mediaPlayer.setLooping(true);
        playMainTheme(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, FragmentLogin.getInstance()).commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (android.os.Build.VERSION.SDK_INT >= 21) {

            ArrayList<String> request_perm = new ArrayList<String>();


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                request_perm.add(Manifest.permission.ACCESS_NETWORK_STATE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                request_perm.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
                request_perm.add(Manifest.permission.WAKE_LOCK);
            }
            if (request_perm.size() > 0) {
                String[] arr_req = new String[request_perm.size()];
                for (int i = 0; i < request_perm.size(); i++) {
                    arr_req[i] = request_perm.get(i);
                }
                ActivityCompat.requestPermissions(this, arr_req, 9899);
            }
        }
    }

    public void buttonClick(View button) {
        playButtonClickSound();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TextView header = findViewById(R.id.top_panel_header);
        TextView score = findViewById(R.id.top_panel_score);
        if (button.getId() == R.id.login_button_login) {
            EditText input_login = findViewById(R.id.login_login);
            EditText input_password = findViewById(R.id.login_password);

            String login = input_login.getText().toString();
            String password = input_password.getText().toString();
            if (!(login.equals("") || password.equals(""))) {
                Login();
            } else {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
            }

        } else if (button.getId() == R.id.login_button_register) {
            fragmentTransaction.
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).
                    replace(R.id.container, FragmentRegisterOne.getInstance()).addToBackStack(null).
                    commit();
        } else if (button.getId() == R.id.register_button_proceed) {
            EditText input_login = findViewById(R.id.register_login);
            EditText input_password = findViewById(R.id.register_password);
            EditText input_password2 = findViewById(R.id.register_password2);

            String login = input_login.getText().toString().toLowerCase().trim();
            String password = input_password.getText().toString().trim();
            String password2 = input_password2.getText().toString().trim();

            if (login.equals("") || password.equals("") || password2.equals("")) {
                Toast.makeText(this, "Заполни все поля!", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(password2)) {
                Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
            } else if (login.length() > 10) {
                Toast.makeText(this, "Имя пользователя не больше 10 символов!", Toast.LENGTH_SHORT).show();
            } else {
                User.name = login;
                User.password = password;
                fragmentTransaction.
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).
                        replace(R.id.container, FragmentRegisterTwo.getInstance()).addToBackStack(null).
                        commit();
            }
        } else if (button.getId() == R.id.register_button_back) {
            getSupportFragmentManager().popBackStack();
        } else if (button.getId() == R.id.register2_avatar1) {
            User.avatar = R.drawable.avatar1;
            User.setAvatarId(User.avatar);
            Register();
        } else if (button.getId() == R.id.register2_avatar2) {
            User.avatar = R.drawable.avatar2;
            User.setAvatarId(User.avatar);
            Register();
        } else if (button.getId() == R.id.register2_avatar3) {
            User.avatar = R.drawable.avatar3;
            Register();
        } else if (button.getId() == R.id.register2_avatar4) {
            User.avatar = R.drawable.avatar4;
            User.setAvatarId(User.avatar);
            Register();
        } else if (button.getId() == R.id.register2_avatar5) {
            User.avatar = R.drawable.avatar5;
            User.setAvatarId(User.avatar);
            Register();
        } else if (button.getId() == R.id.register2_avatar6) {
            User.avatar = R.drawable.avatar6;
            User.setAvatarId(User.avatar);
            Register();
        } else if (button.getId() == R.id.register2_avatar7) {
            User.avatar = R.drawable.avatar7;
            User.setAvatarId(User.avatar);
            Register();
        } else if (button.getId() == R.id.register2_avatar8) {
            User.avatar = R.drawable.avatar8;
            User.setAvatarId(User.avatar);
            Register();
        } else if (button.getId() == R.id.register2_avatar9) {
            User.avatar = R.drawable.avatar9;
            User.setAvatarId(User.avatar);
            Register();
        } else if (button.getId() == R.id.bottom_panel_profile) {
            fragmentTransaction.
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).
                    replace(R.id.panels_container, FragmentProfile.getInstance()).
                    commit();
            findViewById(R.id.top_panel_score).setVisibility(View.INVISIBLE);
            findViewById(R.id.top_panel_coin).setVisibility(View.INVISIBLE);
            header.setText("профиль");

        } else if (button.getId() == R.id.bottom_panel_rating) {
            fragmentTransaction.
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).
                    replace(R.id.panels_container, FragmentRating.getInstance()).
                    commit();
            findViewById(R.id.top_panel_score).setVisibility(View.INVISIBLE);
            findViewById(R.id.top_panel_coin).setVisibility(View.VISIBLE);
            header.setText("рейтинг");


        } else if (button.getId() == R.id.bottom_panel_category) {
            fragmentTransaction.
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).
                    replace(R.id.panels_container, FragmentCategory.getInstance()).
                    commit();
            score.setText(String.valueOf(User.score));
            findViewById(R.id.top_panel_score).setVisibility(View.VISIBLE);
            findViewById(R.id.top_panel_coin).setVisibility(View.VISIBLE);
            header.setText("категории");
//            FragmentCategory.getInstance().init();

        } else if (button.getId() == R.id.bottom_panel_exit) {
            showExitDialog();

        }

    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Выход");
        builder.setMessage("Правда хочешь выйти?");
        builder.setNegativeButton("Нет",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });
        builder.setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        System.exit(0);
                    }
                });
        builder.show();
    }

    private void Register() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NetworkUtils.ConnectRegisterTask registerTask = new NetworkUtils.ConnectRegisterTask();
        JSONObject jsonObject = null;
        String username = "";
        try {
            jsonObject = registerTask.execute(StaticData.URL_REGISTER).get();
            username = jsonObject.getString("name");
        } catch (Exception e) {
            Toast.makeText(this, "Проблемы с подключением", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        if (username.equals(User.name)) {
            fragmentManager.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.
                    replace(R.id.container, FragmentPanels.getInstance()).replace(R.id.panels_container, FragmentLoggedIn.getInstance()).
                    commit();
        } else if (username.equals(StaticData.FAILED_REGISTER_USER_NAME)) {
            fragmentManager.popBackStack();
            Toast.makeText(this, "Такой пользователь уже есть!", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Log.d("mytag", jsonObject.toString());
            } catch (NullPointerException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void Login() {
        NetworkUtils.ConnectGetTask getAllUsersTask = new NetworkUtils.ConnectGetTask();
        try {
            EditText loginInput = findViewById(R.id.login_login);
            EditText passwordInput = findViewById(R.id.login_password);
            if (loginInput != null && passwordInput != null) {
                String login = loginInput.getText().toString().toLowerCase().trim();
                String password = passwordInput.getText().toString().trim();
                JSONArray userList = new JSONArray(getAllUsersTask.execute(StaticData.URL_GET_ALL_USERS).get());
                for (int i = 0; i < userList.length(); i++) {
                    if (login.equals(userList.getString(i))) {
                        NetworkUtils.ConnectGetTask getUserTask = new NetworkUtils.ConnectGetTask();
                        JSONObject userJson = new JSONObject(getUserTask.execute(String.format(StaticData.URL_GET_USER_BY_NAME, userList.getString(i))).get());
                        if (userJson.getString("password").equals(password)) {
                            User.name = login;
                            User.password = password;
                            User.setAvatarResourceId(userJson.getInt("avatar"));
                            User.correct = userJson.getInt("correct");
                            User.wrong = userJson.getInt("wrong");
                            User.score = userJson.getInt("score");
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentManager.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            fragmentTransaction.
                                    replace(R.id.container, FragmentPanels.getInstance()).replace(R.id.panels_container, FragmentLoggedIn.getInstance()).
                                    commit();
                        } else {
                            Toast.makeText(this, "Неправильный пароль!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    } else if (i == userList.length() - 1) {
                        Toast.makeText(this, "Нет такого пользователя!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Проблемы с подключением", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        showExitDialog();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlaying) {
            playMainTheme(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPlaying = mediaPlayer.isPlaying();
        playMainTheme(false);
    }

    public static void playButtonClickSound() {
        soundPool.play(soundIdButtonClick, 1, 1, 1, 0, 0);
    }

    public static void playAnswerCorrect() {
        soundPool.play(soundIdCorrect, 1, 1, 1, 0, 0);
    }

    public static void playAnswerWrong() {
        soundPool.play(soundIdWrong, 1, 1, 1, 0, 0);
    }

    public static void playMainTheme(boolean play) {
        if (mediaPlayer.isPlaying() && !play) {
            mediaPlayer.pause();
        }
        if (!mediaPlayer.isPlaying() && play) {
            mediaPlayer.start();
        }
    }

    public static void stopPlayingSounds() {
        soundPool.stop(soundIdCorrect);
        soundPool.stop(soundIdWrong);
    }

    public static void loadSongs () {
        NetworkUtils.ConnectGetTask getAllSongs = new NetworkUtils.ConnectGetTask();
        NetworkUtils.ConnectGetTask getPlayedSongs = new NetworkUtils.ConnectGetTask();
        JSONArray playedSongs = new JSONArray();
        JSONArray allSongs = new JSONArray();
        try {
            allSongs = new JSONArray(getAllSongs.execute(StaticData.URL_REST_BASE_FILES).get());
            playedSongs = new JSONArray(getPlayedSongs.execute(String.format(StaticData.URL_GET_PLAYED_BY_USER, User.name)).get());
            StaticData.playedSongs = playedSongs;
            StaticData.allSongs = allSongs;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
