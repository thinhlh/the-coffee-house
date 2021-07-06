package com.coffeehouse.the.views.OthersViewFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.OrderHistoryDetailAdapter;
import com.coffeehouse.the.databinding.OrderHistoryDetailFragmentBinding;
import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.services.repositories.PromotionsRepo;
import com.coffeehouse.the.viewModels.OrderDetailViewModel;
import com.coffeehouse.the.views.OrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class OrderHistoryDetailFragment extends Fragment {
    private Order order;
    private OrderHistoryDetailFragmentBinding orderHistoryDetailFragmentBinding;
    private OrderDetailViewModel orderDetailViewModel = new OrderDetailViewModel();
    private final OrderHistoryDetailAdapter adapter = new OrderHistoryDetailAdapter();
    private final PromotionsRepo promotionsRepo = new PromotionsRepo();

    public OrderHistoryDetailFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        orderHistoryDetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.order_history_detail_fragment, container, false);

        //Binding
        orderHistoryDetailFragmentBinding.setOrder(order);
        orderDetailViewModel = new ViewModelProvider(this).get(OrderDetailViewModel.class);
        orderDetailViewModel.setCart(order.getCart());
        setUpRecyclerView();
//        RecyclerView recyclerView = orderHistoryDetailFragmentBinding.orderCartRecyclerView;
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(adapter);
//        getCart(adapter);
        //End binding

        //Set up View
        orderHistoryDetailFragmentBinding.totalOrder.setText(total());
        if (order.getDelivered())
            orderHistoryDetailFragmentBinding.txtOrderStatus.setText("Hoàn tất");
        else
            orderHistoryDetailFragmentBinding.txtOrderStatus.setText("Đang chuẩn bị");
        promotionsRepo.getPromotions().observe(getViewLifecycleOwner(), observe -> {
            if (promotionsRepo.getPromotionById(order.getPromotionId()) != null)
                orderHistoryDetailFragmentBinding.textPromotionCode.setText(promotionsRepo.getPromotionById(order.getPromotionId()).getCode());
        });
        //End

        orderHistoryDetailFragmentBinding.btnBuyBack.setOnClickListener(l -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.home_fragment_container, new OrderFragment()).addToBackStack(null).commit();
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.action_order);
        });
        orderHistoryDetailFragmentBinding.btnContactSupporter.setOnClickListener(l -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:18006936"));
            startActivity(intent);
        });

        orderHistoryDetailFragmentBinding.closeOrderHistoryFragmentDetailFragment.setOnClickListener(l -> {
            OrderHistoryFragment fragment = new OrderHistoryFragment();
            getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
        });

        return orderHistoryDetailFragmentBinding.getRoot();
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = orderHistoryDetailFragmentBinding.orderCartRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        getCart(adapter);
    }

    private void getCart(OrderHistoryDetailAdapter adapter) {
        orderDetailViewModel.getCartLiveData().observe(getViewLifecycleOwner(), adapter::setCart);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    private String total() {
        Locale locale = new Locale("vi", "VN");
        Format format = NumberFormat.getCurrencyInstance(locale);
        if (order.getDelivered()) {
            return format.format(order.getOrderValue() + 30000);
        } else {
            return format.format(order.getOrderValue());
        }
    }
}
