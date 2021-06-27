package com.coffeehouse.the.viewModels.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.services.repositories.NotificationsRepo;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class AdminNotificationsViewModel extends ViewModel {

    private final NotificationsRepo repo = new NotificationsRepo();

    public LiveData<List<Notification>> getNotifications() {
        return repo.getNotifications();
    }

    public Task<Void> removeNotification(int position){
        return repo.removeNotification(position);
    }
}
