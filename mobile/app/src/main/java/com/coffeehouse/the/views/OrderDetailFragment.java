package com.coffeehouse.the.views;

import android.content.Context;
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
import com.coffeehouse.the.models.Promotion;
import com.coffeehouse.the.models.UserAddress;
import com.coffeehouse.the.services.repositories.OrdersRepo;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.coffeehouse.the.viewModels.OrderDetailViewModel;
import com.coffeehouse.the.views.OthersViewFragment.ChangeAddressBottomSheet;

import org.jetbrains.annotations.NotNull;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class OrderDetailFragment extends Fragment implements View.OnClickListener, ChangeAddressBottomSheet.OrderStorePick, OrderPromotionChoseBottomSheet.updatePromotionSelect {
    private OrderDetailBinding orderDetailBinding;
    private OrderDetailViewModel orderDetailViewModel = new OrderDetailViewModel();
    private final CartItemAdapter cartItemAdapter = new CartItemAdapter();
    private Cart currentCart = new Cart();
    private UserAddress address = new UserAddress();
    private final OrdersRepo ordersRepo = new OrdersRepo();
    private final UserRepo userRepo = new UserRepo();
    private boolean delivered;
    private String promotionId;

    private final Locale locale = new Locale("vi", "VN");
    private final Format format = NumberFormat.getCurrencyInstance(locale);


    //Declare Delete current Cart Region
    public interface deleteCart {
        void onDeleteCart();
    }

    private deleteCart listener;

    public void setListener(deleteCart listener) {
        this.listener = listener;
    }
    //End


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
        orderDetailViewModel.setTotalBill(currentCart.getTotalCartValue());
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
            deleteCurrentCart();
        });

        if (orderDetailViewModel.getCart().getItems().size() == 0) {
            orderDetailBinding.textAddmenu.setError("Ch??a c?? s???n ph???m trong gi???");
        }

        orderDetailBinding.textChange.setOnClickListener(this::onClick);
        orderDetailBinding.textAddmenu.setOnClickListener(this::onClick);
        orderDetailBinding.textChoosepromotion.setOnClickListener(this::onClick);

        cartItemAdapter.setClickListener(this::updateCartItem);

        orderDetailBinding.selectPromotion.setOnClickListener(l -> {
            selectPromotion();
        });

        return v;
    }

    private void updateCartItem(Cart items) {
        currentCart = items;
        orderDetailViewModel.setCart(items);
        orderDetailBinding.setCart(items);
        orderDetailViewModel.setTotalBill(items.getTotalCartValue());
        if (!orderDetailBinding.txtPromotionCode.getText().toString().isEmpty()) {
            orderDetailBinding.txtPromotionCode.setText("");
            Toast.makeText(getContext(), "Ch???n l???i ??u ????i ph?? h???p v???i ????n h??ng m???i", Toast.LENGTH_SHORT).show();
        }
        if (orderDetailViewModel.getCart().getItems().size() == 0) {
            orderDetailBinding.textAddmenu.setError("Ch??a c?? s???n ph???m trong gi???");
        }
        getCart(cartItemAdapter);
    }

    private void deleteCurrentCart() {
        currentCart = new Cart();
        orderDetailViewModel.setCart(currentCart);
        orderDetailViewModel.setTotalBill(currentCart.getTotalCartValue());
        orderDetailBinding.txtPromotionCode.setText("");
        getCart(cartItemAdapter);
        orderDetailBinding.setCart(currentCart);
        this.listener.onDeleteCart();
    }

    private boolean validation() {
        if (orderDetailViewModel.getCart().getItems().size() == 0) {
            Toast.makeText(getContext(), "Ch??a c?? s???n ph???m trong gi???", Toast.LENGTH_SHORT).show();
            return false;
        } else if (orderDetailBinding.textOrder.getText().toString().equals("Ph????ng th???c giao h??ng")) {
            orderDetailBinding.textChange.setError("Ch???nh s???a ph????ng th???c nh???n h??ng");
            Toast.makeText(getContext(), "Ch???nh s???a ph????ng th???c nh???n h??ng", Toast.LENGTH_SHORT).show();
            return false;
        } else if (orderDetailBinding.textName.getText().toString().isEmpty()) {
            orderDetailBinding.textName.setError("Nh???p t??n ng?????i nh???n");
            Toast.makeText(getContext(), "Nh???p t??n ng?????i nh???n", Toast.LENGTH_SHORT).show();
            return false;
        } else if (orderDetailBinding.textPhoneNumber.getText().toString().isEmpty()) {
            orderDetailBinding.textPhoneNumber.setError("Nh???p s??? ??i???n tho???i ng?????i nh???n");
            Toast.makeText(getContext(), "Nh???p s??? ??i???n tho???i ng?????i nh???n", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void createOrder() {
        if (validation()) {
            Order order = new Order(orderDetailViewModel.getCart(), orderDetailBinding.textOrder.getText().toString(), delivered, orderDetailViewModel.getTotalBill(), promotionId, orderDetailBinding.textDestinationDetail.getText().toString(), orderDetailBinding.textName.getText().toString(), orderDetailBinding.textPhoneNumber.getText().toString());
            ordersRepo.addOrderData(order).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    userRepo.updateUserPoint(orderDetailViewModel.getCart().getTotalCartValue() / 1000);
                    Toast.makeText(getContext(), "?????t h??ng th??nh c??ng!", Toast.LENGTH_SHORT).show();
                    deleteCurrentCart();
                    backToOrderFragment();
                } else {
                    String e = task.getException().getMessage();
                    Toast.makeText(getContext(), "????n h??ng ch??a ???????c ghi nh???n: " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void backToOrderFragment() {
        getFragmentManager().popBackStack();
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
                selectPromotion();
                break;
        }
    }

    private void selectPromotion() {
        OrderPromotionChoseBottomSheet orderPromotionChoseBottomSheet = new OrderPromotionChoseBottomSheet();
        orderPromotionChoseBottomSheet.setTargetFragment(OrderDetailFragment.this, 4);
        orderPromotionChoseBottomSheet.show(getFragmentManager(), "Choose Promotion");
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
        currentAddress(name, des, recipientName, recipientPhone);
    }

    private void currentAddress(String name, String des, String recipientName, String recipientPhone) {
        address.setTitle(name);
        address.setDescription(des);
        address.setTitle(name);
        address.setTitle(name);
    }

    private void shipOrder(String name, String des, String recipientName, String recipientPhone) {
        orderDetailBinding.textChange.setError(null);
        orderDetailBinding.textOrder.setText("Giao t???n n??i");
        delivered = true;
        orderDetailBinding.textDestination.setText(name);
        orderDetailBinding.textDestinationDetail.setText(des);
        orderDetailBinding.textName.setText(recipientName);
        orderDetailBinding.textPhoneNumber.setText(recipientPhone);
        orderDetailBinding.textOrderprice.setText(format.format(orderDetailViewModel.getTotalBill() + 30000));
    }

    private void storeOrder(String name, String des, String recipientName, String recipientPhone) {
        orderDetailBinding.textChange.setError(null);
        orderDetailBinding.textOrder.setText("?????n l???y t???i c???a h??ng");
        delivered = false;
        orderDetailBinding.textDestination.setText(name);
        orderDetailBinding.textDestinationDetail.setText(des);
        orderDetailBinding.textName.setText(recipientName);
        orderDetailBinding.textPhoneNumber.setText(recipientPhone);
        orderDetailBinding.textOrderprice.setText(format.format(orderDetailViewModel.getTotalBill()));
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    @Override
    public void onPromotionPick(Promotion promotion) {
        if (currentCart.getTotalCartValue() == 0) {
            Toast.makeText(getContext(), "Ch??a c?? s???n ph???m ???????c ch???n", Toast.LENGTH_SHORT).show();
        } else {
            promotionId = promotion.getId();
            orderDetailBinding.txtPromotionCode.setText(promotion.getCode());
            orderDetailBinding.txtPromotionCode.setError(null);
            String price;
            int ship = 0;
            if (orderDetailBinding.textOrder.getText().equals("Giao t???n n??i"))
                ship = 30000;

            if (promotion.getValue().contains("%")) {
                int _current = (int) (currentCart.getTotalCartValue() * (1 - ((double) promotion.getValueToInt() / 100)));
                orderDetailViewModel.setTotalBill(_current);
            } else {
                int _current = currentCart.getTotalCartValue() - Integer.parseInt(promotion.getValue());
                orderDetailViewModel.setTotalBill(Math.max(_current, 0));
            }

            price = format.format(ship + orderDetailViewModel.getTotalBill());
            orderDetailBinding.textItempricetotal2.setText(price);
            orderDetailBinding.textOrderprice.setText(price);
        }
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (deleteCart) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Delete Current Cart listener");
        }
    }
}