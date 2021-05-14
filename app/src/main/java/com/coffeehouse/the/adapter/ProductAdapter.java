package com.coffeehouse.the.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.models.Product;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductAdapter extends Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;

    @NonNull
    @NotNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductAdapter.ProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() { return products != null ? products.size() : 0; }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
