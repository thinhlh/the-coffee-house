package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.services.repositories.OrdersRepo;

import java.util.List;

public class OrderHistoryViewModel extends ViewModel {
    private final OrdersRepo ordersRepo;

    public OrderHistoryViewModel() {
        ordersRepo = new OrdersRepo();
    }

    public LiveData<List<Order>> getOrders() {
        return ordersRepo.getOrder();
    }

}
