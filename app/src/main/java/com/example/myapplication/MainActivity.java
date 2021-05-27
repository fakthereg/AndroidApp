package com.example.myapplication;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.ui.main.fragments.FragmentCategory;
import com.example.myapplication.ui.main.fragments.FragmentLoggedIn;
import com.example.myapplication.ui.main.fragments.FragmentLogin;
import com.example.myapplication.ui.main.fragments.FragmentPanels;
import com.example.myapplication.ui.main.fragments.FragmentPlay;
import com.example.myapplication.ui.main.fragments.FragmentProfile;
import com.example.myapplication.ui.main.fragments.FragmentRating;
import com.example.myapplication.ui.main.fragments.FragmentRegisterOne;
import com.example.myapplication.ui.main.fragments.FragmentRegisterTwo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity /*implements WampInterface*/ {
    //   WebSocketClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //      client = WebSocketClient.getInstance();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.container);
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
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                request_perm.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
            String password = input_password.getText().toString().toLowerCase().trim();
            String password2 = input_password2.getText().toString().toLowerCase().trim();

            if (login.equals("") || password.equals("") || password2.equals("")) {
                Toast.makeText(this, "Заполни все поля!", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(password2)) {
                Toast.makeText(this, "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
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
            score.setText(User.score+"");
            findViewById(R.id.top_panel_score).setVisibility(View.VISIBLE);
            findViewById(R.id.top_panel_coin).setVisibility(View.VISIBLE);
            header.setText("категории");

        } else if (button.getId() == R.id.bottom_panel_exit) {
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
    }

    private void Register() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ConnectPostTask registerTask = new ConnectPostTask();
        JSONObject jsonObject = null;
        String username = "";
        try {
            jsonObject = registerTask.execute(StaticData.URL_REST_BASE_USERS + StaticData.URL_REGISTER).get();
            username = jsonObject.getString("name");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (username.equals(User.name)) {
            fragmentManager.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.
                    replace(R.id.container, FragmentPanels.getInstance()).replace(R.id.panels_container, FragmentLoggedIn.getInstance()).
                    commit();
            //   return StaticData.EVENT_CODE_SUCCESS;
        } else if (username.equals(StaticData.FAILED_REGISTER_USER_NAME)) {
            fragmentManager.popBackStack();
            Toast.makeText(this, "Такой пользователь уже есть!", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Log.d("mytag", jsonObject.toString());
            } catch (NullPointerException exception) {
                exception.printStackTrace();
            }
            Toast.makeText(this, "" + jsonObject.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void Login() {
        ConnectGetTask getAllUsersTask = new ConnectGetTask();
        try {
            EditText loginInput = findViewById(R.id.login_login);
            EditText passwordInput = findViewById(R.id.login_password);
            if (loginInput != null && passwordInput != null) {
                String login = loginInput.getText().toString().toLowerCase().trim();
                String password = passwordInput.getText().toString().toLowerCase().trim();
                JSONArray userList = new JSONArray(getAllUsersTask.execute(StaticData.URL_REST_BASE_USERS + StaticData.URL_GET_ALL_USERS).get());
                for (int i = 0; i < userList.length(); i++) {
                    if (login.equals(userList.getString(i))) {
                        ConnectGetTask getUserTask = new ConnectGetTask();
                        JSONObject userJson = new JSONObject(getUserTask.execute(StaticData.URL_REST_BASE_USERS + String.format(StaticData.URL_GET_USER_BY_NAME, userList.getString(i))).get());
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
            ex.printStackTrace();
        }
    }


    private static class ConnectPostTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            JSONObject result = null;
            StringBuilder answer = new StringBuilder();
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                BufferedOutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(String.format("name=%s&password=%s&avatar=%s", User.name, User.password, User.avatarId + ""));
                writer.flush();
                writer.close();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = reader.readLine();
                while (line != null) {
                    answer.append(line);
                    line = reader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            try {
                result = new JSONObject(answer.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public static class ConnectGetTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL(strings[0]);
                Log.i("mytag", "url ready " + url);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                Log.i("mytag", "connected");
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = reader.readLine();
                Log.i("mytag", "reading input stream");
                while (line != null) {
                    result.append(line);
                    line = reader.readLine();
                }
                Log.i("mytag", "result = " + result.toString());
                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }

    private static class DownloadFileTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            String category = strings[0];
            String filename = strings[1];
            URL url = null;
            HttpURLConnection urlConnection = null;


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
