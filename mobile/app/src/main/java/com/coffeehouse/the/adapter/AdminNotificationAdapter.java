package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.AdminNotificationCardBinding;
import com.coffeehouse.the.models.Notification;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminNotificationAdapter extends RecyclerView.Adapter<AdminNotificationAdapter.AdminNotificationViewHolder> implements SwipeAbleRecyclerView<Notification> {

    private List<Notification> notifications;
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
        notifyItemRemoved(position);
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
