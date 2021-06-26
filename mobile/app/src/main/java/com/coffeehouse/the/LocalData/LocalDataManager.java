package com.coffeehouse.the.LocalData;

import android.content.Context;

import java.util.Set;

public class LocalDataManager {
    private static LocalDataManager instance;
    private NotificationSharedPreferences sharedPreferences;
    private static final String NOTIFICATIONS_READ_PREF = "NOTIFICATIONS_READ_PREF";
    private static final String NOTIFICATIONS_COUNT_PREF = "NOTIFICATIONS_COUNT_PREF";
    private static final String CURRENT_USER_PREF = "CURRENT_USER_PREF";

    public static void init(Context context) {
        instance = new LocalDataManager();
        instance.sharedPreferences = new NotificationSharedPreferences(context);
    }

    public static LocalDataManager getInstance() {
        if (instance == null) {
            instance = new LocalDataManager();
        }
        return instance;
    }

    public static void setCurrentUserId(String userId) {
        LocalDataManager.instance.sharedPreferences.putStringValue(CURRENT_USER_PREF, userId);
    }

    public static String getCurrentUserId() {
        return LocalDataManager.instance.sharedPreferences.getStringValue(CURRENT_USER_PREF);
    }

    public static void setReadNotifications(Set<String> readNotifications) {
        LocalDataManager.instance.sharedPreferences.putStringSetValue(NOTIFICATIONS_READ_PREF, readNotifications);
    }

    public static Set<String> getReadNotifications() {
        return LocalDataManager.instance.sharedPreferences.getStringSetValue(NOTIFICATIONS_READ_PREF);
    }

    public static void setCountNotifications(int countNotifications) {
        LocalDataManager.instance.sharedPreferences.putIntValue(NOTIFICATIONS_COUNT_PREF, countNotifications);
    }

    public static int getCountNotifications() {
        return LocalDataManager.instance.sharedPreferences.getIntValue(NOTIFICATIONS_COUNT_PREF);
    }
}