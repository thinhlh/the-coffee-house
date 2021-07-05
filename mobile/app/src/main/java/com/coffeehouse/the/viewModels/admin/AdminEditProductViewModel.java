package com.coffeehouse.the.viewModels.admin;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.services.repositories.AdminProductRepo;
import com.coffeehouse.the.services.repositories.CategoriesRepo;
import com.coffeehouse.the.services.repositories.ProductsRepo;
import com.coffeehouse.the.views.admin.AdminEditProduct;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class AdminEditProductViewModel extends ViewModel {
    final CategoriesRepo categoriesRepo = new CategoriesRepo();
    final AdminProductRepo productsRepo = new AdminProductRepo();

    public LiveData<List<Category>> getCategories() {
        return categoriesRepo.getCategories();
    }

    public Task<Void> onSubmitProduct(Product product, Intent imageData) {
        if (product.getId().isEmpty()) {
            return productsRepo.addProduct(product, imageData.getData());
        } else {
            return productsRepo.updateProduct(product, imageData == null ? null : imageData.getData());
        }
    }
}
