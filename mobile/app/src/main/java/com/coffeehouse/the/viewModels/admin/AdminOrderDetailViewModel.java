package com.coffeehouse.the.viewModels.admin;

import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.services.repositories.AdminOrdersRepo;
import com.coffeehouse.the.services.repositories.ProductsRepo;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderDetailViewModel extends ViewModel {

    private final ProductsRepo productsRepo = new ProductsRepo();
    private final AdminOrdersRepo adminOrdersRepo = new AdminOrdersRepo();

    /*
     * This is used for getting all product that all cart items need*/
    public Task<List<Product>> getProductsOfCartItems(List<CartItem> cartItems) {
        List<String> productsId = new ArrayList<>();
        cartItems.forEach(cartItem -> {
            productsId.add(cartItem.getProductId());
        });
        return productsRepo.getProductsByIds(productsId);
    }

    public Task<Void> delivery(String orderId) {
        return adminOrdersRepo.delivery(orderId);
    }
}
