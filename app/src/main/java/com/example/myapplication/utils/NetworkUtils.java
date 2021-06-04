package com.example.myapplication.utils;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.myapplication.User;

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
import java.nio.charset.StandardCharsets;

public class NetworkUtils {

    public static class ConnectRegisterTask extends AsyncTask<String, Void, JSONObject> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected JSONObject doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            JSONObject result = null;
            StringBuilder answer = new StringBuilder();
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(3000);
                urlConnection.setRequestMethod("POST");
                BufferedOutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                writer.write(String.format("name=%s&password=%s&avatar=%s", User.name, User.password, User.avatarId));
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
            Log.d("mytag", "connect GET task executed");
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL(strings[0]);
                Log.d("mytag", "url ready " + url);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setRequestMethod("GET");
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = reader.readLine();
                while (line != null) {
                    result.append(line);
                    line = reader.readLine();
                }
                Log.d("mytag", "connect GET data returned: " + result.toString());
                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    Log.d("mytag", "connect GET task disconnected");
                }
            }
            Log.d("mytag", "connect GET data returned null!!!");
            return null;
        }
    }

    public static class ConnectPostTask extends AsyncTask<String, Void, JSONArray> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected JSONArray doInBackground(String... strings) {
            Log.d("mytag", "connect POST task executed");
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder answer = new StringBuilder();
            try {
                url = new URL(strings[0]);
                Log.d("mytag", "URL ready " + url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                Log.d("mytag", "string to send: " + strings[1]);
                BufferedOutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                writer.write(strings[1]);
                writer.flush();
                writer.close();
                urlConnection.connect();
                Log.d("mytag", "data sent");
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
                    Log.d("mytag", "connect POST task disconnected");
                }
            }
            JSONArray jsonArray = new JSONArray();
            if (answer.length() > 0) {
                try {
                    jsonArray = new JSONArray(answer.toString());
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
            return jsonArray;
        }
    }

    public static class DownloadFileTask extends AsyncTask<String, Void, Void> {

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
