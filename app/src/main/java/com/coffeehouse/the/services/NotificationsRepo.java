package com.coffeehouse.the.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Notification;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotificationsRepo extends Fetching {

    private final MutableLiveData<List<Notification>> notifications = new MutableLiveData<>();

    public NotificationsRepo(){
        setupNotificationsListener();
    }

    private void setupNotificationsListener() {
         db.collection("notifications").addSnapshotListener((value, error) -> {
             if(error!=null){
                 Log.w("Notifications Repo",error);
             }
             else {
                 List<Notification> _notifications= new ArrayList<>();
                 for(QueryDocumentSnapshot doc: Objects.requireNonNull(value)){
                    if(doc!=null){
                        Notification notification = doc.toObject(Notification.class);
                        notification.setId(doc.getId());
                        _notifications.add(notification);
                    }
                 }
                 this.notifications.setValue(_notifications);
             }
         });
    }

    public LiveData<List<Notification>> getNotifications() {
        return notifications;
    }

    public int getNumberOfNotifications() {
        return notifications.getValue().size();
    }


}
