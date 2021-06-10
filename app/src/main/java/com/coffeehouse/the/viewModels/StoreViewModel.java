package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Store;
import com.coffeehouse.the.services.StoresRepo;

import java.util.List;

public class StoreViewModel extends ViewModel {
    private final StoresRepo storesRepo;

    public StoreViewModel() {
        storesRepo = new StoresRepo();
    }

    public LiveData<List<Store>> getStores() {
        return storesRepo.getStores();
    }

}
