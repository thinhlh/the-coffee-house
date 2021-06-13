package com.coffeehouse.the.views.admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AdminHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActionBar toolBar;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        frameLayout = findViewById(R.id.admin_frame_layout);
        loadFragment(new AdminProductsFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        AdminProductsFragment adminProductFragment = new AdminProductsFragment();
        AdminNotificationsFragment adminNotificationsFragment=new AdminNotificationsFragment();
        switch (item.getItemId()) {
            case R.id.products_admin_nav:
                loadFragment(adminProductFragment);
                return true;
            case R.id.categories_admin_nav:
                loadFragment(adminProductFragment);
                return true;
            case R.id.notification_admin_nav:
                loadFragment(adminNotificationsFragment);
                return true;
            case R.id.stores_admin_nav:
                loadFragment(adminProductFragment);
                return true;
            case R.id.others_admin_nav:
                loadFragment(adminProductFragment);
                return true;
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_frame_layout, fragment).commit();
        }

    }
}