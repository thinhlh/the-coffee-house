package com.coffeehouse.the.views.admin;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.AdminCartItemAdapter;
import com.coffeehouse.the.databinding.AdminOrderDetailBinding;
import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.viewModels.admin.AdminOrderDetailViewModel;

import java.util.Objects;

public class AdminOrderDetail extends AppCompatActivity {
    private AdminOrderDetailBinding binding;
    private AdminOrderDetailViewModel viewModel;
    private final AdminCartItemAdapter adapter = new AdminCartItemAdapter();


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.admin_order_detail);
        viewModel = new ViewModelProvider(this).get(AdminOrderDetailViewModel.class);

        initValues();
        setUpComponents();
    }

    private void setUpComponents() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.content.setVisibility(View.GONE);
        binding.progressCircular.setVisibility(View.VISIBLE);
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
            order.setCart((Cart) getIntent().getSerializableExtra("cart"));
        }
        binding.setOrder(order);

        binding.adminToolbar.setTitleTextColor(Color.WHITE);
        if (order.getDelivered()) {
            binding.adminToolbar.setBackground(new ColorDrawable(Color.GREEN));
            binding.adminToolbar.setTitleTextColor(Color.BLACK);
            binding.adminToolbar.setNavigationIconTint(Color.BLACK);
        } else {
            binding.adminToolbar.setBackground(new ColorDrawable(Color.RED));
        }
    }

    private void setUpRecyclerView() {
        RecyclerView cartRecyclerView = binding.recyclerView;

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setAdapter(adapter);

        adapter.setItems(binding.getOrder().getCart().getItems());
        viewModel.getProductsOfCartItems(adapter.getItems()).addOnCompleteListener(task -> {
            adapter.setProducts(task.getResult());

            binding.progressCircular.setVisibility(View.GONE);
            binding.content.setVisibility(View.VISIBLE);
            adapter.setClickListener(item -> {
            });
        });
    }
}
