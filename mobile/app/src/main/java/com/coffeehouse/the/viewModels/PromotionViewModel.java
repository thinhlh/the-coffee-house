package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Promotion;
import com.coffeehouse.the.services.repositories.PromotionsRepo;

import java.util.List;

public class PromotionViewModel extends ViewModel {
    private final PromotionsRepo promotionsRepo;

    public PromotionViewModel() {
        promotionsRepo = new PromotionsRepo();
    }

    public LiveData<List<Promotion>> getPromotions() {
        return promotionsRepo.getPromotions();
    }

    public LiveData<List<Promotion>> searchPromotions(String s) {
        promotionsRepo.setUpRealTimeSearch(s);
        return promotionsRepo.getPromotionsSearch();
    }
}