package com.coffeehouse.the.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.models.Store;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StoresRepo implements Fetching {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final MutableLiveData<List<Store>> stores = new MutableLiveData<>();
    private final MutableLiveData<List<Store>> filters = new MutableLiveData<>();

    public StoresRepo() {
        setUpRealTimeListener();
    }

    public LiveData<List<Store>> getStores() {
        return stores;
    }

    @Override
    public void setUpRealTimeListener() {
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

    public void setUpRealTimeFilter(String s) {
        db.collection("stores").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Stores Repo", error);
            } else {
                List<Store> _stores = new ArrayList<>();
                for (QueryDocumentSnapshot doc : Objects.requireNonNull(value)) {
                    if (doc != null) {
                        Store store = Store.fromQueryDocumentSnapshot(doc);
                        Log.d("", store.toString());
                        if (store.getAddress().toLowerCase().contains(s.toLowerCase()))
                            _stores.add(store);
                    }
                }
                this.filters.setValue(_stores);
            }
        });
    }

    public LiveData<List<Store>> filterStores() {
        return filters;
    }
}
