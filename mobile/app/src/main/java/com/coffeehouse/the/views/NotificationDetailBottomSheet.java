package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.BottomsheetNotificationBinding;
import com.coffeehouse.the.models.Notification;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class NotificationDetailBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    private Notification notification;
    private BottomsheetNotificationBinding binding;

    public NotificationDetailBottomSheet() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_notification, container, false);

        if (!notification.getImageUrl().isEmpty())
            Picasso.get().load(notification.getImageUrl()).into((ImageView) binding.notiImage);
        binding.setNotification(notification);
        binding.closeNotiDetail.setOnClickListener(this::onClick);
        binding.readNoti.setOnClickListener(this::onClick);

        return binding.getRoot();
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
