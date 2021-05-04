package com.coffeehouse.the.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.coffeehouse.the.models.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FetchNotifications extends Fetching {

    public Task<QuerySnapshot> fetchNotifications() {
        return db.collection("notifications").get();
    }
}
