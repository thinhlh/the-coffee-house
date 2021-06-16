package com.coffeehouse.the.services;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Notification;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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

    public Task<Void> removeNotification(int index) {
        String id = notifications.getValue().get(index).getId();
        return storage.getReference().child("images/notifications/" + id).delete()
                .continueWithTask(task -> db.collection("notifications").document(id).delete());
    }

    public Task<Void> addNotification(Notification notification, Uri imageUri) {
        String id = db.collection("notifications").document().getId();
        StorageReference storageReferenceToImage = storage.getReference().child("images/notifications/" + id);
        UploadTask uploadTask = storageReferenceToImage.putFile(imageUri);

        return uploadTask.continueWithTask(upTask -> storageReferenceToImage.getDownloadUrl()).continueWith(uri -> {
            notification.setImageUrl(uri.getResult().toString());
            return null;
        }).continueWithTask(task -> db.collection("notifications").document(id).set(notification.toMap()));
    }

    public Task<Void> updateNotification(Notification notification, Uri imageUri) {
        if (imageUri != null) {
            // Update the image
            StorageReference storageReferenceImage = storage.getReference().child("images/notifications/" + notification.getId());
            UploadTask uploadTask = storageReferenceImage.putFile(imageUri);
            return uploadTask.continueWithTask(upTask -> storageReferenceImage.getDownloadUrl())
                    .continueWith(uri -> {
                        notification.setImageUrl(uri.getResult().toString());
                        return null;
                    })
                    .continueWithTask(task -> db.collection("notifications").document(notification.getId()).update(notification.toMap()));
        } else {
            // Don't update the image
            return db.collection("notifications").document(notification.getId()).update(notification.toMap());
        }

    }
}
