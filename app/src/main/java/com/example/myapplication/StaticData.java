package com.example.myapplication;

public final class StaticData {

    public static final String URL_REST_BASE_USERS = "http://188.68.184.242:65001/user/";
    ;
    public static final String URL_GET_ALL_USERS = "listUsers";
    public static final String URL_GET_USER_BY_ID = "?id=%s";
    public static final String URL_GET_USER_BY_NAME = "?name=%s";
    public static final String URL_REGISTER = "createUser";
    public static final String URL_GET_ALL_CATEGORIES = "getCategories";
    public static final String URL_GET_FILES_BY_CATEGORY = "/%s/";
    public static final String URL_GET_USER_SCORES = "getUserScores";

    public static final String FAILED_REGISTER_USER_NAME = "UserAlreadyExist";

    public static final String URL_REST_BASE_FILES = "http://188.68.184.242:65001/file/";
    public static final String URL_GET_FILE_BY_NAME = "?songName=%s";
    public static final String URL_GET_FILE_BY_FILENAME = "?fileName=%s";

    //   public static final String wsUri = "wss://?"; //??
    //   public static final String AUTHENTICATE_URL = "com.system.authenticate";
    //   public static final String AUTHORIZE_URL = "com.system.authorize";



}

