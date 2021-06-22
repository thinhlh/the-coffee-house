package com.coffeehouse.the.viewModels;

import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.services.UserRepo;
import com.google.android.gms.tasks.Task;

public class SettingsViewModel extends ViewModel {
    private final UserRepo repo=new UserRepo();

    public Task<Void> changeSubscriptionStatus(){
        return repo.changeSubscriptionStatus();
    }
}
