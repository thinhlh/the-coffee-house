package com.coffeehouse.the.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.AuthViewPagerAdapter;
import com.coffeehouse.the.services.UserRepo;
import com.coffeehouse.the.views.admin.AdminHomeActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        FirebaseApp.initializeApp(this);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            UserRepo.isCurrentUserAdmin().addOnCompleteListener(task -> {
                startActivity(new Intent(this, task.getResult() ? AdminHomeActivity.class : HomeActivity.class));
            });
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_skeleton);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        AuthViewPagerAdapter viewPagerAdapter = new AuthViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(viewPagerAdapter);
        ((TabLayout) findViewById(R.id.tab_layout)).setupWithViewPager(viewPager);
    }
}