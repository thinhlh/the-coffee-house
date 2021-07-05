package com.coffeehouse.the.views.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.AdminOrderAdapter;
import com.coffeehouse.the.databinding.AdminOrdersFragmentBinding;
import com.coffeehouse.the.viewModels.admin.AdminOrderViewModel;

import org.jetbrains.annotations.NotNull;

public class AdminOrdersFragment extends Fragment implements SearchView.OnQueryTextListener {

    private AdminOrdersFragmentBinding binding;

    private AdminOrderViewModel viewModel;

    private final AdminOrderAdapter adapter = new AdminOrderAdapter();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.admin_orders_fragment, container, false);
        viewModel = new ViewModelProvider(this).get(AdminOrderViewModel.class);

        setUpComponents();
        return binding.getRoot();
    }

    private void setUpComponents() {
        binding.searchView.setOnQueryTextListener(this);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView ordersRecyclerView = binding.recyclerView;

        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ordersRecyclerView.setHasFixedSize(true);
        ordersRecyclerView.setAdapter(adapter);

        //TODO observable here
        viewModel.getOrders().observe(getViewLifecycleOwner(), adapter::setItems);

        adapter.setClickListener(item -> {
            //TODO START ACTIVITY HERE
            Intent intent = new Intent(getContext(), AdminOrderDetail.class);
            intent.putExtra("order", item.toGson());
            intent.putExtra("cart", item.getCart());
            startActivity(intent);
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
