package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.services.repositories.ProductsRepo;

import java.util.List;

public class FavouriteProductViewModel extends ViewModel {

    private final ProductsRepo productsRepo;

    public FavouriteProductViewModel() {
        productsRepo = new ProductsRepo();
        productsRepo.setUpRTListenerForFavPro();
    }

    public LiveData<List<Product>> getFavProducts() {
        return productsRepo.getFavProductOfUser();
    }
}
