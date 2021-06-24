package com.coffeehouse.the.views.OthersViewFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.BottomsheetChangeaddressOrderdetailBinding;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.coffeehouse.the.views.OrderAddressChoseBottomSheet;
import com.coffeehouse.the.views.OrderStoreChoseBottomSheet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class ChangeAddressBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener, OrderStoreChoseBottomSheet.UpdateOrderStore, OrderAddressChoseBottomSheet.UpdateOrderAddress {
    private BottomsheetChangeaddressOrderdetailBinding bottomsheetChangeaddressOrderdetailBinding;

    public interface OrderStorePick {
        void onOrderStorePick(String name, String des, String recipientName, String recipientPhone, boolean flag);
    }

    private OrderStorePick listener;

    public void setListener(OrderStorePick listener) {
        this.listener = listener;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        bottomsheetChangeaddressOrderdetailBinding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_changeaddress_orderdetail, container, false);
        View v = bottomsheetChangeaddressOrderdetailBinding.getRoot();

        bottomsheetChangeaddressOrderdetailBinding.setUser(UserRepo.user);

        bottomsheetChangeaddressOrderdetailBinding.buttonChangeUseraddress.setOnClickListener(this::onClick);
        bottomsheetChangeaddressOrderdetailBinding.changeUseraddress.setOnClickListener(this::onClick);
        bottomsheetChangeaddressOrderdetailBinding.buttonChooseStorelocation.setOnClickListener(this::onClick);
        bottomsheetChangeaddressOrderdetailBinding.chooseStorelocation.setOnClickListener(this::onClick);
        bottomsheetChangeaddressOrderdetailBinding.closeChangeaddress.setOnClickListener(listener -> {
            dismiss();
        });

        return v;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_change_useraddress || v.getId() == R.id.change_useraddress) {
            updateUserAddress();
        } else if (v.getId() == R.id.button_choose_storelocation || v.getId() == R.id.choose_storelocation) {
            updateChosenStoreOrder();
        }
        dismiss();
    }

    private void updateUserAddress() {
        OrderAddressChoseBottomSheet bottomSheet = new OrderAddressChoseBottomSheet();
        bottomSheet.setTargetFragment(ChangeAddressBottomSheet.this, 10);
        bottomSheet.show(getFragmentManager(), "Address Pick");
    }

    private void updateChosenStoreOrder() {
        OrderStoreChoseBottomSheet bottomSheet = new OrderStoreChoseBottomSheet();
        bottomSheet.setTargetFragment(ChangeAddressBottomSheet.this, 9);
        bottomSheet.show(getFragmentManager(), "Store pick");
    }

    @Override
    public void onUpdateOrderAddress(String name, String des, String recipientName, String recipientPhone) {
        listener.onOrderStorePick(name, des, recipientName, recipientPhone, true);
    }

    @Override
    public void onUpdateOrderStore(String name, String des, String recipientName, String recipientPhone) {
        listener.onOrderStorePick(name, des, recipientName, recipientPhone, false);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (OrderStorePick) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OrderPick listener");
        }
    }
}