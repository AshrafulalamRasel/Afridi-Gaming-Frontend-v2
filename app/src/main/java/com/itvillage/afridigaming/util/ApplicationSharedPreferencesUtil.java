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
}
