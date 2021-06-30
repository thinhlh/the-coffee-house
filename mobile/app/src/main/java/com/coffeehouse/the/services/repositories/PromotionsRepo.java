package com.coffeehouse.the.services.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Promotion;
import com.coffeehouse.the.utils.helper.Fetching;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PromotionsRepo implements Fetching {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final MutableLiveData<List<Promotion>> promotions = new MutableLiveData<>();
    private final MutableLiveData<List<Promotion>> search = new MutableLiveData<>();

    public PromotionsRepo() {
        setUpRealTimeListener();
    }

    @Override
    public void setUpRealTimeListener() {
        db.collection("promotions").whereArrayContains("targetCustomer", UserRepo.user.getMembership())
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.w("Promotions Repo", error);
                    } else {
                        List<Promotion> list = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            Log.d("PROMOTION", doc.getId());
                            if (doc != null) {
                                Promotion promotion = new Promotion();
                                promotion = doc.toObject(Promotion.class);
                                promotion.setId(doc.getId());
                                if (Date.from(Instant.now()).before(promotion.getExpiryDate()))
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

    public void setUpRealTimeSearch(String s) {
        db.collection("promotions").whereArrayContains("targetCustomer", UserRepo.user.getMembership())
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.w("Promotions Repo", error);
                    } else {
                        List<Promotion> list = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc != null) {
                                Promotion promotion = doc.toObject(Promotion.class);
                                if (Date.from(Instant.now()).before(promotion.getExpiryDate())) {
                                    if (promotion.getCode().equals(s) || s.isEmpty())
                                        list.add(promotion);
                                }
                            }
                        }
                        search.setValue(list);
                    }
                });
    }

    public LiveData<List<Promotion>> getPromotionsSearch() {
        return search;
    }

    public Promotion getPromotionById(String promotionId) {
        for (Promotion promotion : promotions.getValue()) {
            if (promotion.getId().equals(promotionId))
                return promotion;
        }
        return null;
    }
}