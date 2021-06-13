package com.coffeehouse.the.viewModels.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.services.NotificationsRepo;

import java.util.List;

public class AdminNotificationsViewModel extends ViewModel {

    private final NotificationsRepo repo = new NotificationsRepo();

    public LiveData<List<Notification>> getNotifications() {
        return repo.getNotifications();
    }

    public void removeANotification(int position){
        repo.removeNotification(position);
    }
}
