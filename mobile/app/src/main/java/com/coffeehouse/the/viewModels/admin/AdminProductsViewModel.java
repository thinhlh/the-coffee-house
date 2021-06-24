package com.coffeehouse.the.viewModels.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.services.repositories.ProductsRepo;

import java.util.List;

public class AdminProductsViewModel extends ViewModel {
    private final ProductsRepo repo = new ProductsRepo();

    public LiveData<List<Product>> getProducts() {
        return repo.getProducts();
    }

    public void removeProduct(int index) {
        repo.removeProduct(index);
    }

    public void addProduct(Product product, int index) {

    }
}
