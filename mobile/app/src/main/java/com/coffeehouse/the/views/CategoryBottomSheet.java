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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.CategoryAdapter;
import com.coffeehouse.the.databinding.MenuBottomSheetBinding;
import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.viewModels.CategoriesViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class CategoryBottomSheet extends BottomSheetDialogFragment {
    private CategoriesViewModel categoriesViewModel;

    public CategoryBottomSheet() {
    }

    //SEND CATEGORY PICK BACK TO ORDER FRAGMENT
    public interface SendCategoryPick {
        void onInputCategory(Category category);
    }

    private SendCategoryPick listener;

    public void setListener(SendCategoryPick listener) {
        this.listener = listener;
    }
    //DONE SETUP


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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        CategoryAdapter categoryAdapter = new CategoryAdapter();
        recyclerView.setAdapter(categoryAdapter);

        getCategories(categoryAdapter);

        categoryAdapter.setClickListener(category -> {
            listener.onInputCategory(category);
            dismiss();
        });
        menuBottomSheetBinding.closeOderBottomesheetdialog.setOnClickListener(listener1 -> {
            dismiss();
        });
        menuBottomSheetBinding.rlAllProduct.setOnClickListener(listener1 -> {
            Category category = new Category("0", "Món phải thử");
            listener.onInputCategory(category);
            dismiss();
        });

        return v;
    }

    private void getCategories(CategoryAdapter categoryAdapter) {
        categoriesViewModel.getCategories().observe(getViewLifecycleOwner(), categoryAdapter::setItems);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (SendCategoryPick) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Category Bottom Sheet send Data listener");
        }
    }
}
