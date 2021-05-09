package com.coffeehouse.the.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.CustomUser;
import com.google.android.gms.tasks.Task;

public class UserRepo extends Fetching {

    private CustomUser user = new CustomUser();

    private LiveData<CustomUser> data = new MutableLiveData<>();


    public Task<CustomUser> fetchUser(String uid) {
        return db.collection("users").document(uid).get().continueWith(task -> {
            user = task.getResult().toObject(CustomUser.class);
            return user;
        });
    }

    public Task<CustomUser> createUser(String uid, CustomUser user) {
        return db.collection("users").document(uid).set(user).continueWith(task -> {
            this.user = user;
            return this.user;
        });
    }

    public void signOut() {
    }

    public LiveData<CustomUser> getUser() {
        return data;
    }


}
