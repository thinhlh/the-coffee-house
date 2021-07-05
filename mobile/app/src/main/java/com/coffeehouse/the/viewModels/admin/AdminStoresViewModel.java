package com.coffeehouse.the.viewModels.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Store;
import com.coffeehouse.the.services.repositories.StoresRepo;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class AdminStoresViewModel extends ViewModel {
    private final StoresRepo repo = new StoresRepo();

    public LiveData<List<Store>> getStores() {
        return repo.getStores();
    }

    public Task<Void> removeStore(int position) {
        //TODO HANDLING REMOVE STORE
        return repo.deleteStore(position);
    }
}
