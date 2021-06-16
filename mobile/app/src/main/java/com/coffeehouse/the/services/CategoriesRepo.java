package com.coffeehouse.the.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Category;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoriesRepo extends Fetching {

    private List<Category> categories;

    private MutableLiveData<List<Category>> data = new MutableLiveData<>();

    private Task<QuerySnapshot> fetchCategories() {
        return db.collection("categories").get();
    }

    public CategoriesRepo() {
        getCategories();
    }

    private void fetchData() {
        if (categories == null || categories.isEmpty()) {
            categories = new ArrayList<>();
            fetchCategories().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshots : task.getResult()) {
                        Category category = documentSnapshots.toObject(Category.class);
                        category.setId(documentSnapshots.getId());
                        categories.add(category);
                        data.setValue(categories);
                        Log.d("", category.getTitle());
                    }

                } else {
                    Log.d("", "Fetching Categories Error");
                }
            });
        }
    }

    public LiveData<List<Category>> getCategories() {
        if (data == null || data.getValue() == null || data.getValue().isEmpty()) {
            fetchData();
        }
        return data;
    }

}
