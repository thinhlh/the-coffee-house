package com.coffeehouse.the.views.OthersViewFragment;

import android.content.Intent;
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
import com.coffeehouse.the.adapter.UserAddressAdapter;
import com.coffeehouse.the.databinding.SavedAddressFragmentBinding;
import com.coffeehouse.the.models.UserAddress;
import com.coffeehouse.the.viewModels.UserAddressViewModel;

import org.jetbrains.annotations.NotNull;

public class SavedAddressFragment extends Fragment implements View.OnClickListener {
    private UserAddressViewModel userAddressViewModel;
    private UserAddressAdapter userAddressAdapter;

    public SavedAddressFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        SavedAddressFragmentBinding savedAddressFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.saved_address_fragment, container, false);
        View v = savedAddressFragmentBinding.getRoot();

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
            Intent intent = new Intent(getContext(), UpdateUserAddress.class);
            intent.putExtra("Flag", false);
            intent.putExtra("UserAddress", item);
            startActivity(intent);
        });

        savedAddressFragmentBinding.closeSavedAddress.setOnClickListener(listener -> {
            getFragmentManager().popBackStack();
        });
        savedAddressFragmentBinding.addNewAddress.setOnClickListener(this::onClick);

        return v;
    }

    private void getUserAddress(UserAddressAdapter userAddressAdapter) {
        userAddressViewModel.getUserAddress().observe(getViewLifecycleOwner(), userAddressAdapter::setItems);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), UpdateUserAddress.class);
        intent.putExtra("Flag", true);
        UserAddress userAddress = new UserAddress();
        intent.putExtra("UserAddress", userAddress);
        startActivity(intent);
    }

}
