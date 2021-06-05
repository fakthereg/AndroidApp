package com.example.myapplication;

import android.os.Build;
import android.util.JsonReader;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashSet;

public final class StaticData {
    public static final String LOG_TAG = "mytag";

    //User
    //  public static final String URL_REST_BASE_USERS = "http://188.68.184.242:65001/user/";
    public static final String URL_GET_ALL_USERS = "http://188.68.184.242:65001/user/listUsers";
    public static final String URL_GET_USER_BY_ID = "http://188.68.184.242:65001/user/?id=%s"; // string _id
    public static final String URL_GET_USER_BY_NAME = "http://188.68.184.242:65001/user/?name=%s"; // string username
    public static final String URL_REGISTER = "http://188.68.184.242:65001/user/createUser"; // string name, string password, int avatarId
    public static final String URL_GET_USER_SCORES = "http://188.68.184.242:65001/user/getUserScores";
    public static final String URL_GET_PLAYED_BY_USER = "http://188.68.184.242:65001/user/getPlayed/%s"; //string username
    public static final String URL_USER_ADD_TO_PLAYED = "http://188.68.184.242:65001/user/addToPlayed/%s"; // string username, Body jsonobject file (id, artist, title, category)
    public static final String URL_SET_USER_DATA = "http://188.68.184.242:65001/user/setData/%s"; //string username, Body jsonobject (score, wrong, correct)

    public static final String FAILED_REGISTER_USER_NAME = "UserAlreadyExist";

    //File
    public static final String URL_REST_BASE_FILES = "http://188.68.184.242:65001/file/";
    public static final String URL_GET_FILES_BY_CATEGORY = "http://188.68.184.242:65001/file/%s/"; //int category
    public static final String URL_DOWNLOAD_FILE = "http://188.68.184.242:65001/file/downloadFile/%s/%s"; // int category, string filename ( artist - title.mp3 )

    public static JSONArray allSongs = new JSONArray();
    public static JSONArray playedSongs = new JSONArray();
    public static JSONArray songsNotPlayedInChosenCategory = new JSONArray();
    public static int songsLeftInChosenCategory = 0;
    //public static JSONArray allSongsInCategory = new JSONArray();
    //   public static JSONArray songsPlayedInCategory = new JSONArray();
    public static int chosenCategory = 0;
    public static String chosenSongArtist = "";
    public static String chosenSongTitle = "";
    public static boolean currentAnswer = false;
    public static int scoreGain = 0;
    public static boolean answered = false;
    //   public static final String wsUri = "wss://?"; //??
    //   public static final String AUTHENTICATE_URL = "com.system.authenticate";
    //   public static final String AUTHORIZE_URL = "com.system.authorize";

    public static String getChosenCategory() {
        switch (chosenCategory) {
            case 0:
                return "Электроника";
            case 1:
                return "Русский рок";
            case 2:
                return "Зарубежный рок";
            case 3:
                return "Old school";
            case 4:
                return "Русский поп вне времени";
            case 5:
                return "Латино";
            case 6:
                return "Зашквар";
            case 7:
                return "Зарубежный поп";
            case 8:
                return "Rap";
            case 9:
                return "Eurovision";
        }
        return "xz";
    }

    public static JSONArray getSongsNotPlayedInCategory(int category) {

        JSONArray allSongsInCategory = getAllSongsInCategory(category);
        JSONArray playedSongsInCategory = getPlayedSongsInCategory(category);
        for (int i=0; i< playedSongsInCategory.length(); i++){
            for (int j=0; j<allSongsInCategory.length(); j++){
                try {
                    if (playedSongsInCategory.getJSONObject(i).getString("filename").equals(allSongsInCategory.getJSONObject(j).getString("filename"))){
                        allSongsInCategory.remove(j);
                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        songsLeftInChosenCategory = allSongsInCategory.length();
        return allSongsInCategory;
      /*  HashSet<JSONObject> hashSetAllSongsInCategory = new HashSet<>();
        HashSet<JSONObject> hashSetPlayedSongsInCategory = new HashSet<>();
        JSONArray allSongsInCategory = getAllSongsInCategory(category);
        JSONArray playedSongsInCategory = getPlayedSongsInCategory(category);
        try {
            for (int i = 0; i < allSongsInCategory.length(); i++) {
                hashSetAllSongsInCategory.add(allSongsInCategory.getJSONObject(i));
            }
            for (int j = 0; j < playedSongsInCategory.length(); j++) {
                hashSetPlayedSongsInCategory.add(allSongsInCategory.getJSONObject(j));
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        for (JSONObject jsonObject : hashSetPlayedSongsInCategory) {

            hashSetAllSongsInCategory.remove(jsonObject);

        }
        //  hashSetAllSongsInCategory.removeAll(hashSetPlayedSongsInCategory);
        JSONArray result = new JSONArray(hashSetAllSongsInCategory);
        songsLeftInChosenCategory = result.length();
        Log.i(LOG_TAG, "getSongsNotPlayedInCategory returned:   category: " + category + "  result: " + result.toString());
        return result;*/
    }


    public static JSONArray getAllSongsInCategory(int category) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < allSongs.length(); i++) {
            try {
                if (allSongs.getJSONObject(i).getInt("category") == category) {
                    result.put(allSongs.getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i(LOG_TAG, "getAllSongsInCategory returned:   category = " + category + "  result = " + result.toString());
        return result;
    }

    public static JSONArray getPlayedSongsInCategory(int category) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < playedSongs.length(); i++) {
            try {
                if (playedSongs.getJSONObject(i).getInt("category") == category) {
                    result.put(playedSongs.getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(LOG_TAG, "getPlayedSongsInCategory returned " + result.toString());
        return result;
    }
}

