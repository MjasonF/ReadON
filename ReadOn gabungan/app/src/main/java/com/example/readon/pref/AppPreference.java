package com.example.readon.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.readon.model.User;

public class AppPreference {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "ReadOnPref";

    public AppPreference(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveSession(User user) {
        editor.putString("username", user.getUsername());
        editor.putString("userId", user.getUserId());
        editor.putBoolean("isAdmin", user.getAdmin());
        editor.putBoolean("isLogin", true);
        editor.apply();
    }

    public void saveFontSize(Integer fontIndex){
        editor.putInt("fontSize", fontIndex);
        editor.apply();
    }

    public String getUserLoggedInUsername() {
        return pref.getString("username", "");
    }

    public String getUserId() {
        return pref.getString("userId", "");
    }

    public Boolean isLogin() {
        return pref.getBoolean("isLogin", false);
    }

    public Boolean isAdmin() { return pref.getBoolean("isAdmin", false); }

    public Integer getFontSize(){
        return pref.getInt("fontSize", 0);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
