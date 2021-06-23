package com.coffeehouse.the.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.AdminOrderCardBinding;
import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.viewModels.admin.AdminOrderViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.AdminOrderViewHolder> implements ClickableRecyclerView<Order> {

    private List<Order> orders = new ArrayList<>();
    private List<Order> ordersCopy = new ArrayList<>();
    private RecyclerViewClickListener<Order> listener;

    @NonNull
    @NotNull
    @Override
    public AdminOrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        AdminOrderCardBinding adminOrderCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.admin_order_card, parent, false);
        return new AdminOrderViewHolder(adminOrderCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdminOrderAdapter.AdminOrderViewHolder holder, int position) {
        Order currentOrder = orders.get(position);
        holder.adminOrderCardBinding.setOrder(currentOrder);
        holder.bindOnClick(currentOrder, listener);
    }

    @Override
    public int getItemCount() {
        return orders != null ? orders.size() : 0;
    }

    @Override
    public void setItems(List<Order> items) {
        this.orders = items;
        this.ordersCopy.addAll(items);
        notifyDataSetChanged();

    }

    @Override
    public List<Order> getItems() {
        return this.orders;
    }

    @Override
    public void setClickListener(RecyclerViewClickListener<Order> listener) {
        this.listener = listener;
    }

    static class AdminOrderViewHolder extends RecyclerView.ViewHolder {
        private final AdminOrderCardBinding adminOrderCardBinding;

        public AdminOrderViewHolder(@NonNull AdminOrderCardBinding adminOrderCardBinding) {
            super(adminOrderCardBinding.getRoot());
            this.adminOrderCardBinding = adminOrderCardBinding;
        }

        public void bindOnClick(Order order, RecyclerViewClickListener<Order> clickListener) {
            adminOrderCardBinding.setOrder(order);
            adminOrderCardBinding.executePendingBindings();
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onClick(order);
                }
            });
        }
    }

}

