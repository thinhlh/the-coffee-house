package com.coffeehouse.the.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.coffeehouse.the.LocalData.LocalDataManager;
import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.AuthViewPagerAdapter;
import com.coffeehouse.the.services.local.FCMService;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.coffeehouse.the.views.admin.AdminHomeActivity;
import com.facebook.*;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onStart() {
        FirebaseApp.initializeApp(this);
        LocalDataManager.init(getApplicationContext());
        LocalDataManager.setCountNotifications(0);
        initDependencies();
        super.onStart();
    }

    private void initDependencies() {
        initFirebaseServices();
    }

    private void initFirebaseServices() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Context context = this;
            //startActivity(new Intent(context, FlashScreen.class));
            setContentView(R.layout.flash_screen);
            UserRepo.isCurrentUserAdmin().addOnCompleteListener(task -> {
                Task<Void> task1;
                if (UserRepo.user.getSubscribeToNotifications()) {
                    task1 = FirebaseMessaging.getInstance().subscribeToTopic(FCMService.TOPIC);
                    Log.d("Subscribed", "TRUE");
                } else {
                    task1 = FirebaseMessaging.getInstance().unsubscribeFromTopic(FCMService.TOPIC);
                }

                task1.addOnCompleteListener(task2 -> {
                    Toast.makeText(this, "Welcome " + UserRepo.user.getName(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, task.getResult() ? AdminHomeActivity.class : HomeActivity.class));
                });
            });
        }
    }


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle
                                    savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_skeleton);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        AuthViewPagerAdapter viewPagerAdapter = new AuthViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(viewPagerAdapter);
        ((TabLayout) findViewById(R.id.tab_layout)).setupWithViewPager(viewPager);


    }

    @Override
    public void onClick(View v) {

    }
}