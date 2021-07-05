package com.coffeehouse.the.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.coffeehouse.the.views.OthersViewFragment.OthersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onStart() {
        super.onStart();
        UserRepo.fetchUser();
    }

    HomeFragment homeFragment = new HomeFragment();
    OrderFragment orderFragment = new OrderFragment();
    StoresFragment storeLocationFragment = new StoresFragment();
    MembershipFragment membershipFragment = new MembershipFragment();
    OthersFragment othersFragment = new OthersFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        loadFragment(homeFragment);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).commit();
            return true;
        }
        return false;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.action_order:
                fragment = orderFragment;
                break;
            case R.id.action_store_location:
                fragment = storeLocationFragment;
                break;
            case R.id.action_accumulate_point:
                fragment = membershipFragment;
                break;
            case R.id.action_others:
                fragment = othersFragment;
                break;
            default:
                fragment = homeFragment;
                break;
        }
        return loadFragment(fragment);
    }
}