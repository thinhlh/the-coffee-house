package com.coffeehouse.the.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.UserAddressAdapter;
import com.coffeehouse.the.databinding.SavedAddressFragmentBinding;
import com.coffeehouse.the.viewModels.UserAddressViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class OrderAddressChoseBottomSheet extends BottomSheetDialogFragment {
    private UserAddressViewModel userAddressViewModel;
    private UserAddressAdapter userAddressAdapter;

    public OrderAddressChoseBottomSheet() {
    }

    public interface UpdateOrderAddress {
        void onUpdateOrderAddress(String name, String des, String recipientName, String recipientPhone);
    }

    private UpdateOrderAddress listener;

    public void setListener(UpdateOrderAddress listener) {
        this.listener = listener;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        SavedAddressFragmentBinding savedAddressFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.saved_address_fragment, container, false);
        View v = savedAddressFragmentBinding.getRoot();

        savedAddressFragmentBinding.addAddressCard.setVisibility(View.GONE);

        //Binding
        userAddressViewModel = new ViewModelProvider(this).get(UserAddressViewModel.class);
        RecyclerView recyclerView = savedAddressFragmentBinding.userAddressRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        userAddressAdapter = new UserAddressAdapter();
        recyclerView.setAdapter(userAddressAdapter);
        getUserAddress(userAddressAdapter);
        //End binding

        userAddressAdapter.setClickListener(item -> {
            listener.onUpdateOrderAddress(item.getTitle(), item.getDescription(), item.getRecipientName(), item.getRecipientPhone());
            dismiss();
        });

        savedAddressFragmentBinding.closeSavedAddress.setOnClickListener(listener -> {
            dismiss();
        });


        return v;
    }

    private void getUserAddress(UserAddressAdapter userAddressAdapter) {
        userAddressViewModel.getUserAddress().observe(getViewLifecycleOwner(), userAddressAdapter::setItems);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateOrderAddress) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UpdateOrderAddress listener");
        }
    }
}
