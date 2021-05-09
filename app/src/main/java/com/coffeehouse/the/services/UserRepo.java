package com.coffeehouse.the.services;

import android.util.Log;

import com.coffeehouse.the.models.CustomUser;
import com.google.android.gms.tasks.Task;

import java.util.Date;

public class UserRepo extends Fetching {

    public Task<CustomUser> fetchUser(String uid) {
        return db.collection("users").document(uid).get().continueWith(task -> {
            return task.getResult().toObject(CustomUser.class);
        });
    }

    public Task<CustomUser> createUser(String uid, CustomUser user) {
        return db.collection("users").document(uid).set(user).continueWith(task -> user);
    }

    public void signOut() {
    }


}
