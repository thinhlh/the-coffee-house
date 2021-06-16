package com.coffeehouse.the.views.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.AdminNotificationAdapter;
import com.coffeehouse.the.databinding.AdminNotificationsFragmentBinding;
import com.coffeehouse.the.utils.SwipeToDeleteCallback;
import com.coffeehouse.the.viewModels.admin.AdminNotificationsViewModel;

import org.jetbrains.annotations.NotNull;

public class AdminNotificationsFragment extends Fragment {

    private AdminNotificationsViewModel viewModel;
    private final AdminNotificationAdapter adapter = new AdminNotificationAdapter();
    private AdminNotificationsFragmentBinding binding;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.admin_notifications_fragment, container, false);
        viewModel = new ViewModelProvider(this).get(AdminNotificationsViewModel.class);

        setUpRecyclerView();
        setUpListeners();
        return binding.getRoot();
    }

    private void setUpListeners() {
        binding.addButton.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AdminEditNotification.class));
        });
    }

    private void setUpRecyclerView() {
        RecyclerView notificationsRecyclerView = binding.notificationsRecyclerView;

        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsRecyclerView.setHasFixedSize(true);
        notificationsRecyclerView.setAdapter(adapter);

        viewModel.getNotifications().observe(getViewLifecycleOwner(), adapter::setItems);

        enableSwipeToDelete(notificationsRecyclerView);

        adapter.setClickListener(notification -> {
            Intent intent = new Intent(getContext(), AdminEditNotification.class);
            intent.putExtra("notification", notification.toGson());
            startActivity(intent);
        });
    }

    private void enableSwipeToDelete(RecyclerView notificationsRecyclerView) {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                new AlertDialog.Builder(getContext()).setTitle("Delete product").setMessage("Are you sure want to delete this notification?").setPositiveButton("Yes", (dialog, which) -> {
                    viewModel.removeANotification(position);
                }).setNegativeButton("No", (dialog, which) -> {
                    adapter.notifyDataSetChanged();
                }).show();
            }
        };
        new ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(notificationsRecyclerView);
    }
}