package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.ActivityProductDetailBinding;
import com.coffeehouse.the.databinding.StoreLocationDetailBinding;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.models.Store;
import com.coffeehouse.the.viewModels.ProductDetailViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class StoreDetailBottomSheet extends BottomSheetDialogFragment {

    private Store store = new Store();

    public StoreDetailBottomSheet() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //INFLATE
        StoreLocationDetailBinding storeLocationDetailBinding = DataBindingUtil.inflate(inflater, R.layout.store_location_detail, container, false);
        View v = storeLocationDetailBinding.getRoot();

        //BINDING
        storeLocationDetailBinding.setStoreDetail(store);
        storeLocationDetailBinding.setLifecycleOwner(this);
        //END BINDING

        v.findViewById(R.id.store_detail_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        v.findViewById(R.id.store_detail_gg_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setStoreChosen(Store store) {
        this.store = store;
    }
}
