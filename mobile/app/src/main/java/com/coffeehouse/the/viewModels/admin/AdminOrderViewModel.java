package com.coffeehouse.the.viewModels.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.services.repositories.AdminOrdersRepo;

import java.util.List;

public class AdminOrderViewModel extends ViewModel {
    private final AdminOrdersRepo repo = new AdminOrdersRepo();

    public LiveData<List<Order>> getOrders() {
        return repo.getOrder();
    }
}
