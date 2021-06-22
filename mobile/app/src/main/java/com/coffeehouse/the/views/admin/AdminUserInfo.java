package com.coffeehouse.the.views.admin;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.AdminUserInfoBinding;
import com.coffeehouse.the.models.AdminCustomUser;
import com.coffeehouse.the.viewModels.admin.AdminUserInfoViewModel;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class AdminUserInfo extends AppCompatActivity {
    private AdminUserInfoBinding binding;
    private AdminUserInfoViewModel viewModel;
    private boolean isAdmin;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.admin_user_info);
        viewModel = new ViewModelProvider(this).get(AdminUserInfoViewModel.class);

        Objects.requireNonNull(getSupportActionBar()).hide();

        initValues();
        setUpPromoteButton();
    }

    private void initValues() {
        AdminCustomUser user = new AdminCustomUser();
        if (!(getIntent().getStringExtra("user") == null)) {
            user = AdminCustomUser.fromGson(getIntent().getStringExtra("user"));
            isAdmin = user.getAdmin();

            //Setting fields properties
            binding.membership.getCompoundDrawablesRelative()[0].setTint(user.getIconColorBasedOnMembership());
            binding.button.setText(isAdmin ? "Demote To Member" : "Promote To Admin");
            binding.phoneTextInput.setText(user.getPhoneNumber().isEmpty() ? "Haven't added yet" : user.getPhoneNumber());
            binding.phoneTextInput.setTypeface(null, user.getPhoneNumber().isEmpty() ? Typeface.ITALIC : Typeface.NORMAL);
        }
        binding.setUser(user);
    }

    private void setUpPromoteButton() {
        binding.button.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Account Role")
                        .setMessage("Are you sure want to " +
                                (isAdmin ? "demote" : "promote") + " this user to " +
                                (isAdmin ? "user" : "admin") + " ?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            Task<Void> changeRole = (isAdmin) ? viewModel.demoteToMember(binding.getUser().getId()) : viewModel.promoteToAdmin(binding.getUser().getId());
                            changeRole.addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(this, (!isAdmin ? "Promoted" : "Demoted") + " successful", Toast.LENGTH_SHORT).show();
                                    isAdmin = !isAdmin;
                                    binding.button.setText(isAdmin ? "Demote To Member" : "Promote To Admin");
                                }
                            });
                        }).
                        setNegativeButton("No", (dialog, which) -> {
                        }).show());
    }
}
