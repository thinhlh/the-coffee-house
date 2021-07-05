package com.coffeehouse.the.viewModels.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.services.repositories.AdminProductRepo;
import com.coffeehouse.the.services.repositories.ProductsRepo;

import java.util.List;

public class AdminProductsViewModel extends ViewModel {
    private final AdminProductRepo repo = new AdminProductRepo();
    private final ProductsRepo productsRepo = new ProductsRepo();

    public LiveData<List<Product>> getProducts() {
        return productsRepo.getProducts();
    }

    public void removeProduct(int index) {
        productsRepo.removeProduct(index);
    }
}
