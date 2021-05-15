package com.coffeehouse.the.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.adapter.ProductsClickListener;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.services.ProductsRepo;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class OrderViewModel extends ViewModel {
    private final ProductsRepo productsRepo;

    private ProductsClickListener listener;

    public OrderViewModel(){ productsRepo = new ProductsRepo(); }

    public LiveData<List<Product>> getProducts(){
        return productsRepo.getProducts();
    }


    public void setListener(ProductsClickListener listener) {
        this.listener = listener;
    }

    public void productOnClick(Product product){
        listener.onItemClick(product);
    }
}