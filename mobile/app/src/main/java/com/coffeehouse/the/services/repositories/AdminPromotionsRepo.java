package com.coffeehouse.the.services.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Promotion;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class AdminPromotionsRepo {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final MutableLiveData<List<Promotion>> promotions = new MutableLiveData<>();

    public AdminPromotionsRepo() {
        setUpRealTimeListener();
    }

    private void setUpRealTimeListener() {
        db.collection("promotions").orderBy("expiryDate", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.w("Promotions Repo", error);
                    } else {
                        List<Promotion> list = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc != null) {
                                Promotion promotion = doc.toObject(Promotion.class);
                                promotion.setId(doc.getId());
                                list.add(promotion);
                            }
                        }
                        promotions.setValue(list);
                    }
                });
    }

    public LiveData<List<Promotion>> getPromotions() {
        return promotions;
    }

    public Task<Void> removePromotion(int position) {
        String id = promotions.getValue().get(position).getId();
        return storage.getReference().child("images/promotions/" + id).delete()
                .continueWithTask(task -> db.collection("promotions").document(id).delete());
    }
}
