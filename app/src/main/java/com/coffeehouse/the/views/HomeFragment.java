package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.NotificationAdapter;
import com.coffeehouse.the.databinding.HomeFragmentBinding;
import com.coffeehouse.the.viewModels.HomeViewModel;
import com.synnapps.carouselview.CarouselView;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel = new HomeViewModel();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //INFLATE
        HomeFragmentBinding homeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        View v = homeFragmentBinding.getRoot();

        //BINDING
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        RecyclerView recyclerView = homeFragmentBinding.notificationsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        NotificationAdapter notificationsAdapter = new NotificationAdapter();
        recyclerView.setAdapter(notificationsAdapter);
        getNotifications(notificationsAdapter);
        //END_BINDING

        CarouselView carouselView = v.findViewById(R.id.carouse_view);
        carouselView.setPageCount(5);
        carouselView.setImageListener((position, imageView) -> {
            switch (position) {
                case 0:
                    imageView.setImageResource(R.drawable.carouselview1);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.carouselview2);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.carouselview3);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.carouselview4);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.carouselview5);
                    break;
            }
        });
        return v;
    }

    private void getNotifications(NotificationAdapter notificationAdapter) {
        homeViewModel.getNotifications().observe(getViewLifecycleOwner(), notificationAdapter::setNotificationsList);
    }
}