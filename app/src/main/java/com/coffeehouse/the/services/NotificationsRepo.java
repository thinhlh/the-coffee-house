package com.coffeehouse.the.services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.coffeehouse.the.models.CustomUser;
import com.coffeehouse.the.models.Notification;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class NotificationsRepo extends Fetching {

    private List<Notification> notifications;

    private MutableLiveData<List<Notification>> data = new MutableLiveData<>();

    private Task<QuerySnapshot> fetchNotifications() {
        return db.collection("notifications").get();
    }

    private void fetchData() {
        if (notifications == null) {
            notifications = new ArrayList<>();
            fetchNotifications().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userMembership = getUserMembership();
                        List<String> targetMembership = (List<String>) document.get("targetCustomer");
                        if (check(userMembership, targetMembership)){
                            Notification notification = document.toObject(Notification.class);
                            notification.setId(document.getId());
                            notifications.add(notification);

                        } else {
                            Log.d("FETCH NOTIFICATIONS", "Cannot fetch notification cause by user.membership is " + userMembership);
                        }

//                        Notification notification = document.toObject(Notification.class);
//                        notification.setId(document.getId());
//                        notifications.add(notification);
                        data.setValue(notifications);

                    }
                } else {
                    Log.d("", "Fetching Notifications Error");
                }
            });
        }
    }

    private boolean check(String userMembership, List<String> targetMembership) {
        for (int i = 0; i < targetMembership.size(); i++) {
            if (targetMembership.get(i).equals(userMembership))
                return true;
        }
        return false;
    }

    public LiveData<List<Notification>> getNotifications() {
        if (data == null || data.getValue() == null || data.getValue().isEmpty())
            fetchData();
        return data;
    }

    public int getNumberOfNotifications() {
        return notifications.size();
    }

    private String getUserMembership(){
        String uid = FirebaseAuth.getInstance().getUid();
        CustomUser user = new CustomUser();
        UserRepo userRepo = new UserRepo();
        userRepo.fetchUser(uid).addOnCompleteListener(task -> { user.setMembership(task.getResult().getMembership());
        });

        return "Membership." + user.getMembership();
    }
}
