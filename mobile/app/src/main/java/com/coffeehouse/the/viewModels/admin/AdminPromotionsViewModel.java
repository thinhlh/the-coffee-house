package com.coffeehouse.the.viewModels.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Promotion;
import com.coffeehouse.the.services.repositories.PromotionsRepo;

import java.util.List;

public class AdminPromotionsViewModel extends ViewModel {
    public PromotionsRepo repo = new PromotionsRepo();

    public LiveData<List<Promotion>> getPromotions() {
        return repo.getPromotions();
    }
}
