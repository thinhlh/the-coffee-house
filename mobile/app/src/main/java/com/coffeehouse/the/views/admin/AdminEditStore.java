package com.coffeehouse.the.views.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.AdminEditStoreBinding;
import com.coffeehouse.the.models.Store;
import com.coffeehouse.the.utils.helper.WaitingHandler;
import com.coffeehouse.the.viewModels.admin.AdminEditStoreViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class AdminEditStore extends AppCompatActivity implements WaitingHandler {
    private final int PICK_IMAGE = 1;
    private AdminEditStoreBinding binding;
    private AdminEditStoreViewModel viewModel;

    //For further, this can be a list of intent
    private Intent imageData = null;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.admin_edit_store);
        viewModel = new ViewModelProvider(this).get(AdminEditStoreViewModel.class);

        initComponents();
        super.onCreate(savedInstanceState);
    }

    private void initComponents() {
        initToolbar();
        initValues();
        initListeners();
    }

    private void initValues() {
        Store store = new Store();
        if (getIntent().getStringExtra("store") != null) {
            store = Store.fromGson(getIntent().getStringExtra("store"));
            Picasso.get().load(store.getImageUrl()).into(binding.image);
        }
        binding.setStore(store);
    }

    private void initToolbar() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        Toolbar toolbar = binding.adminToolbar;
        toolbar.setNavigationOnClickListener(v -> {
            this.finish();
        });

        //TODO on submit listener for toolbar
        toolbar.setOnMenuItemClickListener(item -> {
            if (validation()) {
                invokeWaiting();

                Store store = new Store();
                store.setId(binding.getStore().getId());
                if (store.getId().isEmpty())
                    store.setName(binding.title.getEditText().getText().toString());
                else {
                    store.setName(binding.getStore().getName());
                }
                store.setAddress(binding.address.getEditText().getText().toString());
                store.setImageUrl(binding.getStore().getImageUrl());
                store.setCoordinate(new LatLng(
                        Double.parseDouble(binding.latitude.getEditText().getText().toString()),
                        Double.parseDouble(binding.longitude.getEditText().getText().toString())));

                viewModel.onSubmitStore(store, imageData).addOnCompleteListener(task -> {
                    dispatchWaiting();
                    if (task.isSuccessful())
                        this.finish();
                    else
                        Toast.makeText(this, "Error occurred: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                });
            }
            return true;
        });
    }

    private void initListeners() {
        binding.pickImageButton.setOnClickListener(v -> {
            if (v.getId() == R.id.pick_image_button) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                //TODO images data can be a list for this store
                imageData = data;
                binding.image.setVisibility(View.VISIBLE);
                Picasso.get().load(data.getData()).into(binding.image);
            }
        }
    }

    private boolean validation() {
        if (binding.getStore().getId().isEmpty()) {
            if (binding.title.getEditText().getText().toString().isEmpty()) {
                binding.title.setError("Store name cannot be empty");
                binding.title.requestFocus();
                return false;
            } else {
                binding.title.setError(null);
            }
        }
        if (binding.address.getEditText().getText().toString().isEmpty()) {
            binding.address.setError("Address cannot be empty");
            binding.address.requestFocus();
            return false;
        } else {
            binding.address.setError(null);
        }
        double latitude = Double.NaN, longitude = Double.NaN;
        try {
            latitude = Double.parseDouble(binding.latitude.getEditText().getText().toString());
            binding.longitude.setError(null);
            longitude = Double.parseDouble(binding.longitude.getEditText().getText().toString());
            binding.latitude.setError(null);

        } catch (NumberFormatException e) {
            if (Double.isNaN(latitude))
                binding.latitude.setError("Invalid latitude");
            if (Double.isNaN(longitude))
                binding.longitude.setError("Invalid longitude");
            return false;
        }

        return validateImage();
    }

    private boolean validateImage() {
        //New product but not yes choose image
        if (binding.getStore().getId().isEmpty() && imageData == null) {
            new AlertDialog.Builder(this).setTitle("No image").setMessage("You have not choose any store image!").setPositiveButton("OKAY", (dialog, which) -> {
                dialog.dismiss();
            }).show();
            return false;
        }
        return true;
    }

    @Override
    public void invokeWaiting() {
        Log.d("", "Called");
        binding.progressCircular.setVisibility(View.VISIBLE);
        binding.content.setVisibility(View.GONE);
    }

    @Override
    public void dispatchWaiting() {
        binding.content.setVisibility(View.VISIBLE);
        binding.progressCircular.setVisibility(View.GONE);
    }
}
