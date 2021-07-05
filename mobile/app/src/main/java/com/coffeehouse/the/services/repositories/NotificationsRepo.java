package com.coffeehouse.the.services.repositories;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coffeehouse.the.LocalData.LocalDataManager;
import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.utils.helper.Fetching;
import com.coffeehouse.the.utils.commons.Constants;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                int count = 0;
                for (QueryDocumentSnapshot doc : Objects.requireNonNull(value)) {
                    if (doc != null) {
                        Notification notification = new Notification();
                        notification = doc.toObject(Notification.class);
                        notification.setId(doc.getId());
                        if (LocalDataManager.getReadNotifications().contains(notification.getId())) {
                            count++;
                        }
                        _notifications.add(notification);
                    }
                }
                LocalDataManager.setCountNotifications(count);
                this.notifications.setValue(_notifications);
            }
        });
    }

    public Task<Void> removeNotification(int index) {
        String id = notifications.getValue().get(index).getId();
        return storage.getReference().child("images/notifications/" + id).delete()
                .continueWithTask(task -> db.collection("notifications").document(id).delete());
    }

    public Task<Void> addNotification(Notification notification, Uri imageUri, Context context) {
        String id = db.collection("notifications").document().getId();

        StorageReference storageReferenceToImage = storage.getReference().child("images/notifications/" + id);
        UploadTask uploadTask = storageReferenceToImage.putFile(imageUri);

        return uploadTask.continueWithTask(upTask -> storageReferenceToImage.getDownloadUrl()).continueWith(uri -> {
            notification.setImageUrl(uri.getResult().toString());
            return null;
        })
                .continueWithTask(firestoreTask ->
                        db.collection("notifications").document(id).set(notification.toMap())
                ).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        sendNotification(notification, context);
                    }
                });
    }

    private void sendNotification(Notification notification, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, Constants.SERVER_ENDPOINT + "push-notification", response -> {
            //TODO ON SUCCESS
        }, error -> {
            //TODO ERROR HANDLER HERE
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return Constants.BASE_HEADERS;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("title", notification.getTitle());
                    jsonBody.put("body", notification.getDescription());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonBody.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        requestQueue.add(request);
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
