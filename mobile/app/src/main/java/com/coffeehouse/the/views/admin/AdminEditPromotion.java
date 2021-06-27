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
import com.coffeehouse.the.databinding.AdminEditPromotionBinding;
import com.coffeehouse.the.models.Category;
import com.coffeehouse.the.models.Membership;
import com.coffeehouse.the.models.Product;
import com.coffeehouse.the.models.Promotion;
import com.coffeehouse.the.utils.helper.WaitingHandler;
import com.coffeehouse.the.viewModels.admin.AdminEditPromotionViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AdminEditPromotion extends AppCompatActivity implements WaitingHandler {
    private final int PICK_IMAGE = 1;
    private AdminEditPromotionBinding binding;
    private AdminEditPromotionViewModel viewModel;
    private Intent imageData;
    private final List<Membership> tempTargetCustomer = new ArrayList<>();
    private Date expiryDate;


    @Override

    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.admin_edit_promotion);
        viewModel = new ViewModelProvider(this).get(AdminEditPromotionViewModel.class);

        setUpComponents();

        super.onCreate(savedInstanceState);
    }

    private void initValues() {
        Promotion promotion = new Promotion();
        if (getIntent().getStringExtra("promotion") != null) {
            promotion = Promotion.fromGson(getIntent().getStringExtra("promotion"));
            tempTargetCustomer.addAll(promotion.getTargetCustomer());
            Picasso.get().load(promotion.getImageUrl()).into(binding.image);


            binding.setForBronze(tempTargetCustomer.contains(Membership.Bronze));
            binding.setForSilver(tempTargetCustomer.contains(Membership.Silver));
            binding.setForGold(tempTargetCustomer.contains(Membership.Gold));
            binding.setForDiamond(tempTargetCustomer.contains(Membership.Diamond));

        } else {
            binding.image.setVisibility(View.GONE);
            binding.setForBronze(false);
            binding.setForSilver(false);
            binding.setForGold(false);
            binding.setForDiamond(false);
        }
        binding.setPromotion(promotion);
    }

    private void setUpComponents() {
        initValues();
        setUpToolBar();
        setUpListeners();
    }

    private void setUpListeners() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        CalendarConstraints.Builder constraints = new CalendarConstraints.Builder();
        constraints.setValidator(DateValidatorPointForward.now());
        builder.setCalendarConstraints(constraints.build());

        binding.pickImageButton.setOnClickListener(v -> {
            if (v.getId() == R.id.pick_image_button) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });
        binding.expiryDate.setOnClickListener(v -> {
            MaterialDatePicker<Long> fromDatePicker = builder.setTitleText(getString(R.string.expiry_date)).build();
            fromDatePicker.addOnPositiveButtonClickListener(value -> {
                binding.expiryDate.setText(fromDatePicker.getHeaderText());
                try {
                    expiryDate = new SimpleDateFormat("MMM d, yyyy").parse(fromDatePicker.getHeaderText());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            fromDatePicker.show(getSupportFragmentManager(), "FROM_DATE");
        });

        setUpChipListeners();
    }

    private void setUpChipListeners() {
        binding.bronze.setOnClickListener(v -> binding.setForBronze(!binding.getForBronze()));
        binding.silver.setOnClickListener(v -> binding.setForSilver(!binding.getForSilver()));
        binding.gold.setOnClickListener(v -> binding.setForGold(!binding.getForGold()));
        binding.diamond.setOnClickListener(v -> binding.setForDiamond(!binding.getForDiamond()));
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

                Promotion promotion = binding.getPromotion();
                //TODO Setup promotion before sending here
                promotion.setTitle(binding.title.getEditText().getText().toString());
                promotion.setCode(binding.code.getEditText().getText().toString());
                promotion.setDescription(binding.description.getEditText().getText().toString());
                promotion.setValue(binding.value.getEditText().getText().toString());
                promotion.setTargetCustomer(new ArrayList<Membership>() {{
                    if (binding.getForBronze())
                        add(Membership.Bronze);
                    if (binding.getForSilver())
                        add(Membership.Silver);
                    if (binding.getForGold())
                        add(Membership.Gold);
                    if (binding.getForDiamond())
                        add(Membership.Diamond);
                }});

                promotion.setExpiryDate(expiryDate == null ? binding.getPromotion().getExpiryDate() : expiryDate);

                viewModel.onSubmitPromotion(promotion, imageData).addOnCompleteListener(task -> {
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

    private boolean validation() {
        boolean result = true;
        if (binding.code.getEditText().getText().toString().isEmpty()) {
            binding.code.setError("Code cannot be empty");
            binding.code.requestFocus();
            result = false;
        } else {
            binding.code.setError(null);
        }
        if (binding.title.getEditText().getText().toString().isEmpty()) {
            binding.title.setError("Title cannot be empty");
            binding.title.requestFocus();
            result = false;
        } else {
            binding.title.setError(null);
        }
        if (binding.description.getEditText().getText().toString().isEmpty()) {
            binding.description.setError("Description cannot be empty");
            binding.description.requestFocus();
            result = false;
        } else {
            binding.description.setError(null);
        }
        if (!validateImage()) {
            result = validateImage();
        }
        if (!validateValue()) {
            binding.value.setError("Invalid promotion value, value must be percentage or unsigned number");
            binding.value.requestFocus();
            result = validateValue();
        } else {
            binding.value.setError(null);
        }
        return result;

    }

    private boolean validateImage() {
        if (imageData == null && binding.getPromotion().getId().equals("")) {
            new AlertDialog.Builder(this).setTitle("No image").setMessage("You ha" +
                    "ve not choose any promotion image!").setPositiveButton("OKAY", (dialog, which) -> {
                dialog.dismiss();
            }).show();
            return false;
        }
        return true;
    }

    private boolean validateValue() {
        //We have 2 situations, one contains percentage, which must less than 100 and higher than 0
        // Another is that not contains percentage but must be unsigned int
        String value = binding.value.getEditText().getText().toString();
        if (value.contains("%")) {
            if (value.charAt(value.length() - 1) == '%') {
                try {
                    String expectedValue = value.substring(0, value.length() - 1);
                    int intValue = Integer.parseInt(expectedValue);
                    return intValue <= 100 && intValue >= 0;
                } catch (NumberFormatException e) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            try {
                int intValue = Integer.parseInt(value);
                return intValue >= 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
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
