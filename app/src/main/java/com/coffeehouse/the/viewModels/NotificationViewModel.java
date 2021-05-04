package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.services.NotificationsRepo;

import java.util.List;

public class NotificationViewModel extends ViewModel {

    private final NotificationsRepo notificationsRepo;

    public NotificationViewModel() {
        notificationsRepo = new NotificationsRepo();
    }

    public LiveData<List<Notification>> getNotifications() {
        return notificationsRepo.getData();
    }
}
