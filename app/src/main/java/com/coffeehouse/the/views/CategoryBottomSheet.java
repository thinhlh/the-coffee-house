package com.coffeehouse.the.views;

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
import com.coffeehouse.the.adapter.CategoryAdapter;
import com.coffeehouse.the.adapter.CategoryClickListener;
import com.coffeehouse.the.databinding.MenuBottomSheetBinding;
import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.viewModels.CategoriesViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class CategoryBottomSheet extends BottomSheetDialogFragment {
    private CategoriesViewModel categoriesViewModel;

    public CategoryBottomSheet() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        //INFLATE
        MenuBottomSheetBinding menuBottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.menu_bottom_sheet, container, false);
        View v = menuBottomSheetBinding.getRoot();

        //BINDING
        categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        RecyclerView recyclerView = menuBottomSheetBinding.categoriesRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        CategoryAdapter categoryAdapter = new CategoryAdapter();
        recyclerView.setAdapter(categoryAdapter);

        getCategories(categoryAdapter);

        categoryAdapter.setListener(new CategoryClickListener() {
            @Override
            public void onCategoryClick(String categoryID) {
                dismiss();
            }
        });

        return v;
    }

    private void getCategories(CategoryAdapter categoryAdapter) {
        categoriesViewModel.getCategories().observe(getViewLifecycleOwner(), categoryAdapter::setCategories);
    }
}
