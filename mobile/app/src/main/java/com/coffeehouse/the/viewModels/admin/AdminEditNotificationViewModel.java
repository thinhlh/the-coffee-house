package com.coffeehouse.the.viewModels.admin;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.services.NotificationsRepo;
import com.google.android.gms.tasks.Task;

public class AdminEditNotificationViewModel extends ViewModel {
    private final NotificationsRepo repo = new NotificationsRepo();

    public Task<Void> onSubmitNotification(Notification notification, Intent imageData) {
        // Add notification
        if (notification.getId().equals("")) {
            // Add notification, image Data must not equal to null
            return repo.addNotification(notification, imageData.getData());
        } else {
            return repo.updateNotification(notification, imageData == null ? null : imageData.getData());
        }
    }
}