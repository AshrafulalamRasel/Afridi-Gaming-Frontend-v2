package com.itvillage.afridigaming.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ApplicationSharedPreferencesUtil {

    private final Context context;

    public ApplicationSharedPreferencesUtil(Context context) {
        this.context = context;
    }

    public void saveAccessToken(String accessToken) {

        SharedPreferences pref = getDentistPointPerf();
        Editor editor = pref.edit();
        editor.putString("accessToken", accessToken);
        editor.commit();
    }

    public String getAccessToken() {

        SharedPreferences pref = getDentistPointPerf();
        return pref.getString("accessToken", null);
    }

    private SharedPreferences getDentistPointPerf() {
        return context.getSharedPreferences("DentistPointPref", Context.MODE_PRIVATE);
    }

    public void putPref(String key, String value) {
        SharedPreferences pref = getDentistPointPerf();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getPref(String key) {
        SharedPreferences pref = getDentistPointPerf();
        return pref.getString(key, null);
    }

    public void clearSharedPreferences(String key) {
        SharedPreferences pref = getDentistPointPerf();
        pref.edit().remove(key).commit();
    }

}
