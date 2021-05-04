package com.coffeehouse.the.services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class NotificationsRepo extends Fetching {


    private List<Notification> notifications;

    private MutableLiveData<List<Notification>> data = new MutableLiveData<>();

    public NotificationsRepo() {
        Log.w("", "HERE");
        fetchData();

    }

    private void fetchData() {
        if (notifications == null) {
            notifications = new ArrayList<>();
            fetchNotifications().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Notification notification = document.toObject(Notification.class);
                        Log.d("TITLE", notification.getTitle());
                        notification.setId(document.getId());
                        notifications.add(notification);
                        data.setValue(notifications);
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
}
