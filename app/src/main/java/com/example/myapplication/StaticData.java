package com.example.myapplication;

import org.json.JSONArray;

public final class StaticData {

    public static final String URL_REST_BASE_USERS = "http://188.68.184.242:65001/user/";
    ;
    public static final String URL_GET_ALL_USERS = "listUsers";
    public static final String URL_GET_USER_BY_ID = "?id=%s";
    public static final String URL_GET_USER_BY_NAME = "?name=%s";
    public static final String URL_REGISTER = "createUser";
    public static final String URL_GET_FILES_BY_CATEGORY = "/%s/";
    public static final String URL_GET_USER_SCORES = "getUserScores";
    public static final String URL_GET_PLAYED_BY_USER_CATEGORY = "/getPlayed/%s/%s";
    public static final String URL_USER_ADD_TO_PLAYED_FILE = "/addToPlayed";

    public static final String FAILED_REGISTER_USER_NAME = "UserAlreadyExist";

    public static final String URL_REST_BASE_FILES = "http://188.68.184.242:65001/file/";
    public static final String URL_GET_FILE_BY_NAME = "?songName=%s";
    public static final String URL_GET_FILE_BY_FILENAME = "?fileName=%s";

    public static JSONArray songsInCategory;
    public static int chosenCategory;
    public static String chosenSongArtist;
    public static String chosenSongTitle;
    public static boolean currentAnswer;
    public static int scoreGain;
    public static boolean answered;
    //   public static final String wsUri = "wss://?"; //??
    //   public static final String AUTHENTICATE_URL = "com.system.authenticate";
    //   public static final String AUTHORIZE_URL = "com.system.authorize";

public static String getChosenCategory(){
    switch (chosenCategory){
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

}

