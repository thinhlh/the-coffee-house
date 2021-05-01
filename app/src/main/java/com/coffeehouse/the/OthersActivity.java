package com.coffeehouse.the;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OthersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.action_others);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:

                        startActivity(new Intent(getApplicationContext()
                                ,HomecontrolActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_order:
                        startActivity(new Intent(getApplicationContext()
                                ,OrderActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_store_location:

                        startActivity(new Intent(getApplicationContext()
                                , Store_LocationActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.action_accumulate_point:
                        startActivity(new Intent(getApplicationContext()
                                , MembershipActivity.class));
                        overridePendingTransition(0,0);

                    case R.id.action_others:

                        return true;
                }
               return true;
            }
        });
    }
}