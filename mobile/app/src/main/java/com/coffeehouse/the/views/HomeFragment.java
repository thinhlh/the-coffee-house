package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.LocalData.LocalDataManager;
import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.NotificationAdapter;
import com.coffeehouse.the.databinding.HomeFragmentBinding;
import com.coffeehouse.the.databinding.NotificationListItemBinding;
import com.coffeehouse.the.viewModels.HomeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.synnapps.carouselview.CarouselView;

import java.util.HashSet;
import java.util.Set;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private HomeViewModel homeViewModel = new HomeViewModel();
    private HomeFragmentBinding binding;
    private final NotificationAdapter adapter = new NotificationAdapter();
    NotificationListItemBinding notificationListItemBinding;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //INFLATE

        if (!LocalDataManager.getCurrentUserId().equals(FirebaseAuth.getInstance().getUid())) {
            LocalDataManager.setCurrentUserId(FirebaseAuth.getInstance().getUid());
            Set<String> set = new HashSet<>();
            LocalDataManager.setReadNotifications(set);
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding.cardviewDelivery.setOnClickListener(this::onClick);
        binding.pickUpCard.setOnClickListener(this::onClick);

        setUpRecyclerView();
        setUpCarouselViewer();

        return binding.getRoot();
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = binding.notificationsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        final int[] notiCount = {0};
        homeViewModel.getNotifications().observe(this, items -> {
            adapter.setItems(items);
            notiCount[0] = items.size();
            setCountText(items.size());
        });

        adapter.setClickListener(item -> {
            NotificationDetailBottomSheet bottomSheet = new NotificationDetailBottomSheet();
            bottomSheet.setNotification(item);
            bottomSheet.show(getFragmentManager(), "Notification Detail");

            if (!LocalDataManager.getReadNotifications().contains(item.getId())) {
                LocalDataManager.setCountNotifications(LocalDataManager.getCountNotifications() + 1);
                Set<String> readNotificationIds = LocalDataManager.getReadNotifications();
                readNotificationIds.add(item.getId());
                LocalDataManager.setReadNotifications(readNotificationIds);
                setCountText(notiCount[0]);
            }
        });
    }

    private void setCountText(int i) {
        String txtCount = Integer.toString(i - LocalDataManager.getCountNotifications());
        binding.totalUnseenNotifications.setText(txtCount);
    }

    private void setUpCarouselViewer() {
        CarouselView carouselView = binding.carouseView;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardview_delivery:
                navigateToOrdersFragment();
                break;
            case R.id.pickUpCard:
                navigateToStoresFragment();
                break;
        }
    }

    private void navigateToStoresFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_fragment_container, new StoresFragment()).addToBackStack(null).commit();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_store_location);
    }

    private void navigateToOrdersFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_fragment_container, new OrderFragment()).addToBackStack(null).commit();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_order);
    }
}
