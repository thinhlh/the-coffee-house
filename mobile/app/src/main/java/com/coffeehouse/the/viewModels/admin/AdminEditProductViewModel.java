package com.coffeehouse.the.viewModels.admin;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.services.CategoriesRepo;
import com.coffeehouse.the.services.ProductsRepo;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class AdminEditProductViewModel extends ViewModel {
    final CategoriesRepo categoriesRepo = new CategoriesRepo();
    final ProductsRepo productsRepo = new ProductsRepo();

    public LiveData<List<Category>> getCategories() {
        return categoriesRepo.getCategories();
    }

    public Task<Void> onSubmitProduct(Product product, Intent imageData) {
        if (product.getId().equals("")) {
            return productsRepo.addProduct(product, imageData.getData());
        } else {
            return productsRepo.updateProduct(product, imageData == null ? null : imageData.getData());
        }
    }
}
