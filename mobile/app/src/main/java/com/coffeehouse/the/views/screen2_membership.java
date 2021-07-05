package com.coffeehouse.the.views;

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
import com.coffeehouse.the.adapter.PromotionAdapter;
import com.coffeehouse.the.databinding.FragmentScreen2MembershipBinding;
import com.coffeehouse.the.viewModels.PromotionViewModel;

import org.jetbrains.annotations.NotNull;

public class screen2_membership extends Fragment {
    private FragmentScreen2MembershipBinding binding;
    private final PromotionAdapter promotionAdapter = new PromotionAdapter();
    private PromotionViewModel promotionViewModel;

    public screen2_membership() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_screen2__membership, container, false);

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

        promotionAdapter.setClickListener(item -> {
            PromotionDetailBottomSheet bottomSheet = new PromotionDetailBottomSheet();
            bottomSheet.setPromotion(item);
            bottomSheet.show(getFragmentManager(), "Promotion Detail");
        });

        return binding.getRoot();
    }

    private void getPromotions(PromotionAdapter promotionAdapter) {
        promotionViewModel.getPromotions().observe(getViewLifecycleOwner(), promotionAdapter::setItems);
    }

    private void searchPromotion(String s, PromotionAdapter promotionAdapter) {
        promotionViewModel.searchPromotions(s).observe(getViewLifecycleOwner(), promotionAdapter::setItems);
    }
}
