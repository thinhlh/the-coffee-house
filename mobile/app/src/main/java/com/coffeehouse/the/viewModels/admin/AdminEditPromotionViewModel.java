package com.coffeehouse.the.viewModels.admin;

import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Promotion;
import com.coffeehouse.the.services.repositories.AdminPromotionRepo;
import com.google.android.gms.tasks.Task;

public class AdminEditPromotionViewModel extends ViewModel {
    private final AdminPromotionRepo repo = new AdminPromotionRepo();

    public Task<Void> onSubmitPromotion(Promotion promotion, Intent imageData) {
        if (promotion.getId().isEmpty()) {
            return repo.addPromotion(promotion, imageData.getData());
        } else {
            return repo.updatePromotion(promotion, imageData == null ? null : imageData.getData());
        }
    }

}
