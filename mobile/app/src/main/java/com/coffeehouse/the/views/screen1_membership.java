package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.PromotionAdapter;
import com.coffeehouse.the.databinding.FragmentScreen1MembershipBinding;
import com.coffeehouse.the.databinding.MembershipFragmentBinding;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.coffeehouse.the.viewModels.PromotionViewModel;

import org.jetbrains.annotations.NotNull;

public class screen1_membership extends Fragment {
    private FragmentScreen1MembershipBinding binding;
    private final PromotionAdapter promotionAdapter = new PromotionAdapter();
    private PromotionViewModel promotionViewModel;

    public screen1_membership() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_screen1__membership, container, false);
        binding.setUser(UserRepo.user);
//        binding.cardviewChangepromotion.setOnClickListener(this::onClick);

        //Binding
        promotionViewModel = new ViewModelProvider(this).get(PromotionViewModel.class);
        RecyclerView recyclerView = binding.promotionRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(promotionAdapter);
        getPromotions(promotionAdapter);
        //End binding

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

}
