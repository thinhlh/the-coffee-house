package com.coffeehouse.the.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.coffeehouse.the.R;

public class GreetingActivity extends AppCompatActivity {
    ImageView appName;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        appName = findViewById(R.id.greeting_introductory);
        lottieAnimationView = findViewById(R.id.lottie);
        appName.animate().translationX(1600).setDuration(1100).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1600).setDuration(1100).setStartDelay(4000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GreetingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5500);
    }
}