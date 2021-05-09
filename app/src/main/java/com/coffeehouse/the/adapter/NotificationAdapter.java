package com.coffeehouse.the.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.NotificationListItemBinding;
import com.coffeehouse.the.models.Notification;

import java.util.List;

public class NotificationAdapter extends Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<Notification> notifications;

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationListItemBinding notificationListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.notification_list_item, parent, false);
        return new NotificationViewHolder(notificationListItemBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification currentNotification=notifications.get(position);
        holder.notificationListItemBinding.setNotification(currentNotification);
    }

    public void setNotificationsList(List<Notification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return notifications != null ? notifications.size() : 0;
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private final NotificationListItemBinding notificationListItemBinding;

        public NotificationViewHolder(@NonNull NotificationListItemBinding notificationListItemBinding) {
            super(notificationListItemBinding.getRoot());
            this.notificationListItemBinding = notificationListItemBinding;
        }
    }
}


