package com.coffeehouse.the.viewModels.admin;

import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Store;
import com.coffeehouse.the.services.repositories.AdminStoreRepo;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class AdminEditStoreViewModel extends ViewModel {
    private final AdminStoreRepo repo = new AdminStoreRepo();

    public Task<Void> onSubmitStore(Store store, Intent intent) {
        if (store.getId().isEmpty()) {
            return repo.addStore(store, intent.getData());
        } else {
            return repo.updateStore(store, intent == null ? null : intent.getData());
        }
    }
}
