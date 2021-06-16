package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.CartItemAdapter;
import com.coffeehouse.the.databinding.OrderDetailBinding;
import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.services.OrderRepo;
import com.coffeehouse.the.services.UserRepo;
import com.coffeehouse.the.viewModels.OrderDetailViewModel;

import org.jetbrains.annotations.NotNull;

public class OrderDetailFragment extends Fragment {
    private OrderDetailViewModel orderDetailViewModel = new OrderDetailViewModel();
    private CartItemAdapter cartItemAdapter = new CartItemAdapter();
    private Cart currentCart = new Cart();
    private Order order;
    private OrderRepo orderRepo = new OrderRepo();
    private UserRepo userRepo = new UserRepo();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        OrderDetailBinding orderDetailBinding = DataBindingUtil.inflate(inflater, R.layout.order_detail, container, false);
        View v = orderDetailBinding.getRoot();

        currentCart = orderDetailViewModel.getCart();
        orderDetailBinding.setCart(currentCart);
        orderDetailBinding.setUser(UserRepo.user);

        //BINDING
        orderDetailViewModel = new ViewModelProvider(this).get(OrderDetailViewModel.class);
        orderDetailViewModel.setCart(currentCart);
        RecyclerView recyclerView = orderDetailBinding.cartRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cartItemAdapter);
        getCart(cartItemAdapter);
        //END BINDING

        orderDetailBinding.closeCartInfo.setOnClickListener(listener -> {
            backToOrderFragment();
        });
        orderDetailBinding.imageFinalorder.setOnClickListener(listener -> {
            createOrder();
        });
        orderDetailBinding.deleteCart.setOnClickListener(listener -> {
            currentCart = new Cart();
            orderDetailViewModel.setCart(currentCart);
            getCart(cartItemAdapter);
            orderDetailBinding.setCart(currentCart);
        });

        return v;
    }

    private void createOrder() {
        order = new Order(orderDetailViewModel.getCart());
        orderRepo.addOrderData(order);
        userRepo.updateUserPoint(orderDetailViewModel.getCart().getTotalCartValue() / 1000);
        OrderFragment fragment = new OrderFragment();
        getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
    }

    private void backToOrderFragment() {
        OrderFragment fragment = new OrderFragment();
        fragment.setCart(orderDetailViewModel.getCart());
        getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
    }

    private void getCart(CartItemAdapter cartItemAdapter) {
        orderDetailViewModel.getCartLiveData().observe(getViewLifecycleOwner(), cartItemAdapter::setCart);
    }

    public void setCartOrderView(Cart cart) {
        orderDetailViewModel.setCart(cart);
    }
}
