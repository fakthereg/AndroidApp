package com.k1d.wave;

public class User {

    public static String name;
    public static String password;
    public static int avatar;
    public static int avatarId;
    public static int score;
    public static int correct;
    public static int wrong;
    public static int place;

    public static void setAvatarId(int resourceId) {
        if (resourceId == R.drawable.avatar1) {
            User.avatarId = 1;
        }
        if (resourceId == R.drawable.avatar2) {
            User.avatarId = 2;
        }
        if (resourceId == R.drawable.avatar3) {
            User.avatarId = 3;
        }
        if (resourceId == R.drawable.avatar4) {
            User.avatarId = 4;
        }
        if (resourceId == R.drawable.avatar5) {
            User.avatarId = 5;
        }
        if (resourceId == R.drawable.avatar6) {
            User.avatarId = 6;
        }
        if (resourceId == R.drawable.avatar7) {
            User.avatarId = 7;
        }
        if (resourceId == R.drawable.avatar8) {
            User.avatarId = 8;
        }
        if (resourceId == R.drawable.avatar9) {
            User.avatarId = 9;
        }
    }

    public static void setAvatarResourceId(int avatarId) {
        switch (avatarId) {
            case 1:
                User.avatar = R.drawable.avatar1;
                break;
            case 2:
                User.avatar = R.drawable.avatar2;
                break;
            case 3:
                User.avatar = R.drawable.avatar3;
                break;
            case 4:
                User.avatar = R.drawable.avatar4;
                break;
            case 5:
                User.avatar = R.drawable.avatar5;
                break;
            case 6:
                User.avatar = R.drawable.avatar6;
                break;
            case 7:
                User.avatar = R.drawable.avatar7;
                break;
            case 8:
                User.avatar = R.drawable.avatar8;
                break;
            default:
                User.avatar = R.drawable.avatar9;
                break;
        }
    }

    public static int getAvatarResourceId(int avatarId) {
        switch (avatarId) {
            case 1:
                return R.drawable.avatar1;

            case 2:
                return R.drawable.avatar2;

            case 3:
                return R.drawable.avatar3;

            case 4:
                return R.drawable.avatar4;

            case 5:
                return R.drawable.avatar5;

            case 6:
                return R.drawable.avatar6;

            case 7:
                return R.drawable.avatar7;

            case 8:
                return R.drawable.avatar8;

            default:
                return R.drawable.avatar9;

        }
    }
}
