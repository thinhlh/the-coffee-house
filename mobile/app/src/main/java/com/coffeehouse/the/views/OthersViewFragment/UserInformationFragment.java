package com.coffeehouse.the.views.OthersViewFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.PersonalinfoFragmentBinding;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserInformationFragment extends Fragment implements View.OnClickListener {
    private UserRepo userRepo = new UserRepo();
    private TextInputEditText txtName, txtPhone, txtBirthday;
    private TextView txtEmail;
    private Date birthDate;
    private PersonalinfoFragmentBinding personalinfoFragmentBinding;

    public UserInformationFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        personalinfoFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.personalinfo_fragment, container, false);
        View v = personalinfoFragmentBinding.getRoot();

        init();
        setVariable();

        //Create Date Picker
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select birthday date");
        builder.setTheme(R.style.MaterialCalendarTheme);
        MaterialDatePicker<Long> materialDatePicker = builder.build();
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            String datePicked = materialDatePicker.getHeaderText();
            personalinfoFragmentBinding.textInputBirthday.setText(datePicked);
            try {
                birthDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(datePicked);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        personalinfoFragmentBinding.textInputBirthday.setOnClickListener(v1 -> materialDatePicker.show(getFragmentManager(), "DATE_PICKER"));
        personalinfoFragmentBinding.textInputBirthday.setOnFocusChangeListener((v12, hasFocus) -> {
            if (hasFocus) {
                materialDatePicker.show(getFragmentManager(), "DATE_PICKER");
            }
        });
        //End Date Picker Region

        personalinfoFragmentBinding.closePersonalInformation.setOnClickListener(this::onClick);
        personalinfoFragmentBinding.updateUserInfoBtn.setOnClickListener(this::onClick);
        personalinfoFragmentBinding.updateUserPasswordBtn.setOnClickListener(l -> {
            startActivity(new Intent(getContext(), UpdatePasswordActivity.class));
        });

        return v;
    }

    private void setVariable() {
        txtName.setText(UserRepo.user.getName());
        txtPhone.setText(UserRepo.user.getPhoneNumber());
        txtEmail.setText(UserRepo.user.getEmail());
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy");
        txtBirthday.setText(df.format(UserRepo.user.getBirthday()));
        birthDate = UserRepo.user.getBirthday();
    }

    private void init() {
        txtName = personalinfoFragmentBinding.textInputName;
        txtPhone = personalinfoFragmentBinding.textInputPhone;
        txtEmail = personalinfoFragmentBinding.textInputEmail;
        txtBirthday = personalinfoFragmentBinding.textInputBirthday;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()) {
            case R.id.close_personal_information:
                fragment = new OthersFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
            case R.id.update_user_info_btn:
                UserRepo userRepo = new UserRepo();
                userRepo.updateUserInfo(txtName.getText().toString(), txtPhone.getText().toString(), txtEmail.getText().toString(), birthDate);
                Toast.makeText(getContext(), "Update User Information Success", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
