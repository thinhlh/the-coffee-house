package com.coffeehouse.the.views.admin;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.utils.helper.WaitingHandler;
import com.coffeehouse.the.viewModels.admin.AdminOrderDetailViewModel;

import java.util.List;
import java.util.Objects;

public class AdminOrderDetail extends AppCompatActivity implements WaitingHandler {
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
        binding.address.getPaint().setUnderlineText(true);
        invokeWaiting();
        setUpListeners();
        setUpRecyclerView();
    }

    private void setUpListeners() {
        binding.adminToolbar.setNavigationOnClickListener(v -> this.finish());
        binding.button.setOnClickListener(v -> {
            invokeWaiting();
            viewModel.delivery(binding.getOrder().getId()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    this.finish();
                } else {
                    dispatchWaiting();
                    Toast.makeText(this, "Error occured, please try again: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        });
        binding.address.setOnClickListener(v -> navigateToMap(binding.address.getText().toString()));
    }

    private void navigateToMap(String destination) {
        try {
            // Create a Uri from an intent string. Use the result to create an Intent
            Uri gmmIntentUri = Uri.parse("https://www.google.co.in/maps/dir//" + destination);

            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");
            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mapIntent);

        } catch (ActivityNotFoundException e) {
            // When google map is not installed
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent ggPlayIntent = new Intent(Intent.ACTION_VIEW, uri);
            ggPlayIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ggPlayIntent);
        }
    }

    @Override
    public void invokeWaiting() {
        binding.content.setVisibility(View.GONE);
        binding.progressCircular.setVisibility(View.VISIBLE);
    }

    @Override
    public void dispatchWaiting() {
        binding.progressCircular.setVisibility(View.GONE);
        binding.content.setVisibility(View.VISIBLE);
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
            binding.button.setEnabled(false);
            binding.button.setBackgroundColor(Color.GRAY);
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
            dispatchWaiting();
            adapter.setClickListener(item -> {
                //TODO START INTENT HERE
                Intent intent = new Intent(this, AdminCartItemDetail.class);
                intent.putExtra("cartItem", item);

                Product currentProduct = getProductById(task.getResult(), item.getProductId());
                intent.putExtra("productTitle", currentProduct.getTitle());
                intent.putExtra("productImageUrl", currentProduct.getImageUrl());

                startActivity(intent);
            });
        });
    }

    private Product getProductById(List<Product> products, String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return new Product();
    }
}
