package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.OrderDetailItemBinding;
import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.services.repositories.ProductsRepo;
import com.coffeehouse.the.utils.helper.ClickableRecyclerView;
import com.coffeehouse.the.utils.helper.RecyclerViewClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CartItemAdapter extends Adapter<CartItemAdapter.CartItemViewHolder> implements ClickableRecyclerView<Cart> {
    private Cart cart = new Cart();
    private final ProductsRepo productsRepo = new ProductsRepo();
    private RecyclerViewClickListener<Cart> listener;

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
        holder.txtItemName.setText(productsRepo.getProductById(currentCartItem.getProductId()).getTitle());
        holder.bindOnClick(cart, position, listener);
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return cart.getItems() != null ? cart.getItems().size() : 0;
    }

    @Override
    public void setItems(List<Cart> items) {

    }

    @Override
    public List<Cart> getItems() {
        return null;
    }

    @Override
    public void setClickListener(RecyclerViewClickListener<Cart> listener) {
        this.listener = listener;
    }


    static class CartItemViewHolder extends RecyclerView.ViewHolder {
        private final OrderDetailItemBinding orderDetailItemBinding;
        private final TextView txtItemName;

        public CartItemViewHolder(@NonNull @NotNull OrderDetailItemBinding orderDetailItemBinding) {
            super(orderDetailItemBinding.getRoot());
            this.orderDetailItemBinding = orderDetailItemBinding;
            txtItemName = orderDetailItemBinding.textItemname;
        }

        public void bindOnClick(Cart cart, int position, RecyclerViewClickListener<Cart> clickListener) {
            orderDetailItemBinding.setCartItem(cart.getItems().get(position));
            orderDetailItemBinding.executePendingBindings();
            orderDetailItemBinding.bush.setOnClickListener(view -> {
                cart.deleteItem(cart.getItems().get(position));
                if (clickListener != null)
                    clickListener.onClick(cart);
            });
        }
    }
}
