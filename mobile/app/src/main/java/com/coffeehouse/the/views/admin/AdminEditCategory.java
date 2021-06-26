package com.coffeehouse.the.views.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.AdminEditCategoryBinding;
import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.utils.helper.WaitingHandler;
import com.coffeehouse.the.viewModels.admin.AdminEditCategoryViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class AdminEditCategory extends AppCompatActivity implements WaitingHandler {
    private final int PICK_IMAGE = 1;
    private AdminEditCategoryBinding binding;
    private AdminEditCategoryViewModel viewModel;
    private Intent imageData;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.admin_edit_category);
        viewModel = new ViewModelProvider(this).get(AdminEditCategoryViewModel.class);

        setUpComponents();

        super.onCreate(savedInstanceState);
    }

    private void setUpComponents() {
        setUpToolBar();
        initValues();
        setUpListeners();
    }

    private void setUpToolBar() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        Toolbar toolbar = binding.adminToolbar;
        toolbar.setNavigationOnClickListener(v -> {
            this.finish();
        });

        //TODO on submit listener for toolbar
        toolbar.setOnMenuItemClickListener(item -> {
            if (validation()) {
                //TODO TASK HANDLER HERE
                invokeWaiting();

                Category category = new Category();
                category.setId(binding.getCategory().getId());
                category.setTitle(binding.categoryTitle.getEditText().getText().toString());
                viewModel.onSubmitCategory(category, imageData).addOnCompleteListener(task -> {
                    dispatchWaiting();
                    if (task.isSuccessful()) {
                        this.finish();
                    } else {
                        Toast.makeText(this, "Error occurred " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return true;
        });
    }

    private void setUpListeners() {
        binding.pickImageButton.setOnClickListener(v -> {
            if (v.getId() == R.id.pick_image_button) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });
    }

    private void initValues() {
        Category category = new Category();
        if (getIntent().getSerializableExtra("category") != null) {
            category = (Category) getIntent().getSerializableExtra("category");
            Picasso.get().load(category.getImageUrl()).into(binding.categoryImage);
        } else {
            binding.categoryImage.setVisibility(View.GONE);
        }
        binding.setCategory(category);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                imageData = data;
                binding.categoryImage.setVisibility(View.VISIBLE);
                Picasso.get().load(data.getData()).into(binding.categoryImage);
            }
        }
    }

    private boolean validation() {
        if (binding.getCategory().getId().isEmpty() && imageData == null) {
            new AlertDialog.Builder(this).setTitle("No image").setMessage("You have not choose any category image!").setPositiveButton("OKAY", (dialog, which) -> {
                dialog.dismiss();
            }).show();
            return false;
        }
        if (binding.categoryTitle.getEditText().getText().toString().isEmpty()) {
            binding.categoryTitle.setError("Title cannot be empty");
            binding.categoryTitle.requestFocus();
            return false;
        } else {
            binding.categoryTitle.setError(null);
        }
        return true;
    }

    @Override
    public void invokeWaiting() {
        binding.content.setVisibility(View.GONE);
        binding.progressCircular.setVisibility(View.VISIBLE);
    }

    @Override
    public void dispatchWaiting() {
        binding.content.setVisibility(View.VISIBLE);
        binding.progressCircular.setVisibility(View.GONE);
    }
}
