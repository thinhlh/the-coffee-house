package com.coffeehouse.the.viewModels.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Promotion;
import com.coffeehouse.the.services.repositories.AdminPromotionRepo;
import com.coffeehouse.the.services.repositories.AdminPromotionsRepo;
import com.coffeehouse.the.services.repositories.PromotionsRepo;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class AdminPromotionsViewModel extends ViewModel {
    public AdminPromotionsRepo repo = new AdminPromotionsRepo();

    public LiveData<List<Promotion>> getPromotions() {
        return repo.getPromotions();
    }

    public Task<Void> deletePromotion(int position) {
        return repo.removePromotion(position);
    }
}
