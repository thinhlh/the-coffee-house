package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.services.repositories.CategoriesRepo;

import java.util.List;

public class CategoriesViewModel extends ViewModel {
    private final CategoriesRepo categoriesRepo;

    public CategoriesViewModel() {
        categoriesRepo = new CategoriesRepo();
    }

    public LiveData<List<Category>> getCategories() {
        return categoriesRepo.getCategories();
    }
}
