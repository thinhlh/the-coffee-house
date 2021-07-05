package com.coffeehouse.the.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.coffeehouse.the.LocalData.LocalDataManager;
import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.NotificationListItemBinding;
import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.utils.helper.ClickableRecyclerView;
import com.coffeehouse.the.utils.helper.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends Adapter<NotificationAdapter.NotificationViewHolder> implements ClickableRecyclerView<Notification> {
    private List<Notification> notifications = new ArrayList<>();
    private RecyclerViewClickListener<Notification> listener;



    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationListItemBinding notificationListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.notification_list_item, parent, false);
        return new NotificationViewHolder(notificationListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification currentNotification = notifications.get(position);
        holder.notificationListItemBinding.setNotification(currentNotification);
        if (LocalDataManager.getReadNotifications().contains(currentNotification.getId())) {
            holder.notificationListItemBinding.notificationReadStatus.setVisibility(View.GONE);
        }
        if (!currentNotification.getImageUrl().isEmpty())
            Picasso.get().load(currentNotification.getImageUrl()).into(holder.notificationListItemBinding.notificationImageView);
        holder.bindOnClick(currentNotification, listener);
    }

    @Override
    public int getItemCount() {
        return notifications == null ? 0 : notifications.size();
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


    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private final NotificationListItemBinding notificationListItemBinding;

        public NotificationViewHolder(@NonNull NotificationListItemBinding notificationListItemBinding) {
            super(notificationListItemBinding.getRoot());
            this.notificationListItemBinding = notificationListItemBinding;
        }


        public void bindOnClick(Notification notification, RecyclerViewClickListener<Notification> clickListener) {

            notificationListItemBinding.setNotification(notification);
            notificationListItemBinding.executePendingBindings();
            itemView.setOnClickListener(v -> {
                notificationListItemBinding.notificationReadStatus.setVisibility(View.GONE);
                if (clickListener != null) {
                    clickListener.onClick(notification);
                }
            });
        }
    }
}


