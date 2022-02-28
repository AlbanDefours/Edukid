package fr.dut.ptut2021.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private final String NAME = "MyPref";
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private static MySharedPreferences instance;

    public static MySharedPreferences getInstance() {
        if (instance == null) {
            instance = new MySharedPreferences();
        }
        return instance;
    }

    public void sharedPreferences(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(NAME, 0);

        if (editor == null)
            editor = sharedPreferences.edit();
    }

    public String getGameName(Context context){
        return getSharedPreferencesString(context, "gameName");
    }

    public int getGameId(Context context){
        return getSharedPreferencesInt(context, "gameId");
    }

    public int getSubGameId(Context context){
        return getSharedPreferencesInt(context, "subGameId");
    }

    public String getSubGameName(Context context){
        return getSharedPreferencesString(context, "subGameName");
    }

    public String getThemeName(Context context){
        return getSharedPreferencesString(context, "themeName");
    }

    public int getUserId(Context context){
        return getSharedPreferencesInt(context, "userId");
    }

    public String getUserImage(Context context){
        return getSharedPreferencesString(context, "userImage");
    }

    public String getUserName(Context context){
        return getSharedPreferencesString(context, "userName");
    }

    public String getSharedPreferencesString(Context context, String name) {
        sharedPreferences(context);
        return sharedPreferences.getString(name, "");
    }

    public int getSharedPreferencesInt(Context context, String name) {
        sharedPreferences(context);
        return sharedPreferences.getInt(name, -1);
    }

    public boolean getSharedPreferencesBoolean(Context context, String name, boolean defaut) {
        sharedPreferences(context);
        return sharedPreferences.getBoolean(name, defaut);
    }

    public void setSharedPreferencesString(Context context, String name, String value) {
        sharedPreferences(context);
        editor.putString(name, value);
    }

    public void setSharedPreferencesInt(Context context, String name, int value) {
        sharedPreferences(context);
        editor.putInt(name, value);
    }

    public void setSharedPreferencesBoolean(Context context, String name, boolean value) {
        sharedPreferences(context);
        editor.putBoolean(name, value);
    }

    public void commit() {
        if (editor != null)
            editor.commit();
    }
}