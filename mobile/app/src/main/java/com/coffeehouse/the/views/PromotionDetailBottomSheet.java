package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.PromotionItembottomsheetBinding;
import com.coffeehouse.the.models.Promotion;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PromotionDetailBottomSheet extends BottomSheetDialogFragment {
    private Promotion promotion = new Promotion();

    public PromotionDetailBottomSheet() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        PromotionItembottomsheetBinding binding = DataBindingUtil.inflate(inflater, R.layout.promotion_itembottomsheet, container, false);

        binding.setPromotion(promotion);
        binding.setLifecycleOwner(this);
        String s = promotion.getDescription().replaceAll("@", "\n");
        binding.promotionDetailDescription.setText(s);
        binding.expiryDate.setText(promotionExpiryDate(promotion));

        binding.closePromotionItemBottomSheet.setOnClickListener(l -> {
            dismiss();
        });

        binding.btnUseNow.setOnClickListener(l -> {
            navigateToOrder();
            dismiss();
        });

        return binding.getRoot();
    }

    private void navigateToOrder() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.home_fragment_container, new OrderFragment()).addToBackStack(null).commit();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_order);
    }

    private String promotionExpiryDate(Promotion promotion) {
        DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        return df.format(promotion.getExpiryDate());
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}
