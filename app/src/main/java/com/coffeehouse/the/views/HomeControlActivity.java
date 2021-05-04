package com.coffeehouse.the.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;


import com.coffeehouse.the.R;
//import com.coffeehouse.the.databinding.ActivityHomeControlBinding;
import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.viewmodel.NotificationViewModel;
import com.coffeehouse.the.adapter.NotificationAdapter;
import com.coffeehouse.the.databinding.ActivityHomeControlBinding;
import com.coffeehouse.the.databinding.ListNotiItemBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

public class HomeControlActivity extends AppCompatActivity {
    private ListView lv_noti;
    private ListNotiItemBinding listNotiItemBinding;
    private ActivityHomeControlBinding activityHomeControlBinding;
    private NotificationViewModel notificationViewModel;
    private List<Notification> notificationViewModelList;
    private NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_home_control);
        ActivityHomeControlBinding activityHomeControlBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_control);
        notificationViewModel = new NotificationViewModel();
        //notificationViewModelList = notificationViewModel.getNotificationList();
        notificationViewModel.getNotificationList().observe(this, new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notificationViewModels) {
                notificationViewModel.fetchNotification(notificationViewModels);
                notificationAdapter = new NotificationAdapter(getBaseContext(), notificationViewModels);
                activityHomeControlBinding.listNotification.setAdapter(notificationAdapter);
            };
        });
//        notificationAdapter = new NotificationAdapter(this, notificationViewModelList);
//        activityHomeControlBinding.listNotification.setAdapter(notificationAdapter);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.action_home);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });

        CarouselView carouselView = (CarouselView) findViewById(R.id.carouse_view);
        carouselView.setPageCount(5);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
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
            }
        });


//        ArrayList<notification> arrayList = new ArrayList<>();
//        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.list_noti_item, arrayList);
//        lv_noti = findViewById(R.id.list_notification);
//        int images1 = R.drawable.notification_1;
//
//        new FetchUser().fetchUser().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    //String uname;
//                    //task.getResult().getString("name")
//                    //tx_noti_title.setText(task.getResult().getString("name"));
//                    String username = "";
//                    username = task.getResult().getString("name").toString();
//                    notification Notification1 = new notification("Chào bạn " + username, "28/04/2021 21:27", images1);
//                    arrayList.add(Notification1);
//                    lv_noti.setAdapter(customAdapter);
//
//                } else {
//                    notification Notification1 = new notification("Chào bạn mới", "28/04/2021 21:27", images1);
//                    arrayList.add(Notification1);
//                    lv_noti.setAdapter(customAdapter);
//                }
//            }
//        });

//        notification Notification1 = new notification("Chào bạn, Thiện Nhân", "28/04/2021 21:27", images1);
//        arrayList.add(Notification1);
//        lv_noti.setAdapter(customAdapter);


    }
}