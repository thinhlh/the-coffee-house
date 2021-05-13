package com.coffeehouse.the.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.CustomUser;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.api.services.people.v1.PeopleService;
import com.google.firebase.firestore.DocumentSnapshot;

public class UserRepo extends Fetching {

    private CustomUser user = new CustomUser();

    private MutableLiveData<CustomUser> data = new MutableLiveData<>();


    private Task<Boolean> isRegistered(String uid) {
        return db.collection("users").document(uid).get().continueWith(task -> {
            if (task.isSuccessful()) {
                return task.getResult().exists();
            }
            return false;
        });

    }

    //For email,password sign in
    public Task<CustomUser> fetchUser(String uid) {
        return db.collection("users").document(uid).get().continueWith(task -> {
            if (!task.getResult().exists())
                createUser(uid).addOnCompleteListener(task1 -> user = task1.getResult());
            else
                user = task.getResult().toObject(CustomUser.class);
            data.setValue(user);
            return user;
        });
    }

    //For google sign in
    public Task<CustomUser> fetchUser(GoogleSignInAccount account) {

        return db.collection("users").document(account.getId()).get().continueWith(task -> {
            if (!task.getResult().exists())
                createUser(account).addOnCompleteListener(task1 -> user = task1.getResult());
            else
                user = task.getResult().toObject(CustomUser.class);
            data.setValue(user);
            return user;
        });
    }

    public Task<CustomUser> createUser(String uid, CustomUser user) {
        return db.collection("users").document(uid).set(user).continueWith(task -> {
            this.user = user;
            return user;
        });
    }

    //Blank user for Email and Password sign up
    public Task<CustomUser> createUser(String uid) {
        this.user = new CustomUser();
        return db.collection("users").document(uid).set(this.user).continueWith(task -> user);
    }

    //User with some information for Google Sign In
    public Task<CustomUser> createUser(GoogleSignInAccount account) {
        CustomUser user = new CustomUser();
        user.setEmail(account.getEmail());
        user.setName(account.getDisplayName());
        this.user = user;
        return db.collection("users").document(account.getId()).set(this.user).continueWith(task -> user);
    }

    public LiveData<CustomUser> getUser() {
        return data;
    }


}
