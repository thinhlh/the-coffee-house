package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.services.ProductsRepo;

import java.util.List;

public class OrderViewModel extends ViewModel {
    private final ProductsRepo productsRepo;

    public OrderViewModel(){ productsRepo = new ProductsRepo(); }

    public LiveData<List<Product>> getProducts(){
        return productsRepo.getProducts();
    }
}
