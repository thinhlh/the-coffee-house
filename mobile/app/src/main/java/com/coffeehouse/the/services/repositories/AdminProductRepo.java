package com.coffeehouse.the.services.repositories;

import android.net.Uri;

import com.coffeehouse.the.models.Product;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class
AdminProductRepo {
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    public Task<Void> addProduct(Product product, Uri imageUri) {
        String id = db.collection("products").document().getId();
        StorageReference storageReferenceToImage = storage.getReference("images/products/" + id);
        UploadTask uploadTask = storageReferenceToImage.putFile(imageUri);
        return uploadTask.continueWithTask(upTask -> storageReferenceToImage.getDownloadUrl()).continueWith(uri -> {
            product.setImageUrl(uri.getResult().toString());
            return null;
        }).continueWithTask(task -> db.collection("products").document(id).set(product.toMap()));
    }

    public Task<Void> updateProduct(Product product, Uri imageUri) {
        if (imageUri != null) {
            // Update the image
            StorageReference storageReferenceImage = storage.getReference().child("images/products/" + product.getId());
            UploadTask uploadTask = storageReferenceImage.putFile(imageUri);
            return uploadTask.continueWithTask(upTask -> storageReferenceImage.getDownloadUrl())
                    .continueWith(uri -> {
                        product.setImageUrl(uri.getResult().toString());
                        return null;
                    })
                    .continueWithTask(task -> db.collection("products").document(product.getId()).update(product.toMap()));
        } else {
            // Don't update the image
            return db.collection("products").document(product.getId()).update(product.toMap());
        }
    }
}
