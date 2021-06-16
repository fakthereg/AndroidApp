package com.k1d.wave;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class StaticData {
    public static final String LOG_TAG = "mytag";

    //User
    public static final String URL_REST_BASE_USERS = "http://188.68.184.242:65001/user/";
    public static final String URL_GET_ALL_USERS = URL_REST_BASE_USERS + "listUsers";
    public static final String URL_GET_USER_BY_ID = URL_REST_BASE_USERS + "?id=%s"; // string _id
    public static final String URL_GET_USER_BY_NAME = URL_REST_BASE_USERS + "?name=%s"; // string username
    public static final String URL_REGISTER = URL_REST_BASE_USERS + "createUser"; // string name, string password, int avatarId
    public static final String URL_GET_USER_SCORES = URL_REST_BASE_USERS + "getUserScores";
    public static final String URL_GET_PLAYED_BY_USER = URL_REST_BASE_USERS + "getPlayed/%s"; //string username
    public static final String URL_USER_ADD_TO_PLAYED = URL_REST_BASE_USERS + "addToPlayed/%s"; // string username, Body jsonobject file (id, artist, title, category)
    public static final String URL_SET_USER_DATA = URL_REST_BASE_USERS + "setData/%s"; //string username, Body jsonobject (score, wrong, correct)

    public static final String FAILED_REGISTER_USER_NAME = "UserAlreadyExist";

    //File
    public static final String URL_REST_BASE_FILES = "http://188.68.184.242:65001/file/";
    public static final String URL_GET_FILES_BY_CATEGORY = URL_REST_BASE_FILES + "%s/"; //int category
    public static final String URL_DOWNLOAD_FILE = URL_REST_BASE_FILES + "downloadFile/%s/%s"; // int category, string filename ( artist - title.mp3 )

    public static final int NUMBER_OF_CATEGORIES = 10;

    public static JSONArray allSongs = new JSONArray();
    public static JSONArray playedSongs = new JSONArray();
    public static int songsLeftInChosenCategory = 0;
    public static int chosenCategory = 0;
    public static String chosenSongArtist = "";
    public static String chosenSongTitle = "";
    public static boolean answerIsCorrect = false;
    public static int scoreGain = 0;
    public static boolean answered = false;
    public static JSONObject songToGuess;
    //   public static final String wsUri = "wss://?"; //??
    //   public static final String AUTHENTICATE_URL = "com.system.authenticate";
    //   public static final String AUTHORIZE_URL = "com.system.authorize";

    public static String getCategoryAsString(int category) {
        switch (category) {
            case 0:
                return "Электроника";
            case 1:
                return "Русский рок";
            case 2:
                return "Зарубежный рок";
            case 3:
                return "Old school";
            case 4:
                return "Русский поп";
            case 5:
                return "Espanol";
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
        for (int i = 0; i < playedSongsInCategory.length(); i++) {
            for (int j = 0; j < allSongsInCategory.length(); j++) {
                try {
                    if (playedSongsInCategory.getJSONObject(i).getString("filename").equals(allSongsInCategory.getJSONObject(j).getString("filename"))) {
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

    public static JSONArray getAnsweredSongsInCategory (int category){
        JSONArray result = new JSONArray();
        JSONArray played = getPlayedSongsInCategory(category);
        for (int i = 0; i < played.length(); i++ ){
            try {
                if (played.getJSONObject(i).getBoolean("answered")){
                result.put(played.getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static int getCorrectSongsInCategoryCount(int category){
        int result = 0;
        JSONArray answered = getAnsweredSongsInCategory(category);
        for (int i = 0; i < answered.length(); i++) {
            try {
                if (answered.getJSONObject(i).getBoolean("correct")){
                    result++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

