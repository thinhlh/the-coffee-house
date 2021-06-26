package com.coffeehouse.the.viewModels.admin;

import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.services.repositories.AdminCategoryRepo;
import com.coffeehouse.the.services.repositories.CategoriesRepo;
import com.google.android.gms.tasks.Task;

public class AdminEditCategoryViewModel extends ViewModel {
    private AdminCategoryRepo repo=new AdminCategoryRepo();

    public Task<Void> onSubmitCategory(Category category, Intent imageData){
        if(category.getId().isEmpty()){
            return repo.addCategory(category,imageData.getData());
        }
        else{
            return repo.updateCategory(category,imageData==null?null:imageData.getData());
        }
    }
}
