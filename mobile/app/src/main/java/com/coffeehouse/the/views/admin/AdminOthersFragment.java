package com.coffeehouse.the.views.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.AdminOthersFragmentBinding;
import com.coffeehouse.the.viewModels.admin.AdminOthersViewModel;
import com.coffeehouse.the.viewModels.admin.AdminUsersViewModel;
import com.coffeehouse.the.views.MainActivity;

import org.jetbrains.annotations.NotNull;

public class AdminOthersFragment extends Fragment {

    private AdminOthersFragmentBinding binding;
    private AdminOthersViewModel viewModel;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.admin_others_fragment, container, false);
        viewModel = new ViewModelProvider(this).get(AdminOthersViewModel.class);


        setListeners();
        return binding.getRoot();
    }

    private void setListeners() {

        binding.stores.setOnClickListener(v -> startActivity(new Intent(getContext(), AdminStoresActivity.class)));

        binding.categories.setOnClickListener(v -> startActivity(new Intent(getContext(), AdminCategories.class)));

        binding.statistic.setOnClickListener(v -> startActivity(new Intent(getContext(), ProfitActivity.class)));

        binding.promotion.setOnClickListener(v -> startActivity(new Intent(getContext(), AdminPromotions.class)));

        binding.logout.setOnClickListener(v -> viewModel.signOut(getContext()).addOnCompleteListener(task -> {
            startActivity(new Intent(getContext(), MainActivity.class));
        }));
    }
}
