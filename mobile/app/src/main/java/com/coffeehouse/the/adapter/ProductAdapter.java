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
import com.coffeehouse.the.utils.helper.RecyclerViewClickListener;
import com.coffeehouse.the.utils.helper.Searchable;
import com.coffeehouse.the.utils.helper.SwipeAbleRecyclerView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ProductAdapter extends Adapter<ProductAdapter.ProductViewHolder> implements SwipeAbleRecyclerView<Product>, Searchable {
    private List<Product> products = new ArrayList<>();
    private List<Product> productsCopy = new ArrayList<>();
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
        if (!currentProduct.getImageUrl().isEmpty())
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
        this.productsCopy.clear();
        this.productsCopy.addAll(items);
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

    @Override
    public void filter(String query) {
        products.clear();
        if (query.isEmpty()) {
            products.addAll(productsCopy);
        } else {
            query = query.toLowerCase();
            String regex = ".*" + query + ".*";

            for (Product product : productsCopy) {
                if (Pattern.matches(regex, product.getTitle().toLowerCase())) {
                    products.add(product);
                }
            }
        }
        notifyDataSetChanged();
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
