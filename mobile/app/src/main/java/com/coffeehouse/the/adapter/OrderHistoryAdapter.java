package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.OrderHistoryListItemBinding;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.services.repositories.ProductsRepo;
import com.coffeehouse.the.utils.helper.ClickableRecyclerView;
import com.coffeehouse.the.utils.helper.RecyclerViewClickListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderHistoryAdapter extends Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> implements ClickableRecyclerView<Order> {
    private List<Order> orders = new ArrayList<>();
    private ProductsRepo productsRepo = new ProductsRepo();
    private RecyclerViewClickListener<Order> listener;

    @NonNull
    @NotNull
    @Override
    public OrderHistoryAdapter.OrderHistoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        OrderHistoryListItemBinding historyListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_history_list_item, parent, false);
        return new OrderHistoryViewHolder(historyListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderHistoryAdapter.OrderHistoryViewHolder holder, int position) {
        Order currentOrder = orders.get(position);
        holder.historyListItemBinding.setOrder(currentOrder);
        holder.historyListItemBinding.textItemName.setText(itemName(currentOrder));
        holder.historyListItemBinding.textItemPrice.setText(itemPrice(currentOrder));
        holder.historyListItemBinding.textItemTime.setText(itemTime(currentOrder));
        holder.bindOnClick(currentOrder, listener);
    }


    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    @Override
    public void setItems(List<Order> items) {
        this.orders = items;
        notifyDataSetChanged();
    }

    @Override
    public List<Order> getItems() {
        return orders;
    }

    @Override
    public void setClickListener(RecyclerViewClickListener<Order> listener) {
        this.listener = listener;
    }

    static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        private final OrderHistoryListItemBinding historyListItemBinding;

        public OrderHistoryViewHolder(@NonNull @NotNull OrderHistoryListItemBinding historyListItemBinding) {
            super(historyListItemBinding.getRoot());
            this.historyListItemBinding = historyListItemBinding;
        }

        public void bindOnClick(Order order, RecyclerViewClickListener<Order> clickListener) {
            historyListItemBinding.setOrder(order);
            historyListItemBinding.executePendingBindings();
            itemView.setOnClickListener(view -> {
                if (clickListener != null)
                    clickListener.onClick(order);
            });
        }
    }

    private String itemName(Order order) {
        String itemName = "";
        if (order.getCart().getItems().size() == 1) {
            return productsRepo.getProductById(order.getCart().getItems().get(0).getProductId()).getTitle();
        }
        for (CartItem cartItem : order.getCart().getItems()) {
            itemName = itemName + productsRepo.getProductById(cartItem.getProductId()).getTitle() + ", ";
        }
        return itemName;
    }

    private String itemPrice(Order order) {
        Locale locale = new Locale("vi", "VN");
        Format format = NumberFormat.getCurrencyInstance(locale);
        if (order.getDelivered())
            return format.format(order.getOrderValue() + 30000);
        return format.format(order.getOrderValue());
    }

    private String itemTime(Order order) {
        DateFormat df = new SimpleDateFormat("h:MM - dd/MMM/yyyy");
        return df.format(order.getOrderTime());
    }
}
