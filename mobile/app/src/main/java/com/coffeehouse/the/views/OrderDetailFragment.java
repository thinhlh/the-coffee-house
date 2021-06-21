package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.CartItemAdapter;
import com.coffeehouse.the.databinding.OrderDetailBinding;
import com.coffeehouse.the.models.Cart;
import com.coffeehouse.the.models.Order;
import com.coffeehouse.the.services.OrdersRepo;
import com.coffeehouse.the.services.UserRepo;
import com.coffeehouse.the.viewModels.OrderDetailViewModel;
import com.coffeehouse.the.views.OthersViewFragment.ChangeAddressBottomsheet;

import org.jetbrains.annotations.NotNull;

public class OrderDetailFragment extends Fragment implements View.OnClickListener {
    private OrderDetailViewModel orderDetailViewModel = new OrderDetailViewModel();
    private CartItemAdapter cartItemAdapter = new CartItemAdapter();
    private Cart currentCart = new Cart();
    private Order order;
    private OrdersRepo ordersRepo = new OrdersRepo();
    private UserRepo userRepo = new UserRepo();

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initview(view);
    }
    private void Initview(View view) {
        TextView textViewchange=view.findViewById(R.id.text_change);
        textViewchange.setOnClickListener(this);
        TextView textViewbacktoorderfragment=view.findViewById(R.id.text_addmenu);
        textViewbacktoorderfragment.setOnClickListener(this);

    }

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
        ordersRepo.addOrderData(order);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_change:
                Changechooseordermethodfragment();
                break;
            case R.id.text_addmenu:
                Backto_orderfragment();
                break;


        }

    }

    private void Backto_orderfragment() {
        FragmentManager fragmentManager=requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_fragment_container,new OrderFragment()).addToBackStack(null).commit();
    }

    private void Changechooseordermethodfragment() {
        ChangeAddressBottomsheet changeAddressBottomsheet=new ChangeAddressBottomsheet();
        changeAddressBottomsheet.setTargetFragment(OrderDetailFragment.this, 2);
        changeAddressBottomsheet.show(getFragmentManager(), "Category");
    }
}
