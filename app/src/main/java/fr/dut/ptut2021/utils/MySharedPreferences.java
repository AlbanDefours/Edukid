package fr.dut.ptut2021.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    private static final String NAME = "MyPref";

    public static void sharedPreferences(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(NAME, 0);

        if (editor == null)
            editor = sharedPreferences.edit();
    }

    public static String getGameName(Context context){
        return getSharedPreferencesString(context, "gameName");
    }

    public static String getSubGameName(Context context){
        return getSharedPreferencesString(context, "subGameName");
    }

    public static String getThemeName(Context context){
        return getSharedPreferencesString(context, "themeName");
    }

    public static int getUserId(Context context){
        return getSharedPreferencesInt(context, "userId");
    }

    public static String getUserImage(Context context){
        return getSharedPreferencesString(context, "userImage");
    }

    public static String getUserName(Context context){
        return getSharedPreferencesString(context, "userName");
    }

    public static String getSharedPreferencesString(Context context, String name) {
        sharedPreferences(context);
        return sharedPreferences.getString(name, "");
    }

    public static int getSharedPreferencesInt(Context context, String name) {
        sharedPreferences(context);
        return sharedPreferences.getInt(name, -1);
    }

    public static boolean getSharedPreferencesBoolean(Context context, String name, boolean defaut) {
        sharedPreferences(context);
        return sharedPreferences.getBoolean(name, defaut);
    }

    public static void setSharedPreferencesString(Context context, String name, String value) {
        sharedPreferences(context);
        editor.putString(name, value);
    }

    public static void setSharedPreferencesInt(Context context, String name, int value) {
        sharedPreferences(context);
        editor.putInt(name, value);
    }

    public static void setSharedPreferencesBoolean(Context context, String name, boolean value) {
        sharedPreferences(context);
        editor.putBoolean(name, value);
    }

    public static void commit() {
        if (editor != null)
            editor.commit();
    }
}