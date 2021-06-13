package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
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

public class ProductAdapter extends Adapter<ProductAdapter.ProductViewHolder> implements SwipeAbleRecyclerView<Product> {
    private List<Product> products;
    private RecyclerViewClickListener<Product> listener;

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
        holder.bindOnClick(currentProduct, listener);
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    @Override
    public void setItems(List<Product> items) {
        this.products = items;
        notifyDataSetChanged();
    }

    @Override
    public List<Product> getItems() {
        return products;
    }

    @Override
    public void setClickListener(RecyclerViewClickListener<Product> listener) {
        this.listener = listener;
    }

    @Override
    public void remove(int position) {
        this.products.remove(position);
        this.notifyItemRemoved(position);
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ProductListItemBinding productListItemBinding;

        public ProductViewHolder(@NonNull ProductListItemBinding productListItemBinding) {
            super(productListItemBinding.getRoot());
            this.productListItemBinding = productListItemBinding;
        }

        public void bindOnClick(Product product, RecyclerViewClickListener<Product> clickListener) {
            productListItemBinding.setProduct(product);
            productListItemBinding.executePendingBindings();
            itemView.setOnClickListener(view -> {
                if (clickListener != null)
                    clickListener.onClick(product);
            });
        }
    }
}
