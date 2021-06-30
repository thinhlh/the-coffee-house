package com.coffeehouse.the.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.OrderHistoryDetailListItemBinding;
import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.services.repositories.ProductsRepo;

import org.jetbrains.annotations.NotNull;

public class OrderHistoryDetailAdapter extends Adapter<OrderHistoryDetailAdapter.OrderDetailHistoryViewHolder> {
    private Cart cart;
    private final ProductsRepo productsRepo = new ProductsRepo();

    @NonNull
    @NotNull
    @Override
    public OrderHistoryDetailAdapter.OrderDetailHistoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        OrderHistoryDetailListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_history_detail_list_item, parent, false);
        return new OrderDetailHistoryViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderHistoryDetailAdapter.OrderDetailHistoryViewHolder holder, int position) {
        CartItem currentCartItem = cart.getItems().get(position);
        holder.binding.setCartItem(currentCartItem);
        holder.title.setText(productsRepo.getProductById(currentCartItem.getProductId()).getTitle());
        holder.quantity.setText(Integer.toString(currentCartItem.getQuantity()));
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cart.getItems() != null ? cart.getItems().size() : 0;
    }

    static class OrderDetailHistoryViewHolder extends RecyclerView.ViewHolder {
        private final OrderHistoryDetailListItemBinding binding;
        TextView quantity, title;

        public OrderDetailHistoryViewHolder(@NonNull @NotNull OrderHistoryDetailListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            quantity = binding.textItemamount;
            title = binding.textItemname;
        }
    }
}
