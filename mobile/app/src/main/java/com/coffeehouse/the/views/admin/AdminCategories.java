package com.coffeehouse.the.views.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.AdminCategoryAdapter;
import com.coffeehouse.the.adapter.CategoryAdapter;
import com.coffeehouse.the.databinding.AdminCategoriesActivityBinding;
import com.coffeehouse.the.utils.helper.SwipeToDeleteCallback;
import com.coffeehouse.the.viewModels.admin.AdminCategoriesViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AdminCategories extends AppCompatActivity {
    private AdminCategoriesActivityBinding binding;
    private AdminCategoriesViewModel viewModel;
    private final AdminCategoryAdapter adapter = new AdminCategoryAdapter();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.admin_categories_activity);
        viewModel = new ViewModelProvider(this).get(AdminCategoriesViewModel.class);

        setUpComponents();
        super.onCreate(savedInstanceState);
    }

    private void setUpComponents() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setUpRecyclerView();
        setUpListeners();
    }

    private void setUpListeners() {
        binding.addButton.setOnClickListener(v -> startActivity(new Intent(this, AdminEditCategory.class)));

        binding.backButton.setOnClickListener(v -> this.finish());
    }

    private void setUpRecyclerView() {
        RecyclerView categoriesRecyclerView = binding.categoriesRecyclerView;

        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriesRecyclerView.setHasFixedSize(true);
        categoriesRecyclerView.setAdapter(adapter);

        viewModel.getCategories().observe(this, adapter::setItems);

        adapter.setClickListener(category -> {
            //TODO HANDLER CLICK BUTTON
            Intent intent = new Intent(this, AdminEditCategory.class);
            intent.putExtra("category", category);
            startActivity(intent);
        });

        enableSwipeToDelete();
    }

    private void enableSwipeToDelete() {
        Context context = this;
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                new AlertDialog.Builder(context).setTitle("Delete category").setMessage("Are you sure want to delete this category?").setPositiveButton("Yes", (dialog, which) -> {
                    viewModel.deleteCategory(position);
                }).setNegativeButton("No", (dialog, which) -> {
                    adapter.notifyDataSetChanged();
                }).show();
            }
        };
        new ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.categoriesRecyclerView);
    }

}
