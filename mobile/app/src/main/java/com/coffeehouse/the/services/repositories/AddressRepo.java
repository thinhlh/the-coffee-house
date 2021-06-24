package com.coffeehouse.the.services.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.UserAddress;
import com.coffeehouse.the.utils.helper.Fetching;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressRepo implements Fetching {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<List<UserAddress>> data = new MutableLiveData<>();

    public AddressRepo() {
        setUpRealTimeListener();
    }

    @Override
    public void setUpRealTimeListener() {
        db.collection("address").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Address Repo", error);
            } else {
                List<UserAddress> list = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    UserAddress userAddress = doc.toObject(UserAddress.class);
                    userAddress.setId(doc.getId());
                    if (userAddress.getUserId().equals(FirebaseAuth.getInstance().getUid()))
                        list.add(userAddress);
                }
                data.setValue(list);
            }
        });
    }

    public void addUserAddress(UserAddress userAddress) {
        db.collection("address").add(userAddress).addOnCompleteListener(task -> {

        });
    }

    public Task<Void> updateAddress(String id, UserAddress userAddress) {
        return db.collection("address").document(id).set(userAddress);
    }

    public Task<Void> deleteUserAddress(String id) {
        return db.collection("address").document(id).delete();
    }

    public LiveData<List<UserAddress>> getUserAddress() {
        return data;
    }
}
