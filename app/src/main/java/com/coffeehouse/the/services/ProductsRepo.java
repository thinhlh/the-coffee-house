package com.coffeehouse.the.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Product;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductsRepo implements Fetching {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<Product>> data = new MutableLiveData<>();
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
                        Product product = doc.toObject(Product.class);
                        product.setId(doc.getId());
                        currentProducts.add(product);
                    }
                }
                data.setValue(currentProducts);
            }
        });
    }


    private Task<QuerySnapshot> fetchProducts() {
        return db.collection("products").get();
    }

    public LiveData<List<Product>> getProducts() {
        return data;
    }

    public LiveData<List<Product>> getProductsOfCategory(String categoryId) {
        List<Product> list = new ArrayList<>();

        for (Product product : data.getValue()) {
            if (product.getCategoryId().equals(categoryId))
                list.add(product);
        }

        MutableLiveData<List<Product>> res = new MutableLiveData<>();
        res.setValue(list);
        return res;
    }

    public Product getProductsById(String productId) {
        for (Product product : data.getValue()) {
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

    public LiveData<List<Product>> filterProduct() {
        return filters;
    }

    public void removeProduct(int index) {
        data.getValue().remove(index);
    }

    public void addProduct(Product product, int index) {
        data.getValue().add(index, product);
    }

}
