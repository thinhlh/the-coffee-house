package com.coffeehouse.the.views.OthersViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.PromotionItembottomsheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class PromotionItemBottomsheet extends BottomSheetDialogFragment  {
    private PromotionItembottomsheetBinding binding;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
            binding= DataBindingUtil.inflate(inflater,R.layout.promotion_itembottomsheet,container,false);
            binding.closePromotionItembottomsheet.setOnClickListener(view ->{
                dismiss();
            });
            return  binding.getRoot();
    }
}
