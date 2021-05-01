package com.coffeehouse.the;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.coffeehouse.the.views.MainActivity;

public class IntroductoryActivity extends AppCompatActivity {
    ImageView appname;
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        appname=findViewById(R.id.greeting_introductory);
        lottieAnimationView=findViewById(R.id.lottie);
        appname.animate().translationX(1600).setDuration(1100).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1600).setDuration(1100).setStartDelay(4000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroductoryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },5500);
    }
}