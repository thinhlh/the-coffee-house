package com.coffeehouse.the.views.admin;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.AdminOrderDetailBinding;
import com.coffeehouse.the.models.AdminCustomUser;
import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.viewModels.admin.AdminOrderDetailViewModel;

import java.util.Objects;

public class AdminOrderDetail extends AppCompatActivity {
    private AdminOrderDetailBinding binding;
    private AdminOrderDetailViewModel viewModel;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.admin_order_detail);
        viewModel = new ViewModelProvider(this).get(AdminOrderDetailViewModel.class);

        setUpComponents();
        initValues();
    }

    private void setUpComponents() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        setUpListeners();
        setUpRecyclerView();
    }

    private void setUpListeners() {
        binding.adminToolbar.setNavigationOnClickListener(v -> this.finish());
    }

    private void initValues() {

        Order order = new Order();
        if (!(getIntent().getStringExtra("order") == null)) {
            order = Order.fromGson(getIntent().getStringExtra("order"));
        }
        binding.setOrder(order);

        binding.adminToolbar.setTitleTextColor(Color.WHITE);
        if (order.getDelivered()) {
            binding.adminToolbar.setBackground(new ColorDrawable(Color.GREEN));
        } else {
            binding.adminToolbar.setBackground(new ColorDrawable(Color.RED));
        }
    }

    private void setUpRecyclerView() {
    }
}
