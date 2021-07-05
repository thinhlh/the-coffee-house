package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.AdminNotificationCardBinding;
import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.utils.helper.RecyclerViewClickListener;
import com.coffeehouse.the.utils.helper.Searchable;
import com.coffeehouse.the.utils.helper.SwipeAbleRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AdminNotificationAdapter extends RecyclerView.Adapter<AdminNotificationAdapter.AdminNotificationViewHolder> implements SwipeAbleRecyclerView<Notification>, Searchable {

    private List<Notification> notifications = new ArrayList<>();
    private final List<Notification> notificationsCopy = new ArrayList<>();
    private RecyclerViewClickListener<Notification> listener;

    @NonNull
    @Override
    public AdminNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminNotificationCardBinding adminNotificationCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.admin_notification_card, parent, false);
        return new AdminNotificationViewHolder(adminNotificationCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminNotificationViewHolder holder, int position) {
        Notification currentNotification = notifications.get(position);
        holder.adminNotificationCardBinding.setNotification(currentNotification);
        if (!currentNotification.getImageUrl().isEmpty())
            Picasso.get().load(currentNotification.getImageUrl()).into(holder.adminNotificationCardBinding.notificationImageView);
        holder.bindOnClick(currentNotification, listener);
    }

    @Override
    public int getItemCount() {
        return notifications != null ? notifications.size() : 0;
    }

    @Override
    public void setItems(List<Notification> items) {
        this.notifications = items;
        this.notificationsCopy.clear();
        notificationsCopy.addAll(notifications);
        notifyDataSetChanged();
    }

    @Override
    public List<Notification> getItems() {
        return this.notifications;
    }

    @Override
    public void setClickListener(RecyclerViewClickListener<Notification> listener) {
        this.listener = listener;
    }

    @Override
    public void remove(int position) {
//        notifyItemRemoved(position);
    }

    @Override
    public void filter(String query) {
        notifications.clear();
        if (query.isEmpty()) {
            notifications.addAll(notificationsCopy);
        } else {
            query = query.toLowerCase();
            String regex = ".*" + query + ".*";

            for (Notification notification : notificationsCopy) {
                if (Pattern.matches(regex, notification.getTitle().toLowerCase()) || Pattern.matches(regex, notification.getDescription().toLowerCase()) && !notifications.contains(notification)) {
                    notifications.add(notification);
                }
            }
        }
        notifyDataSetChanged();
    }


    static class AdminNotificationViewHolder extends RecyclerView.ViewHolder {
        private final AdminNotificationCardBinding adminNotificationCardBinding;

        public AdminNotificationViewHolder(@NonNull AdminNotificationCardBinding adminNotificationCardBinding) {
            super(adminNotificationCardBinding.getRoot());
            this.adminNotificationCardBinding = adminNotificationCardBinding;
        }

        public void bindOnClick(Notification notification, RecyclerViewClickListener<Notification> clickListener) {
            adminNotificationCardBinding.setNotification(notification);
            adminNotificationCardBinding.executePendingBindings();
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onClick(notification);
                }
            });
        }
    }
}
