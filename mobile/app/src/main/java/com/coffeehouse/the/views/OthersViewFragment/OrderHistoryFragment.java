package com.coffeehouse.the.views.OthersViewFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.OrderHistoryAdapter;
import com.coffeehouse.the.databinding.HistoryFragmentBinding;
import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.CartItem;
import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.services.OrdersRepo;
import com.coffeehouse.the.viewModels.OrderHistoryViewModel;
import com.coffeehouse.the.viewModels.OrderViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderHistoryFragment extends Fragment {
    private OrderHistoryViewModel orderHistoryViewModel;
    private OrderHistoryAdapter orderHistoryAdapter;
    private HistoryFragmentBinding historyFragmentBinding;

    public OrderHistoryFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        historyFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.history_fragment, container, false);
        View v = historyFragmentBinding.getRoot();

        //Binding
        orderHistoryViewModel = new ViewModelProvider(this).get(OrderHistoryViewModel.class);
        RecyclerView recyclerView = historyFragmentBinding.historyFragmentRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        orderHistoryAdapter = new OrderHistoryAdapter();
        recyclerView.setAdapter(orderHistoryAdapter);
        getOrder(orderHistoryAdapter);

        orderHistoryAdapter.getOrders();
        orderHistoryAdapter.getItemCount();
        //End binding

        historyFragmentBinding.closeOrderHistoryFragment.setOnClickListener(listener -> {
            Fragment fragment = new OthersFragment();
            getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
        });

        return v;
    }

    private void getOrder(OrderHistoryAdapter orderHistoryAdapter) {
        orderHistoryViewModel.getOrders().observe(getViewLifecycleOwner(), orderHistoryAdapter::setOrders);
    }
}