package com.coffeehouse.the.services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.CustomUser;
import com.coffeehouse.the.models.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class NotificationsRepo extends Fetching {

    private List<Notification> notifications;
    private String membership = new FetchUser().getUserMembership();
    private MutableLiveData<List<Notification>> data = new MutableLiveData<>();

    public NotificationsRepo() {
    }

    private void fetchData() {
        if (notifications == null) {
            notifications = new ArrayList<>();
            fetchNotifications().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //fetch by targetCustomer

                        List<String> targetCustomer = new ArrayList<>();
                        targetCustomer = (List<String>) document.get("targetCustomer");
                        if (checkMembership(targetCustomer)) {
                            Notification notification = document.toObject(Notification.class);
                            Log.d("TITLE", notification.getTitle());
                            notification.setId(document.getId());
                            notifications.add(notification);
                            data.setValue(notifications);
                        } else {
                            Log.d("", "Get Notification Failed cause membership doesn't match");
                        }
                    }
                } else {
                    Log.d("", "Error");
                }
            });
        }
    }

    public LiveData<List<Notification>> getData() {
        fetchData();
        return data;
    }

    public Task<QuerySnapshot> fetchNotifications() {
        return db.collection("notifications").get();
    }

    public boolean checkMembership(List<String> targetCustomer) {
        for (int i = 0; i < targetCustomer.size(); i++) {
            if (targetCustomer.get(i).equals(membership))
                return true;
        }
        return false;
    }
}
