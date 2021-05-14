//package com.coffeehouse.the.views;
//
//import android.app.DatePickerDialog;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.coffeehouse.the.R;
//import com.coffeehouse.the.viewModels.AuthViewModel;
//import com.google.android.material.button.MaterialButton;
//import com.google.android.material.textfield.TextInputLayout;
//
//import java.sql.Date;
//import java.time.Instant;
//
//public class SignUpFragment extends Fragment {
//
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    private TextView mDisplayDate;
//
//    private AuthViewModel authViewModel=new AuthViewModel();
//
//    private DatePickerDialog.OnDateSetListener mDateSetListener;
//
//    public SignUpFragment() {
//    }
//
//    public static SignUpFragment newInstance(String param1, String param2) {
//        SignUpFragment fragment = new SignUpFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View v = inflater.inflate(R.layout.fragment_signup, container, false);
//
//        //VALIDATION HERE
//        ((MaterialButton) v.findViewById(R.id.sign_up_button)).setOnClickListener(button -> {
//
//            String name = ((TextInputLayout) v.findViewById(R.id.name_text_input)).getEditText().getText().toString().trim();
//            String email = ((TextInputLayout) v.findViewById(R.id.email_text_input)).getEditText().getText().toString().trim();
//            String phone = ((TextInputLayout) v.findViewById(R.id.phone_text_input)).getEditText().getText().toString().trim();
//            String password = ((TextInputLayout) v.findViewById(R.id.password_text_input)).getEditText().getText().toString().trim();
//
//            authViewModel.signUp(email, name, password, phone, Date.from(Instant.now())).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    startActivity(new Intent(getContext(), HomeActivity.class));
//                } else {
//                    Toast.makeText(getContext(), "Account incorrect", Toast.LENGTH_SHORT).show();
//                }
//            });
//        });
//
//        return v;
//
//
//    }
//}
package com.coffeehouse.the.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.coffeehouse.the.viewModels.AuthViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    TextInputLayout input_name, input_email, input_phone, input_password, input_cf_password, mDisplayDate;
    String name, email, phone, password, cf_password, birthday;
    Date birthDate;

    private final AuthViewModel authViewModel = new AuthViewModel();

    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.signup_fragment, container, false);
        init(v);

        //CREATE DATE_PICKER
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select birthday date");
        builder.setTheme(R.style.MaterialCalendarTheme);
        MaterialDatePicker<Long> materialDatePicker = builder.build();
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            String datePicked = materialDatePicker.getHeaderText();
            Objects.requireNonNull(mDisplayDate.getEditText()).setText(datePicked);
            try {
                birthDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(datePicked);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        //SIGN UP
        ((MaterialButton) v.findViewById(R.id.sign_up_button)).setOnClickListener(button -> {
            getVariable(v);
            if (validation()) {
                signUp();
            }
        });


        mDisplayDate.getEditText().setOnClickListener(v1 -> materialDatePicker.show(getFragmentManager(), "DATE_PICKER"));
        mDisplayDate.getEditText().setOnFocusChangeListener((v12, hasFocus) -> {
            if (hasFocus) {
                materialDatePicker.show(getFragmentManager(), "DATE_PICKER");
            }
        });

        return v;
    }

    private void getVariable(View v) {
        name = Objects.requireNonNull(input_name.getEditText()).getText().toString().trim();
        email = Objects.requireNonNull(input_email.getEditText()).getText().toString().trim();
        phone = Objects.requireNonNull(input_phone.getEditText()).getText().toString().trim();
        password = Objects.requireNonNull(input_password.getEditText()).getText().toString().trim();
        cf_password = Objects.requireNonNull(input_cf_password.getEditText()).getText().toString().trim();
        birthday = Objects.requireNonNull(mDisplayDate.getEditText()).getText().toString().trim();
    }

    private void init(View v) {
        input_name = (TextInputLayout) v.findViewById(R.id.name_text_input);
        input_email = (TextInputLayout) v.findViewById(R.id.email_text_input);
        input_phone = (TextInputLayout) v.findViewById(R.id.phone_text_input);
        input_password = (TextInputLayout) v.findViewById(R.id.password_text_input);
        input_cf_password = (TextInputLayout) v.findViewById(R.id.confirm_password_text_input);
        mDisplayDate = (TextInputLayout) v.findViewById(R.id.birthday);
    }

    private void signUp() {
        authViewModel.signUp(email, name, password, phone, birthDate).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(getContext(), HomeActivity.class));
            } else {
                Toast.makeText(getContext(), "Account incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation() {
        return (nameValidation() && emailValidation() && phoneValidation() && birthdayValidation() && passwordValidation() && cfPasswordValidation());
    }

    private boolean birthdayValidation() {
        if (birthday.isEmpty()) {
            mDisplayDate.setError("Birthday is required");
            return false;
        } else {
            mDisplayDate.setError(null);
            return true;
        }
    }

    private boolean emailValidation() {
        if (email.isEmpty()) {
            input_email.setError("Email is required");
            input_email.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("Invalid Email");
            input_email.requestFocus();
            return false;
        } else {
            input_email.setError(null);
            return true;
        }
    }

    private boolean passwordValidation() {
        if (password.isEmpty()) {
            input_password.setError("Password is required");
            input_password.requestFocus();
            return false;
        } else if (password.length() < 6) {
            input_password.setError("Password required at least 6 characters");
            input_password.requestFocus();
            return false;
        } else {
            input_password.setError(null);
            return true;
        }
    }

    private boolean cfPasswordValidation() {
        if (cf_password.isEmpty()) {
            input_cf_password.setError("Confirm Password is required");
            input_cf_password.requestFocus();
            return false;
        } else if (!cf_password.equals(password)) {
            input_cf_password.setError("Confirm Password does not match");
            input_cf_password.requestFocus();
            return false;
        } else {
            input_cf_password.setError(null);
            return true;
        }
    }

    private boolean nameValidation() {
        if (name.isEmpty()) {
            input_name.setError("Name is required");
            input_name.requestFocus();
            return false;
        } else {
            input_name.setError(null);
            return true;
        }
    }

    private boolean phoneValidation() {
        if (phone.isEmpty()) {
            input_phone.setError("Phone number is required");
            input_phone.requestFocus();
            return false;
        } else if (phone.length() < 8) {
            input_phone.setError("Invalid Phone number");
            input_phone.requestFocus();
            return false;
        } else {
            input_phone.setError(null);
            return true;
        }
    }

    @Override
    public void onClick(View v) {

    }
}