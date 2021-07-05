package com.coffeehouse.the.views.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.UserAdapter;
import com.coffeehouse.the.databinding.AdminUserFragmentBinding;
import com.coffeehouse.the.utils.helper.SwipeToDeleteCallback;
import com.coffeehouse.the.viewModels.admin.AdminUsersViewModel;

import org.jetbrains.annotations.NotNull;

public class AdminUsersFragment extends Fragment implements SearchView.OnQueryTextListener {
    private AdminUsersViewModel viewModel;
    private AdminUserFragmentBinding binding;

    private final UserAdapter adapter = new UserAdapter();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.admin_user_fragment, container, false);

        viewModel = new ViewModelProvider(this).get(AdminUsersViewModel.class);
        viewModel.setContext(getContext());
        setUpComponents();
        return binding.getRoot();
    }

    private void setUpComponents() {
        binding.searchView.setOnQueryTextListener(this);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView usersRecyclerView = binding.recyclerView;

        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        usersRecyclerView.setHasFixedSize(true);
        usersRecyclerView.setAdapter(adapter);

        viewModel.getUsers().observe(getViewLifecycleOwner(), adapter::setItems);

        enableSwipeToDelete(usersRecyclerView);
        adapter.setClickListener(item -> {
            //TODO OPEN INTENT FOR VIEWING USER INFORMATION
            Intent intent = new Intent(getContext(), AdminUserInfo.class);
            intent.putExtra("user", item.toGson());
            startActivity(intent);
        });
    }

    private void enableSwipeToDelete(RecyclerView usersRecyclerView) {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                new AlertDialog.Builder(getContext()).setTitle("Delete User").setMessage("Are you sure want to delete this user?").setPositiveButton("Yes", (dialog, which) -> {
                    viewModel.deleteUser(adapter.getItems().get(position).getId());
                }).setNegativeButton("No", (dialog, which) -> adapter.notifyDataSetChanged()).show();
            }

        };
        new ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(usersRecyclerView);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.filter(query);
        return true;
    }
}
