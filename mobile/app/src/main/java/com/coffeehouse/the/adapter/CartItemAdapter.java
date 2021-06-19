package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.OrderDetailItemBinding;
import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.services.ProductsRepo;

import org.jetbrains.annotations.NotNull;

public class CartItemAdapter extends Adapter<CartItemAdapter.CartItemViewHolder> {
    private Cart cart;
    private ProductsRepo productsRepo = new ProductsRepo();

    @NonNull
    @NotNull
    @Override
    public CartItemAdapter.CartItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        OrderDetailItemBinding orderDetailItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_detail_item, parent, false);
        return new CartItemViewHolder(orderDetailItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartItemAdapter.CartItemViewHolder holder, int position) {
        CartItem currentCartItem = cart.getItems().get(position);
        holder.orderDetailItemBinding.setCartItem(currentCartItem);
        holder.txtItemName.setText(productsRepo.getProductsById(currentCartItem.getProductId()).getTitle());
        holder.btnDeleteItem.setOnClickListener(listener -> {
            cart.deleteItem(currentCartItem);
            notifyDataSetChanged();
        });
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return cart.getItems() != null ? cart.getItems().size() : 0;
    }

    static class CartItemViewHolder extends RecyclerView.ViewHolder {
        private final OrderDetailItemBinding orderDetailItemBinding;
        private TextView txtItemName;
        private ImageButton btnDeleteItem;

        public CartItemViewHolder(@NonNull @NotNull OrderDetailItemBinding orderDetailItemBinding) {
            super(orderDetailItemBinding.getRoot());
            this.orderDetailItemBinding = orderDetailItemBinding;
            txtItemName = orderDetailItemBinding.textItemname;
            btnDeleteItem = orderDetailItemBinding.bush;
        }
    }
}
