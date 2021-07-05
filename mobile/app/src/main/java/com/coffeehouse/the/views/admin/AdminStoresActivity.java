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
import com.coffeehouse.the.adapter.AdminStoreAdapter;
import com.coffeehouse.the.databinding.AdminStoresActivityBinding;
import com.coffeehouse.the.utils.helper.SwipeToDeleteCallback;
import com.coffeehouse.the.viewModels.admin.AdminStoresViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AdminStoresActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private AdminStoresActivityBinding binding;
    private final AdminStoreAdapter adapter = new AdminStoreAdapter();
    private AdminStoresViewModel viewModel;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.admin_stores_activity);
        viewModel = new ViewModelProvider(this).get(AdminStoresViewModel.class);

        setUpComponents();
        super.onCreate(savedInstanceState);
    }

    private void setUpComponents() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setUpRecyclerView();
        setUpListener();
    }

    private void setUpRecyclerView() {
        RecyclerView storesRecyclerview = binding.storeRecyclerView;

        storesRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        storesRecyclerview.setHasFixedSize(true);
        storesRecyclerview.setAdapter(adapter);

        viewModel.getStores().observe(this, adapter::setItems);
        enableSwipeToDelete();

        adapter.setClickListener(store -> {
            //TODO NAVIGATE TO NEW ADMIN EDIT STORE INTENT
            Intent intent = new Intent(this, AdminEditStore.class);
            intent.putExtra("store",store.toGson());
            startActivity(intent);
        });

    }

    private void setUpListener() {
        binding.backButton.setOnClickListener(v -> this.finish());
        binding.addButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminEditStore.class));
        });
        binding.searchView.setOnQueryTextListener(this);
    }

    private void enableSwipeToDelete() {
        Context context = this;
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                new AlertDialog.Builder(context).setTitle("Delete store").setMessage("Are you sure want to delete this store?").setPositiveButton("Yes", (dialog, which) -> {
                    viewModel.removeStore(position).addOnCompleteListener(task -> {
                        if (task.getException() != null) {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }).setNegativeButton("No", (dialog, which) -> {
                    adapter.notifyDataSetChanged();
                }).show();
            }
        };
        new ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.storeRecyclerView);
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
