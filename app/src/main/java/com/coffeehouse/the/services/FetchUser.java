package com.coffeehouse.the.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.coffeehouse.the.models.CustomUser;
import com.coffeehouse.the.models.Membership;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.Objects;

public class FetchUser extends Fetching {

    private CustomUser user;

    public FetchUser() {
    }

    public Task<DocumentSnapshot> fetchUser() {

        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) uid = "";
        return db.collection("users").document(uid).get();
    }

    private void fetchUserData() {
        if (user == null) {
            user = new CustomUser();
            fetchUser().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        user = documentSnapshot.toObject(CustomUser.class);
                        Log.d("USER MEMBERSHIP", user.getMembership());
                    } else {
                        Log.d("ERROR", "Fetch User failed with: ", task.getException());
                    }
                }
            });
        }
    }

    public CustomUser getUserData() {
        fetchUserData();
        return user;
    }

    public String getUserMembership(){
        getUserData();
        return user.getMembership();
    }

}
