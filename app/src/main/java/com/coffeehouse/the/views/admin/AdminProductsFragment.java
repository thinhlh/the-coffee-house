package com.coffeehouse.the.views.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.ProductAdapter;
import com.coffeehouse.the.databinding.FragmentAdminProductsBindingImpl;
import com.coffeehouse.the.utils.SwipeToDeleteCallback;
import com.coffeehouse.the.viewModels.admin.AdminProductsViewModel;

import org.jetbrains.annotations.NotNull;

public class AdminProductsFragment extends Fragment {

    private AdminProductsViewModel viewModel;
    private final ProductAdapter adapter = new ProductAdapter();
    private FragmentAdminProductsBindingImpl binding;


    public AdminProductsFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_products, container, false);
        viewModel = new ViewModelProvider(this).get(AdminProductsViewModel.class);

        setUpRecyclerView();

        return binding.getRoot();
    }

    private void setUpRecyclerView() {
        RecyclerView productsRecyclerView = binding.productsRecyclerView;

        productsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productsRecyclerView.setHasFixedSize(true);
        productsRecyclerView.setAdapter(adapter);

        viewModel.getProducts().observe(getViewLifecycleOwner(), adapter::setItems);

        enableSwipeToDelete(productsRecyclerView);

        adapter.setClickListener(product -> {
            Log.d("", product.getTitle());
            Intent intent = new Intent(getContext(), AdminEditNotification.class);
            intent.putExtra("product", product.toGson());
            startActivity(intent);
        });
    }

    private void enableSwipeToDelete(RecyclerView productsRecyclerView) {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                new AlertDialog.Builder(getContext()).setTitle("Delete product").setMessage("Are you sure want to delete this message?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //TODO TASK HERE THEN NOTIFY ITEM REMOVED
                        viewModel.removeProduct(position);
                        adapter.notifyItemRemoved(position);
                    }
                }).setNegativeButton("No", (dialog, which) -> {
                    adapter.notifyDataSetChanged();
                }).show();
            }
        };
        new ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(productsRecyclerView);
    }


}
