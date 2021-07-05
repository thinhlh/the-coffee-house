package com.coffeehouse.the.services.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Data hierarchy
 * userId1 ->
 *   seenNotifications=[ notificationId1,notificationId2 ...];
 * userId2 ->
 *   seenNotifications=[ notificationId1,notificationId2 ...]
 * */


public class NotificationsSharedPref {
    //Define a db mSharedPref
    private SharedPreferences mSharedPref;
    private final String SEEN_NOTIFICATIONS = "SEEN_NOTIFICATIONS";
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    public NotificationsSharedPref(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mSharedPref = context.getSharedPreferences(FirebaseAuth.getInstance().getUid(), Context.MODE_PRIVATE);
        this.listener = listener;
        mSharedPref.registerOnSharedPreferenceChangeListener(this.listener);
    }

    public void unRegisterOnSharedPreferenceChangeListener() {
        mSharedPref.unregisterOnSharedPreferenceChangeListener(listener);
    }

    /*
     * Add id of seen notifications
     * */
    public void addNotification(String id) {
        SharedPreferences.Editor editor = mSharedPref.edit();

        Set<String> currentSeenNotifications = seenNotifications();
        if (currentSeenNotifications.add(id)) {
            editor.putStringSet(SEEN_NOTIFICATIONS, currentSeenNotifications);
            editor.apply();
        }
    }


    private Set<String> seenNotifications() {
        return mSharedPref.getStringSet(SEEN_NOTIFICATIONS, new HashSet<>());
    }

    public List<String> getSeenNotifications() {
        return fromSetToString(mSharedPref.getStringSet(SEEN_NOTIFICATIONS, new HashSet<>()));
    }

    private List<String> fromSetToString(Set<String> set) {
        return new ArrayList<>(set);
    }

    public void clearSeenNotifications() {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.remove(SEEN_NOTIFICATIONS);

        editor.apply();
    }
}
