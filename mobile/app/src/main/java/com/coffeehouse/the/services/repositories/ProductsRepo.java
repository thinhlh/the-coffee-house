package com.coffeehouse.the.services.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.utils.helper.Fetching;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ProductsRepo implements Fetching {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final MutableLiveData<List<Product>> products = new MutableLiveData<>();
    private final MutableLiveData<List<Product>> dataFavProduct = new MutableLiveData<>();
    private final MutableLiveData<List<Product>> filters = new MutableLiveData<>();

    public ProductsRepo() {
        setUpRealTimeListener();
    }

    @Override
    public void setUpRealTimeListener() {
        db.collection("products").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Products Repo", error);
            } else {
                List<Product> currentProducts = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        Product product = new Product();
                        product = doc.toObject(Product.class);
                        product.setId(doc.getId());
                        currentProducts.add(product);
                    }
                }
                products.setValue(currentProducts);
            }
        });
    }

    private Task<QuerySnapshot> fetchProducts() {
        return db.collection("products").get();
    }

    public LiveData<List<Product>> getProducts() {
        return products;
    }

    public LiveData<List<Product>> getProductsOfCategory(String categoryId) {
        if (categoryId.equals("0"))
            return products;

        List<Product> list = new ArrayList<>();

        for (Product product : products.getValue()) {
            if (product.getCategoryId().equals(categoryId))
                list.add(product);
        }

        MutableLiveData<List<Product>> res = new MutableLiveData<>();
        res.setValue(list);
        return res;
    }

    public Task<List<Product>> getProductsByIds(List<String> ids) {
        return db.collection("products").whereIn(FieldPath.documentId(), ids).get().continueWith(task -> {
            List<Product> currentProducts = new ArrayList<>();
            if (!task.isSuccessful()) {
                Log.w("Products Repo", task.getException().getMessage());
            } else {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    if (doc != null) {
                        Product product = doc.toObject(Product.class);
                        product.setId(doc.getId());
                        currentProducts.add(product);
                    }
                }
            }
            return currentProducts;
        });
    }

    public Product getProductById(String productId) {
        for (Product product : products.getValue()) {
            if (product.getId().equals(productId))
                return product;
        }
        return null;
    }

    //FAVORITE PRODUCT REGION
    public void setUpRTListenerForFavPro() {
        db.collection("products").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Products Repo", error);
            } else {
                List<Product> favProducts = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        Product product = doc.toObject(Product.class);
                        product.setId(doc.getId());
                        if (UserRepo.user.getFavoriteProducts().contains(product.getId())) {
                            favProducts.add(product);
                        }
                    }
                }
                dataFavProduct.setValue(favProducts);
            }
        });
    }

    public LiveData<List<Product>> getFavProductOfUser() {
        return dataFavProduct;
    }


    //FILTER PRODUCT REGION
    public void setUpRTListenerForFilterProduct(String s) {
        db.collection("products").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Products Repo", error);
            } else {
                List<Product> filterProducts = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        Product product = doc.toObject(Product.class);
                        product.setId(doc.getId());
                        if (product.getTitle().toLowerCase().contains(s.toLowerCase()))
                            filterProducts.add(product);
                    }
                }
                filters.setValue(filterProducts);
            }
        });
    }

    public Task<Void> removeProduct(int index) {
        String id = products.getValue().get(index).getId();
        return storage.getReference().child("images/products/" + id).delete()
                .continueWithTask(task -> db.collection("products").document(id).delete());
    }

    public LiveData<List<Product>> filterProduct() {
        return filters;
    }


}
