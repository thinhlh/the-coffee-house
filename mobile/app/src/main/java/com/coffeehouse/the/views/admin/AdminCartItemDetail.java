package com.coffeehouse.the.views.admin;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.AdminCartItemDetailBinding;
import com.coffeehouse.the.models.CartItem;
import com.squareup.picasso.Picasso;

public class AdminCartItemDetail extends AppCompatActivity {

    private AdminCartItemDetailBinding binding;
    private String productTitle = "";


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.admin_cart_item_detail);
        initValues();

        super.onCreate(savedInstanceState);
    }

    private void initValues() {
        CartItem cartItem = new CartItem();
        if (!(getIntent().getSerializableExtra("cartItem") == null)) {
            cartItem = (CartItem) getIntent().getSerializableExtra("cartItem");
        }
        if (!(getIntent().getStringExtra("productTitle") == null)) {
            productTitle = getIntent().getStringExtra("productTitle");
        }

        String productImageUrl = getIntent().getStringExtra("productImageUrl");
        if (productImageUrl != null && !productImageUrl.isEmpty()) {
            Picasso.get().load(productImageUrl).into(binding.image);
        }
        binding.setCartItem(cartItem);
        setUpAppBar();
    }

    private void setUpAppBar() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(productTitle);
        actionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.accent_color)));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
