package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.services.repositories.ProductsRepo;

import java.util.List;

public class OrderViewModel extends ViewModel {
    private final ProductsRepo productsRepo;

    public OrderViewModel() {
        productsRepo = new ProductsRepo();
    }

    public LiveData<List<Product>> getProducts() {
        return productsRepo.getProducts();
    }

    public LiveData<List<Product>> getProductsOfCategory(String categoryID) {
        return productsRepo.getProductsOfCategory(categoryID);
    }

    public LiveData<List<Product>> getFilterProduct(String s) {
        productsRepo.setUpRTListenerForFilterProduct(s);
        return productsRepo.filterProduct();
    }

}