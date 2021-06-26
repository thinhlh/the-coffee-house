package com.coffeehouse.the.LocalData;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class NotificationSharedPreferences {
    private static final String NOTIFICATIONS_PREF = "NOTIFICATIONS_PREF";
    private Context context;

    public NotificationSharedPreferences(Context context) {
        this.context = context;
    }

    public void putStringValue(String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(NOTIFICATIONS_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        SharedPreferences preferences = context.getSharedPreferences(NOTIFICATIONS_PREF, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public void putStringSetValue(String key, Set<String> value) {
        SharedPreferences preferences = context.getSharedPreferences(NOTIFICATIONS_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public Set<String> getStringSetValue(String key) {
        SharedPreferences preferences = context.getSharedPreferences(NOTIFICATIONS_PREF, Context.MODE_PRIVATE);
        Set<String> defaultValue = new HashSet<>();
        return preferences.getStringSet(key, defaultValue);
    }

    public void putIntValue(String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(NOTIFICATIONS_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getIntValue(String key) {
        SharedPreferences preferences = context.getSharedPreferences(NOTIFICATIONS_PREF, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }
}