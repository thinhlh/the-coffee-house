package com.coffeehouse.the.views.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.AdminPromotionAdapter;
import com.coffeehouse.the.databinding.AdminPromotionsActivityBinding;
import com.coffeehouse.the.utils.helper.SwipeToDeleteCallback;
import com.coffeehouse.the.viewModels.admin.AdminPromotionsViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AdminPromotions extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private AdminPromotionsActivityBinding binding;
    private AdminPromotionsViewModel viewModel;
    private final AdminPromotionAdapter adapter = new AdminPromotionAdapter();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.admin_promotions_activity);
        viewModel = new ViewModelProvider(this).get(AdminPromotionsViewModel.class);

        setUpComponents();
        super.onCreate(savedInstanceState);
    }

    private void setUpComponents() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setUpRecyclerView();
        setUpListeners();
    }

    private void setUpListeners() {
        binding.backButton.setOnClickListener(v -> this.finish());
        binding.addButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminEditPromotion.class));
        });
        binding.searchView.setOnQueryTextListener(this);
    }

    private void setUpRecyclerView() {
        RecyclerView promotionsRecyclerView = binding.promotionRecyclerview;

        promotionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        promotionsRecyclerView.setHasFixedSize(true);
        promotionsRecyclerView.setAdapter(adapter);

        viewModel.getPromotions().observe(this, adapter::setItems);

        adapter.setClickListener(promotion -> {
            Intent intent = new Intent(this, AdminEditPromotion.class);
            intent.putExtra("promotion", promotion.toGson());
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

                new AlertDialog.Builder(context).setTitle("Delete promotion").setMessage("Are you sure want to delete this promotion?").setPositiveButton("Yes", (dialog, which) -> {
                    viewModel.deletePromotion(position).addOnCompleteListener(task -> {
                        if (task.getException() != null) {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }).setNegativeButton("No", (dialog, which) -> {
                    adapter.notifyDataSetChanged();
                }).show();
            }
        };
        new ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.promotionRecyclerview);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return true;
    }
}
