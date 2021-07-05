package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.CartItemCardBinding;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.utils.helper.ClickableRecyclerView;
import com.coffeehouse.the.utils.helper.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminCartItemAdapter extends RecyclerView.Adapter<AdminCartItemAdapter.AdminCartItemViewHolder> implements ClickableRecyclerView<CartItem> {

    private List<CartItem> cartItems = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private RecyclerViewClickListener<CartItem> listener;

    @NonNull
    @NotNull
    @Override
    public AdminCartItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CartItemCardBinding cartItemCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cart_item_card, parent, false);
        return new AdminCartItemViewHolder(cartItemCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdminCartItemAdapter.AdminCartItemViewHolder holder, int position) {
        CartItem currentCartItem = cartItems.get(position);
        Product currentProduct = new Product();

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(currentCartItem.getProductId())) {
                currentProduct = products.get(i);
                break;
            }
        }
        holder.cartItemCardBinding.setCartItem(currentCartItem);
        holder.cartItemCardBinding.setProduct(currentProduct);

        //TODO SHOW PRODUCT IMAGE HERE
        if (!currentProduct.getImageUrl().isEmpty())
            Picasso.get().load(currentProduct.getImageUrl()).into(holder.cartItemCardBinding.productImage);

        holder.bindOnClick(currentCartItem, currentProduct, listener);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cartItems != null ? cartItems.size() : 0;
    }

    @Override
    public void setItems(List<CartItem> items) {
        this.cartItems = items;
        notifyDataSetChanged();
    }

    @Override
    public List<CartItem> getItems() {
        return cartItems;
    }

    @Override
    public void setClickListener(RecyclerViewClickListener<CartItem> listener) {
        this.listener = listener;
    }

    static class AdminCartItemViewHolder extends RecyclerView.ViewHolder {
        private final CartItemCardBinding cartItemCardBinding;

        public AdminCartItemViewHolder(@NonNull CartItemCardBinding cartItemCardBinding) {
            super(cartItemCardBinding.getRoot());
            this.cartItemCardBinding = cartItemCardBinding;
        }

        public void bindOnClick(CartItem cartItem, Product product, RecyclerViewClickListener<CartItem> clickListener) {
            cartItemCardBinding.setCartItem(cartItem);
            cartItemCardBinding.setProduct(product);

            cartItemCardBinding.executePendingBindings();
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onClick(cartItem);
                }
            });
        }
    }
}
