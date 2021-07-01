package com.coffeehouse.the.services.repositories;

import android.net.Uri;

import com.coffeehouse.the.models.Store;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AdminStoreRepo {
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<Void> addStore(Store store, Uri imageUri) {
        String id = db.collection("stores").document().getId();
        StorageReference storageReferenceToImage = storage.getReference("images/stores/" + id);
        UploadTask uploadTask = storageReferenceToImage.putFile(imageUri);
        return uploadTask.continueWithTask(upTask -> storageReferenceToImage.getDownloadUrl()).continueWith(uri -> {
            store.setImageUrl(uri.getResult().toString());
            return null;
        }).continueWithTask(task -> db.collection("stores").document(id).set(store.toMap()));
    }

    public Task<Void> updateStore(Store store, Uri imageUri) {
        if (imageUri != null) {
            // Update the image
            StorageReference storageReferenceImage = storage.getReference().child("images/stores/" + store.getId());
            UploadTask uploadTask = storageReferenceImage.putFile(imageUri);
            return uploadTask.continueWithTask(upTask -> storageReferenceImage.getDownloadUrl())
                    .continueWith(uri -> {
                        store.setImageUrl(uri.getResult().toString());
                        return null;
                    })
                    .continueWithTask(task -> db.collection("stores").document(store.getId()).update(store.toMap()));
        } else {
            // Don't update the image
            return db.collection("stores").document(store.getId()).update(store.toMap());
        }
    }
}
