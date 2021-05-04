package com.coffeehouse.the.services;

import androidx.annotation.NonNull;

import com.coffeehouse.the.models.CustomUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

public class FetchUser extends Fetching {

    public Task<DocumentSnapshot> fetchUser() {

        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) uid = "";
        return db.collection("users").document(uid).get();
    }
}
