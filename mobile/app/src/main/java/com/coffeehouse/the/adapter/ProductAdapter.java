package com.coffeehouse.the.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.ProductListItemBinding;
import com.coffeehouse.the.models.Product;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductAdapter extends Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products;
//    private ProductsClickListener listener;

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ProductListItemBinding productListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_list_item, parent, false);
        return new ProductViewHolder(productListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductAdapter.ProductViewHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.productListItemBinding.setProduct(currentProduct);
        Picasso.get().load(currentProduct.getImageUrl()).into((ImageView) holder.itemView.findViewById(R.id.product_image));
    }

    public void setProductsList(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder{

        private final ProductListItemBinding productListItemBinding;

        public ProductViewHolder(@NonNull ProductListItemBinding productListItemBinding) {
            super(productListItemBinding.getRoot());
            this.productListItemBinding = productListItemBinding;
        }

    }

}
