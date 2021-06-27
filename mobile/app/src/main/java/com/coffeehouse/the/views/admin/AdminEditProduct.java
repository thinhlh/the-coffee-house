package com.coffeehouse.the.views.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.AdminCategorySpinnerAdapter;
import com.coffeehouse.the.databinding.AdminEditProductBinding;
import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.utils.helper.WaitingHandler;
import com.coffeehouse.the.viewModels.admin.AdminEditProductViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class AdminEditProduct extends AppCompatActivity implements WaitingHandler {

    private static final int PICK_IMAGE = 1;

    private AdminEditProductBinding binding;
    private AdminEditProductViewModel viewModel;

    private AdminCategorySpinnerAdapter categorySpinnerAdapter;
    private Intent imageData;

    /* Can be null to indicate that new product but must not be null if submitted,
     * this field is used for holding the categoryId, help in enhancing performance while reloading to update
     * the categoriesAdapter
     */
    private String categoryId;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.admin_edit_product);
        viewModel = new ViewModelProvider(this).get(AdminEditProductViewModel.class);
        super.onCreate(savedInstanceState);

        setUpToolBar();
        setUpCategorySpinner();
        initValues();
        setUpListeners();
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

    private void setUpToolBar() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        Toolbar toolbar = binding.adminToolbar;
        toolbar.setNavigationOnClickListener(v -> {
            this.finish();
        });

        //TODO on submit listener for toolbar
        toolbar.setOnMenuItemClickListener(item -> {
            if (validation()) {

                invokeWaiting();

                Product currentProduct = binding.getProduct();
                currentProduct.setTitle(binding.title.getEditText().getText().toString());
                currentProduct.setDescription(binding.productDescription.getEditText().getText().toString());
                currentProduct.setPrice(Integer.parseInt(binding.itemPrice.getEditText().getText().toString()));
                currentProduct.setCategoryId(((Category) binding.spinner.getSelectedItem()).getId());

                //TODO TASK HANDLER HERE
                viewModel.onSubmitProduct(currentProduct, imageData).addOnCompleteListener(task -> {
                    dispatchWaiting();
                    if (task.isSuccessful()) {
                        this.finish();
                    } else {
                        Toast.makeText(this, "Error occurred: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
            }
            return true;
        });
    }

    private void setUpCategorySpinner() {
        final Spinner spinner = binding.spinner;
        categorySpinnerAdapter = new AdminCategorySpinnerAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                new ArrayList<>(Collections.singletonList(new Category()))
        );
        spinner.setAdapter(categorySpinnerAdapter);
        viewModel.getCategories().observe(this, categories -> {
            categorySpinnerAdapter.addAll(categories);
            updateSpinnerSelection();
        });
    }

    private void updateSpinnerSelection() {
        int categoryIndex = findIndexOfCategory();
        binding.spinner.setSelection(Math.max(categoryIndex, 0));
    }

    private void initValues() {
        Product product = new Product();
        if (getIntent().getStringExtra("product") != null) {
            product = Product.fromGson(getIntent().getStringExtra("product"));
            binding.setProduct(product);
            binding.adminToolbar.setTitle(product.getTitle());
            Picasso.get().load(product.getImageUrl()).into(binding.image);

            //Setup the current categoryId
            categoryId = product.getCategoryId();

            updateSpinnerSelection();
        } else {
            binding.setProduct(product);
            binding.adminToolbar.setTitle("Add Product");
            binding.image.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                imageData = data;
                binding.image.setVisibility(View.VISIBLE);
                Picasso.get().load(data.getData()).into(binding.image);
            }
        }
    }

    private int findIndexOfCategory() {
        if (categoryId == null) return -1;
        for (int i = 0; i < categorySpinnerAdapter.getCount(); i++) {
            if (categorySpinnerAdapter.getItem(i).getId().equals(categoryId))
                return i;
        }
        return -1;
    }

    private boolean validation() {
        boolean result = true;
        if (!validateCategory()) {
            result = false;
        }
        if (!validatePrice()) {
            result = false;
        }
        if (!validateTitle()) {
            result = false;
        }
        if (!validateImage()) {
            result = false;
        }
        return result;
    }

    private boolean validateImage() {
        //New product but not yes choose image
        if (binding.getProduct().getId().isEmpty() && imageData == null) {
            new AlertDialog.Builder(this).setTitle("No image").setMessage("You have not choose any product image!").setPositiveButton("OKAY", (dialog, which) -> {
                dialog.dismiss();
            }).show();
            return false;
        }
        return true;
    }

    private boolean validateTitle() {
        if (binding.title.getEditText().getText().toString().trim().equals("")) {
            binding.title.setError("Title must not be empty");
            binding.title.requestFocus();
            return false;
        }
        binding.title.setError(null);
        return true;
    }

    private boolean validatePrice() {
        try {
            Integer.parseInt(binding.itemPrice.getEditText().getText().toString());
            binding.itemPrice.setError(null);
            return true;
        } catch (NumberFormatException e) {
            binding.itemPrice.setError("Invalid price");
            binding.itemPrice.requestFocus();
            return false;
        }
    }

    private boolean validateCategory() {
        if (binding.spinner.getSelectedItemPosition() == 0) {
            binding.spinner.setPrompt("You have to choose the category");
            Toast.makeText(this, "You must choose category", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void invokeWaiting() {
        binding.progressCircular.setVisibility(View.VISIBLE);
        binding.content.setVisibility(View.GONE);
    }

    @Override
    public void dispatchWaiting() {


        binding.progressCircular.setVisibility(View.GONE);
        binding.content.setVisibility(View.VISIBLE);
    }
}
