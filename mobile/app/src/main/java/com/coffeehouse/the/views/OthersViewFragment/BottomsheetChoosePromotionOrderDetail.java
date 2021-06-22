package com.coffeehouse.the.views.OthersViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.BottomsheetChoosepromotionOrderdetailBinding;
import com.coffeehouse.the.databinding.MenuBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class BottomsheetChoosePromotionOrderDetail extends BottomSheetDialogFragment implements View.OnClickListener {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
    BottomsheetChoosepromotionOrderdetailBinding bottomsheetChoosepromotionOrderdetailBinding=DataBindingUtil.inflate(inflater,R.layout.bottomsheet_choosepromotion_orderdetail,container,false);
    View v=bottomsheetChoosepromotionOrderdetailBinding.getRoot();
    bottomsheetChoosepromotionOrderdetailBinding.closePromotion.setOnClickListener(listener1 -> {
        dismiss();
    });
    return v;

    }

    @Override
    public void onClick(View v) {

    }
}
