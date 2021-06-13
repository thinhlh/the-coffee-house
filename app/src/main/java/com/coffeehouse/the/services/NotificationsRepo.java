package com.coffeehouse.the.services;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Notification;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotificationsRepo implements Fetching {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();

    private final MutableLiveData<List<Notification>> notifications = new MutableLiveData<>();

    public NotificationsRepo() {
        setUpRealTimeListener();
    }

    public LiveData<List<Notification>> getNotifications() {
        return notifications;
    }

    public int getNumberOfNotifications() {
        return Objects.requireNonNull(notifications.getValue()).size();
    }

    @Override
    public void setUpRealTimeListener() {
        db.collection("notifications").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Notifications Repo", error);
            } else {
                List<Notification> _notifications = new ArrayList<>();
                for (QueryDocumentSnapshot doc : Objects.requireNonNull(value)) {
                    if (doc != null) {
                        Notification notification = doc.toObject(Notification.class);
                        notification.setId(doc.getId());
                        _notifications.add(notification);
                    }
                }
                this.notifications.setValue(_notifications);
            }
        });
    }

    public void removeNotification(int index) {
        notifications.getValue().remove(index);
    }

    public void addNotification(Notification notification, Uri imagrUri) {
        String id = db.collection("notifications").document().getId();
        StorageReference storageReference = storage.getReference().child("images").child("notifications");
        db.collection("notifications").document().set(notification.toMap());
        //TODO IMPLEMENT STORAGE HERE

    }
}
