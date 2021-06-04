package com.example.myapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

public final class StaticData {

    //User
  //  public static final String URL_REST_BASE_USERS = "http://188.68.184.242:65001/user/";
    public static final String URL_GET_ALL_USERS = "http://188.68.184.242:65001/user/listUsers";
    public static final String URL_GET_USER_BY_ID = "http://188.68.184.242:65001/user/?id=%s"; // string _id
    public static final String URL_GET_USER_BY_NAME = "http://188.68.184.242:65001/user/?name=%s"; // string username
    public static final String URL_REGISTER = "http://188.68.184.242:65001/user/createUser"; // string name, string password, int avatarId
    public static final String URL_GET_USER_SCORES = "http://188.68.184.242:65001/user/getUserScores";
    public static final String URL_GET_PLAYED_BY_USER_CATEGORY = "http://188.68.184.242:65001/user/getPlayed/%s/%s"; //string username, int category
    public static final String URL_USER_ADD_TO_PLAYED_FILE = "http://188.68.184.242:65001/user/%s/addToPlayed"; // string username, Body jsonobject file (id, artist, title, category)
    public static final String URL_SET_USER_DATA = "http://188.68.184.242:65001/user/setData/%s"; //string username, Body jsonobject (score, wrong, correct)

    public static final String FAILED_REGISTER_USER_NAME = "UserAlreadyExist";

    //File
 //   public static final String URL_REST_BASE_FILES = "http://188.68.184.242:65001/file/";
    public static final String URL_GET_FILES_BY_CATEGORY = "http://188.68.184.242:65001/file/%s/"; //int category
    public static final String URL_DOWNLOAD_FILE = "http://188.68.184.242:65001/file/downloadFile/%s/%s"; // int category, string filename ( artist - title.mp3 )

    public static JSONArray allSongsInCategory = new JSONArray();
    public static JSONArray songsPlayedInCategory = new JSONArray();
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

    public static JSONArray getSongsNotPlayedInCategory() {
        HashSet<JSONObject> hashSetAllSongs = new HashSet<>();
        HashSet<JSONObject> hashSetPlayedSongs = new HashSet<>();
        for (int i = 0; i < allSongsInCategory.length(); i++) {
            try {
                hashSetAllSongs.add(allSongsInCategory.getJSONObject(i));
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }
        for (int j = 0; j < songsPlayedInCategory.length(); j++) {
            try {
                hashSetPlayedSongs.add(songsPlayedInCategory.getJSONObject(j));
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        }
        hashSetAllSongs.removeAll(hashSetPlayedSongs);
        JSONArray result = new JSONArray(hashSetAllSongs);
        Log.i("mylog", "getsongsnotplayed result: " + result.toString());
        return result;
    }

}

