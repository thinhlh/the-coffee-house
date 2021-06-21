package com.coffeehouse.the.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.NotificationAdapter;
import com.coffeehouse.the.databinding.HomeFragmentBinding;
import com.coffeehouse.the.viewModels.HomeViewModel;
import com.coffeehouse.the.views.admin.AdminEditNotification;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.synnapps.carouselview.CarouselView;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private HomeViewModel homeViewModel = new HomeViewModel();
    private HomeFragmentBinding binding;
    private final NotificationAdapter adapter = new NotificationAdapter();

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initview(view);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //INFLATE
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setUpRecyclerView();
        setUpCarouselViewer();
        return binding.getRoot();
    }

    private void setUpRecyclerView(){
        RecyclerView recyclerView = binding.notificationsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        homeViewModel.getNotifications().observe(getViewLifecycleOwner(), adapter::setItems);
        adapter.setClickListener(notification -> {
            //TODO: BOTTOM NAVIGATION NOTIFICATION HERE
        });
    }

    private void setUpCarouselViewer(){
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

    private void Initview (View view){
        CardView cardViewdelivery=view.findViewById(R.id.cardview_delivery);
        cardViewdelivery.setOnClickListener(this);
        CardView cardViewpickup=view.findViewById(R.id.pickUpCard);
        cardViewpickup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardview_delivery:
                Changefragment();
                break;
            case R.id.pickUpCard:
                Changefragmentstorelocation();
                break;
        }


    }

    private void Changefragmentstorelocation() {
        FragmentManager fragmentManager =requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_fragment_container,new StoresFragment()).addToBackStack(null).commit();
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_store_location);
    }

    private void Changefragment() {
        FragmentManager fragmentManager=requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_fragment_container,new OrderFragment()).addToBackStack(null).commit();
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_order);
    }
}
