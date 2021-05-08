package com.coffeehouse.the.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.NotificationAdapter;
import com.coffeehouse.the.databinding.ActivityHomeBinding;
import com.coffeehouse.the.models.CustomUser;
import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.services.FetchUser;
import com.coffeehouse.the.viewModels.NotificationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private NotificationViewModel notificationViewModel;
    //private NotificationAdapter notificationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHomeBinding activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        //BINDING
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        RecyclerView recyclerView = activityHomeBinding.notificationsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        NotificationAdapter notificationsAdapter = new NotificationAdapter();
        recyclerView.setAdapter(notificationsAdapter);
        getNotifications(notificationsAdapter);
        //END_BINDING

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.action_home);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:

                    return true;
                case R.id.action_order:

                    startActivity(new Intent(getApplicationContext()
                            , OrderActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.action_store_location:

                    startActivity(new Intent(getApplicationContext()
                            , StoreLocationsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.action_accumulate_point:

                    startActivity(new Intent(getApplicationContext()
                            , MembershipActivity.class));
                    overridePendingTransition(0, 0);
                case R.id.action_others:

                    startActivity(new Intent(getApplicationContext()
                            , OthersActivity.class));
                    overridePendingTransition(0, 0);
            }
            return true;
        });

        CarouselView carouselView = (CarouselView) findViewById(R.id.carouse_view);
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

    private void getNotifications(NotificationAdapter notificationAdapter) {
        notificationViewModel.getNotifications().observe(this, notificationAdapter::setNotificationsList);
    }
}