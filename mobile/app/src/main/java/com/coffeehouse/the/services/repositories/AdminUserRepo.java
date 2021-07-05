package com.coffeehouse.the.services.repositories;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.coffeehouse.the.models.AdminCustomUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminUserRepo{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<Void> promoteToAdmin(String uid){
        return db.collection("users").document(uid).update("admin",true);
    }

    public Task<Void> demoteToMember(String uid){
        return db.collection("users").document(uid).update("admin",false);
    }

}
