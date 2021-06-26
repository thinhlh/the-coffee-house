package com.coffeehouse.the.viewModels.admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.services.repositories.CategoriesRepo;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class AdminCategoriesViewModel extends ViewModel {
    private final CategoriesRepo repo = new CategoriesRepo();

    public LiveData<List<Category>> getCategories() {
        return repo.getCategories();
    }

    public Task<Void> deleteCategory(int position) {
        return repo.deleteCategory(position);
    }
}
