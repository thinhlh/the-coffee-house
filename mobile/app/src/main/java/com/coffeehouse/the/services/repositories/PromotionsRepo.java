package com.coffeehouse.the.services.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Promotion;
import com.coffeehouse.the.utils.helper.Fetching;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PromotionsRepo implements Fetching {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<Promotion>> data = new MutableLiveData<>();
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
                            if (doc != null) {
                                Promotion promotion = doc.toObject(Promotion.class);
                                if (Date.from(Instant.now()).before(promotion.getExpiryDate()))
                                    list.add(promotion);
                            }
                        }
                        data.setValue(list);
                    }
                });
    }

    public LiveData<List<Promotion>> getPromotions() {
        return data;
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
}