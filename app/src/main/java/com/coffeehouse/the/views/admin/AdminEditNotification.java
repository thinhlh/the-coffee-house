package com.coffeehouse.the.views.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.AdminEditNotificationBinding;
import com.coffeehouse.the.models.Notification;
import com.coffeehouse.the.viewModels.admin.AdminEditNotificationViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class AdminEditNotification extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;


    private AdminEditNotificationViewModel viewModel = new AdminEditNotificationViewModel();
    private AdminEditNotificationBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AdminEditNotificationViewModel.class);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.admin_edit_notification);

        setUpToolBar();
        initValues();
    }

    private void setUpToolBar() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        Toolbar toolbar = binding.adminToolbar;
        toolbar.setNavigationOnClickListener(v -> {
            this.finish();
        });

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.submit_area) {
                validate();
            }
            return true;
        });
    }

    private void initValues() {
        Notification notification = new Notification();
        if (!(getIntent().getStringExtra("notification") == null)) {
            notification = Notification.fromGson(getIntent().getStringExtra("notification"));
            binding.setNotification(notification);
            Picasso.get().load(notification.getImageUrl()).into(binding.notificationImageView);
        } else {
            notification.setTitle("Add Notification");
            binding.setNotification(notification);
            binding.notificationImageView.setVisibility(View.GONE);
            binding.notificationDate.setVisibility(View.GONE);
        }


        binding.pickImageButton.setOnClickListener(v -> {
            if (v.getId() == R.id.pick_image_button) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });
    }

    private void validate() {
        if (Objects.requireNonNull(binding.notificationTitle.getEditText()).getText().toString().trim().equals("")) {
            binding.notificationTitle.setError("Title cannot be null");
            binding.notificationTitle.requestFocus();
        }
        if (Objects.requireNonNull(binding.notificationDescription.getEditText()).getText().toString().trim().equals("")) {
            binding.notificationDescription.setError("Description cannot be null");
            binding.notificationDescription.requestFocus();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                Picasso.get().load(data.getData()).fit().into(binding.notificationImageView);
            }
        }
    }


}
