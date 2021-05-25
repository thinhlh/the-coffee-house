package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.NotificationListItemBinding;
import com.coffeehouse.the.models.Notification;
import com.squareup.picasso.Picasso;

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
        Picasso.get().load(currentNotification.getImageUrl()).into((ImageView) holder.itemView.findViewById(R.id.notification_image_view));
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


