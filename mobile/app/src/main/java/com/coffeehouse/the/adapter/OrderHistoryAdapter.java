package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.HistoryListItemBinding;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.services.ProductsRepo;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderHistoryAdapter extends Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    private List<Order> orders;
    private ProductsRepo productsRepo = new ProductsRepo();

    @NonNull
    @NotNull
    @Override
    public OrderHistoryAdapter.OrderHistoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        HistoryListItemBinding historyListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.history_list_item, parent, false);
        return new OrderHistoryViewHolder(historyListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderHistoryAdapter.OrderHistoryViewHolder holder, int position) {
        Order currentOrder = orders.get(position);
        holder.historyListItemBinding.setOrder(currentOrder);
        holder.historyListItemBinding.textItemName.setText(itemName(currentOrder));
        holder.historyListItemBinding.textItemPrice.setText(itemPrice(currentOrder));
        holder.historyListItemBinding.textItemTime.setText(itemTime(currentOrder));
    }


    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

//    @Override
//    public void setItems(List<Order> items) {
//        this.orders = items;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public List<Order> getItems() {
//        return orders;
//    }
//
//    @Override
//    public void setClickListener(RecyclerViewClickListener<Order> listener) {
//
//    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        private final HistoryListItemBinding historyListItemBinding;

        public OrderHistoryViewHolder(@NonNull @NotNull HistoryListItemBinding historyListItemBinding) {
            super(historyListItemBinding.getRoot());
            this.historyListItemBinding = historyListItemBinding;
        }
    }

    private String itemName(Order order) {
        String itemName = "";
        for (CartItem cartItem : order.getCart().getItems()) {
            itemName = productsRepo.getProductsById(cartItem.getProductId()).getTitle() + ", ";
        }
        return itemName;
    }

    private String itemPrice(Order order) {
        Locale locale = new Locale("vi", "VN");
        Format format = NumberFormat.getCurrencyInstance(locale);
        return format.format(order.getTotal());
    }

    private String itemTime(Order order) {
        DateFormat df = new SimpleDateFormat("h:MM - dd/MMM/yyyy");
        return df.format(order.getOrderTime());
    }
}
