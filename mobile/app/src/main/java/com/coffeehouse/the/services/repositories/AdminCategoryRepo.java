package com.coffeehouse.the.services.repositories;

import android.net.Uri;

import com.coffeehouse.the.models.Category;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdminCategoryRepo {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();


    public Task<Void> addCategory(Category category, Uri imageUri) {
        String id = db.collection("categories").document().getId();
        StorageReference storageReferenceToImage = storage.getReference("images/categories/" + id);
        UploadTask uploadTask = storageReferenceToImage.putFile(imageUri);
        return uploadTask.continueWithTask(upTask -> storageReferenceToImage.getDownloadUrl()).continueWith(uri -> {
            category.setImageUrl(uri.getResult().toString());
            return null;
        }).continueWithTask(task -> db.collection("categories").document(id).set(category));
    }

    public Task<Void> updateCategory(Category category, Uri imageUri) {
        if (imageUri != null) {
            // Update the image
            StorageReference storageReferenceImage = storage.getReference().child("images/categories/" + category.getId());
            UploadTask uploadTask = storageReferenceImage.putFile(imageUri);
            return uploadTask.continueWithTask(upTask -> storageReferenceImage.getDownloadUrl())
                    .continueWith(uri -> {
                        category.setImageUrl(uri.getResult().toString());
                        return null;
                    })
                    .continueWithTask(task -> db.collection("categories").document(category.getId()).update(category.toMap()));
        } else {
            // Don't update the image
            return db.collection("products").document(category.getId()).update(category.toMap());
        }
    }
}
