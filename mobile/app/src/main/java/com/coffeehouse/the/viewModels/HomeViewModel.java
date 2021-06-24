package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.services.repositories.NotificationsRepo;

import java.util.List;

public class HomeViewModel extends ViewModel {

    //Declare repos
    private final NotificationsRepo notificationsRepo;

    public HomeViewModel() {
        notificationsRepo = new NotificationsRepo();
    }

    //Observable data, which could either return LiveData or MutableLiveData
    public LiveData<List<Notification>> getNotifications() {
        return notificationsRepo.getNotifications();
    }
}
