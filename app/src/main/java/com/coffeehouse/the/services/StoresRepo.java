package com.coffeehouse.the.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Store;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StoresRepo extends Fetching {

    private final MutableLiveData<List<Store>> stores = new MutableLiveData<>();

    public StoresRepo() {
        setUpStoresListener();
    }

    private void setUpStoresListener() {
        db.collection("stores").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Stores Repo", error);
            } else {
                List<Store> _stores = new ArrayList<>();
                for (QueryDocumentSnapshot doc : Objects.requireNonNull(value)) {
                    if (doc != null) {
                        Store store = Store.fromQueryDocumentSnapshot(doc);
                        Log.d("", store.toString());
                        _stores.add(store);
                    }
                }
                this.stores.setValue(_stores);
            }
        });
    }

    public LiveData<List<Store>> getStores() {
        return stores;
    }
}
