package com.coffeehouse.the.views.OthersViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.PersonalinfoFragmentBinding;
import com.coffeehouse.the.services.UserRepo;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class UserInformationFragment extends Fragment implements View.OnClickListener {
    private UserRepo userRepo = new UserRepo();
    private TextInputEditText txtName, txtPhone, txtEmail, txtBirthday;
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

        personalinfoFragmentBinding.closePersonalInformation.setOnClickListener(this::onClick);
        personalinfoFragmentBinding.updateUserInfoBtn.setOnClickListener(this::onClick);

        return v;
    }

    private void setVariable() {
        txtName.setText(UserRepo.user.getName());
        txtPhone.setText(UserRepo.user.getPhoneNumber());
        txtEmail.setText(UserRepo.user.getEmail());
        txtBirthday.setText(UserRepo.user.birthdayString());
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
                userRepo.updateUserInfo(txtName.getText().toString(), txtPhone.getText().toString(), birthDate);
                Toast.makeText(getContext(), "Updated User Information", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
