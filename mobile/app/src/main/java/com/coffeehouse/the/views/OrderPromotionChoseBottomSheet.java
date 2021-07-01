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
import com.coffeehouse.the.adapter.PromotionAdapter;
import com.coffeehouse.the.databinding.BottomsheetChoosepromotionOrderdetailBinding;
import com.coffeehouse.the.models.Promotion;
import com.coffeehouse.the.viewModels.PromotionViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class OrderPromotionChoseBottomSheet extends BottomSheetDialogFragment {
    private BottomsheetChoosepromotionOrderdetailBinding binding;
    private final PromotionAdapter promotionAdapter = new PromotionAdapter();
    private PromotionViewModel promotionViewModel;

    public OrderPromotionChoseBottomSheet() {
    }

    public interface updatePromotionSelect {
        void onPromotionPick(Promotion promotion);
    }

    private updatePromotionSelect listener;

    public void setListener(updatePromotionSelect listener) {
        this.listener = listener;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_choosepromotion_orderdetail, container, false);

        //Binding
        promotionViewModel = new ViewModelProvider(this).get(PromotionViewModel.class);
        RecyclerView recyclerView = binding.promotionRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(promotionAdapter);
        getPromotions(promotionAdapter);
        //End binding

        //Search promotion by Code
        binding.searchPromotion.setOnClickListener(l -> {
            searchPromotion(binding.inputPromotionCode.getText().toString(), promotionAdapter);
        });
        //End search

        //Promotion Click event
        promotionAdapter.setClickListener(item -> {
            listener.onPromotionPick(item);
            dismiss();
        });

        binding.closePromotion.setOnClickListener(l -> {
            dismiss();
        });

        return binding.getRoot();
    }

    private void getPromotions(PromotionAdapter promotionAdapter) {
        promotionViewModel.getPromotions().observe(getViewLifecycleOwner(), promotionAdapter::setItems);
    }

    private void searchPromotion(String s, PromotionAdapter promotionAdapter) {
        promotionViewModel.searchPromotions(s).observe(getViewLifecycleOwner(), promotionAdapter::setItems);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (updatePromotionSelect) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UpdatePromotion listener");
        }
    }
}
