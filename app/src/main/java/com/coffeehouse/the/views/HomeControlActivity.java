package com.coffeehouse.the.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;


import com.coffeehouse.the.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HomeControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_control);
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
    }
}