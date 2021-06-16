package com.coffeehouse.the.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.models.Product;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductsRepo extends Fetching {

    private List<Product> products=new ArrayList<>();

    public ProductsRepo() {db.collection("products").addSnapshotListener((value, error) -> {
        for(QueryDocumentSnapshot doc:value){
            if(doc!=null){
                Product product = doc.toObject(Product.class);
                product.setId(doc.getId());
                products.add(product);
                data.setValue(products);
            }
        }
    });
        fetchData();
    }

    private final MutableLiveData<List<Product>> data = new MutableLiveData<>();

    private Task<QuerySnapshot> fetchProducts() {
        return db.collection("products").get();
    }

    private void fetchData() {
        if (products == null) {
            products = new ArrayList<>();
            fetchProducts().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Product product = documentSnapshot.toObject(Product.class);
                        product.setId(documentSnapshot.getId());
                        products.add(product);
                        data.setValue(products);
                    }
                    Log.d("",String.valueOf(data.getValue().size()));
                } else {
                    Log.d("", "Fetching Products Error");
                }
            });
        }
    }

    public LiveData<List<Product>> getProducts() {
        if (data.getValue() == null || data.getValue().isEmpty()) {
            fetchData();
        }
        return data;
    }

    public LiveData<List<Product>> getProductsOfCategory(String categoryId) {
        List<Product> list = new ArrayList<>();

        for (Product product : products) {
            if (product.getCategoryId().equals(categoryId))
                list.add(product);
        }

        MutableLiveData<List<Product>> res = new MutableLiveData<>();
        res.setValue(list);
        return res;
    }

}
