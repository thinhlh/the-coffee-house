package com.coffeehouse.the.views.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.ProfitActivityBinding;
import com.coffeehouse.the.utils.commons.Constants;
import com.coffeehouse.the.utils.helper.WaitingHandler;
import com.coffeehouse.the.viewModels.admin.ProfitViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class ProfitActivity extends AppCompatActivity implements WaitingHandler {
    private ProfitActivityBinding binding;
    private ProfitViewModel viewModel;
    private Date fromDate;
    private Date toDate;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.profit_activity);
        viewModel = new ViewModelProvider(this).get(ProfitViewModel.class);
        viewModel.setContext(this);
        getSupportActionBar().hide();

        setUpListeners();
        super.onCreate(savedInstanceState);
    }

    private void setUpListeners() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        CalendarConstraints.Builder constraints = new CalendarConstraints.Builder();
        constraints.setValidator(DateValidatorPointBackward.now());
        builder.setCalendarConstraints(constraints.build());

        binding.fromDate.getEditText().setOnClickListener(v -> {
            MaterialDatePicker<Long> fromDatePicker = builder.setTitleText(getString(R.string.from_date)).build();
            fromDatePicker.addOnPositiveButtonClickListener(value -> {
                binding.fromDate.getEditText().setText(fromDatePicker.getHeaderText());
                try {
                    fromDate = new SimpleDateFormat("MMM d, yyyy").parse(fromDatePicker.getHeaderText());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            fromDatePicker.show(getSupportFragmentManager(), "FROM_DATE");

        });
        binding.toDate.getEditText().setOnClickListener(v -> {
            MaterialDatePicker<Long> toDatePicker = builder.setTitleText(getString(R.string.to_date)).build();
            toDatePicker.addOnPositiveButtonClickListener(value -> {
                binding.toDate.getEditText().setText(toDatePicker.getHeaderText());
                try {
                    toDate = new SimpleDateFormat("MMM dd, yyyy").parse(toDatePicker.getHeaderText());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            toDatePicker.show(getSupportFragmentManager(), "TO_DATE");
        });

        binding.button.setOnClickListener(v -> {
            if (validate()) {
                invokeWaiting();
                viewModel.getProfit(fromDate, toDate, response -> {
                    dispatchWaiting();
                    try {
                        binding.totalProfit.setText(Constants.currencyFormatter.format(response.getInt("total")));
                        binding.totalOrder.setText(String.valueOf(response.getInt("numberOfOrders")));
                        binding.totalDelivered.setText(String.valueOf(response.getInt("deliveredOrders")));
                        binding.totalPending.setText(String.valueOf(response.getInt("numberOfOrders") - response.getInt("deliveredOrders")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    dispatchWaiting();
                    Toast.makeText(this, "Something happened: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private boolean validate() {
        if (fromDate == null && toDate == null) {
            binding.fromDate.setError("You must specify the from date value");
            binding.toDate.setError("You must specify the to date value");
            return false;
        } else if (fromDate == null) {
            binding.fromDate.setError("You must specify the from date value");
            return false;
        } else if (toDate == null) {
            binding.toDate.setError("You must specify the to date value");
            return false;
        } else if (fromDate.after(toDate)) {
            binding.fromDate.setError("Invalid range");
            binding.toDate.setError("Invalid range");
            return false;
        } else {
            binding.fromDate.setError(null);
            binding.toDate.setError(null);
            return true;
        }
    }

    @Override
    public void invokeWaiting() {
        binding.content.setVisibility(View.GONE);
        binding.progressCircular.setVisibility(View.VISIBLE);
    }

    @Override
    public void dispatchWaiting() {
        binding.progressCircular.setVisibility(View.GONE);
        binding.content.setVisibility(View.VISIBLE);
    }
}
