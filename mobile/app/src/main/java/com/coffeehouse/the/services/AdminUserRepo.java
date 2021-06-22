package com.coffeehouse.the.services;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminUserRepo {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<Void> promoteToAdmin(String uid){
        return db.collection("users").document(uid).update("admin",true);
    }

    public Task<Void> demoteToMember(String uid){
        return db.collection("users").document(uid).update("admin",false);
    }
}
