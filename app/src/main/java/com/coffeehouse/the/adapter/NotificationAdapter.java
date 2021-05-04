package com.coffeehouse.the.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import com.coffeehouse.the.R;
import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.databinding.ListNotiItemBinding;
import com.coffeehouse.the.viewmodel.NotificationViewModel;

import java.util.List;

public class NotificationAdapter extends ArrayAdapter<Notification> {
    private List<Notification> notificationList;
    private Context context;
    private NotificationViewModel notificationViewModel;

    public NotificationAdapter(@NonNull Context context, List<Notification> notificationViewModelList) {
        super(context, R.layout.list_noti_item, notificationViewModelList);
        this.context = context;
        this.notificationList = notificationViewModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListNotiItemBinding listNotiItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_noti_item, parent, false);
        listNotiItemBinding.setNotifications(notificationViewModel);
        return listNotiItemBinding.getRoot();
    }

}
