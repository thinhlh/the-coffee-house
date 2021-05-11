package com.coffeehouse.the.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.NotificationAdapter;
import com.coffeehouse.the.databinding.ActivityHomeBinding;
import com.coffeehouse.the.viewModels.HomeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.synnapps.carouselview.CarouselView;

public class HomeActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        navigationView = findViewById(R.id.bottom_navigation);
        mViewPager = findViewById(R.id.home_viewpager);
        setUpViewPager();


        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.action_order:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.action_store_location:
                    mViewPager.setCurrentItem(2);

                    break;
                case R.id.action_accumulate_point:
                    mViewPager.setCurrentItem(3);

                    break;

                case R.id.action_others:
                    mViewPager.setCurrentItem(4);

                    break;
            }
            return true;
        });


    }

    private void setUpViewPager() {
        ViewPagerAdapterHome viewPagerAdapterHome = new ViewPagerAdapterHome(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapterHome);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        navigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.action_order).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.action_store_location).setChecked(true);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.action_accumulate_point).setChecked(true);
                        break;
                    case 4:
                        navigationView.getMenu().findItem(R.id.action_others).setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


}