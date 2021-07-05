package com.coffeehouse.the.services.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.utils.helper.Fetching;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class CategoriesRepo implements Fetching {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final MutableLiveData<List<Category>> categories = new MutableLiveData<>();

    public CategoriesRepo() {
        setUpRealTimeListener();
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    @Override
    public void setUpRealTimeListener() {
        db.collection("categories").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Categories Repository", error);
            } else {
                List<Category> currentCategories = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        Category category = new Category();
                        category = doc.toObject(Category.class);
                        category.setId(doc.getId());
                        currentCategories.add(category);
                    }
                }
                categories.setValue(currentCategories);
            }
        });
    }


    public Task<Void> deleteCategory(int position) {
        String id = categories.getValue().get(position).getId();
        return FirebaseStorage.getInstance().getReference("images/categories/" + id).delete()
                .continueWithTask(task -> db.collection("categories").document(id).delete());
    }

    public Task<List<Category>> getCategoriesTask() {
        return db.collection("categories").get().continueWith(task -> task.getResult().toObjects(Category.class));
    }
}
