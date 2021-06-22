package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.coffeehouse.the.services.OrdersRepo;
import com.coffeehouse.the.services.UserRepo;
import com.coffeehouse.the.viewModels.OrderDetailViewModel;
import com.coffeehouse.the.views.OthersViewFragment.BottomsheetChoosePromotionOrderDetail;
import com.coffeehouse.the.views.OthersViewFragment.ChangeAddressBottomSheet;

import org.jetbrains.annotations.NotNull;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class OrderDetailFragment extends Fragment implements View.OnClickListener, ChangeAddressBottomSheet.OrderStorePick {
    private OrderDetailBinding orderDetailBinding;
    private OrderDetailViewModel orderDetailViewModel = new OrderDetailViewModel();
    private CartItemAdapter cartItemAdapter = new CartItemAdapter();
    private Cart currentCart = new Cart();
    private Order order;
    private OrdersRepo ordersRepo = new OrdersRepo();
    private UserRepo userRepo = new UserRepo();

    private Locale locale = new Locale("vi", "VN");
    private Format format = NumberFormat.getCurrencyInstance(locale);

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        orderDetailBinding = DataBindingUtil.inflate(inflater, R.layout.order_detail, container, false);
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

        orderDetailBinding.textChange.setOnClickListener(this::onClick);
        orderDetailBinding.textAddmenu.setOnClickListener(this::onClick);
        orderDetailBinding.textChoosepromotion.setOnClickListener(this::onClick);

        return v;
    }

    private void createOrder() {
        if (orderDetailBinding.textOrder.getText().toString().equals("Phương thức giao hàng")) {
            Toast.makeText(getContext(), "Chỉnh sửa chi tiết nhận hàng", Toast.LENGTH_LONG).show();
        } else if (orderDetailViewModel.getCart().getItems().size() == 0) {
            Toast.makeText(getContext(), "Chưa có sản phẩm trong giỏ", Toast.LENGTH_LONG).show();
        } else {
            order = new Order(orderDetailViewModel.getCart(), orderDetailBinding.textOrder.getText().toString(), orderDetailBinding.textDestinationDetail.getText().toString(), orderDetailBinding.textName.getText().toString(), orderDetailBinding.textPhoneNumber.getText().toString());
            ordersRepo.addOrderData(order);
            userRepo.updateUserPoint(orderDetailViewModel.getCart().getTotalCartValue() / 1000);
            OrderFragment fragment = new OrderFragment();
            getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
        }
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
        switch (v.getId()) {
            case R.id.text_change:
                orderMethod();
                break;
            case R.id.text_addmenu:
                backToOrderFragment();
                break;
            case R.id.text_choosepromotion:
                Choosepromotion();
                break;


        }
    }

    private void Choosepromotion() {
        BottomsheetChoosePromotionOrderDetail bottomsheetChoosePromotionOrderDetail =new BottomsheetChoosePromotionOrderDetail();
        bottomsheetChoosePromotionOrderDetail.setTargetFragment(OrderDetailFragment.this,4);
        bottomsheetChoosePromotionOrderDetail.show(getFragmentManager(),"Choose Promotion");
    }

    private void orderMethod() {
        ChangeAddressBottomSheet changeAddressBottomsheet = new ChangeAddressBottomSheet();
        changeAddressBottomsheet.setTargetFragment(OrderDetailFragment.this, 3);
        changeAddressBottomsheet.show(getFragmentManager(), "Choose Address");
    }

    @Override
    public void onOrderStorePick(String name, String des, String recipientName, String recipientPhone, boolean flag) {
        if (flag) {
            shipOrder(name, des, recipientName, recipientPhone);
        } else {
            storeOrder(name, des, recipientName, recipientPhone);
        }
    }

    private void shipOrder(String name, String des, String recipientName, String recipientPhone) {
        orderDetailBinding.textOrder.setText("Giao tận nơi");
        orderDetailBinding.textDestination.setText(name);
        orderDetailBinding.textDestinationDetail.setText(des);
        orderDetailBinding.textName.setText(recipientName);
        orderDetailBinding.textPhoneNumber.setText(recipientPhone);
        orderDetailBinding.textOrderprice.setText(format.format(currentCart.getTotalCartValue() + 30000));
    }

    private void storeOrder(String name, String des, String recipientName, String recipientPhone) {
        orderDetailBinding.textOrder.setText("Đến lấy tại cửa hàng");
        orderDetailBinding.textDestination.setText(name);
        orderDetailBinding.textDestinationDetail.setText(des);
        orderDetailBinding.textName.setText(recipientName);
        orderDetailBinding.textPhoneNumber.setText(recipientPhone);
        orderDetailBinding.textOrderprice.setText(format.format(currentCart.getTotalCartValue()));
    }
}