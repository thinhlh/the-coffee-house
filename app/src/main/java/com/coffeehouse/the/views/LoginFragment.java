package com.coffeehouse.the.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.coffeehouse.the.R;
import com.coffeehouse.the.viewModels.AuthViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class LoginFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    AuthViewModel authViewModel = new AuthViewModel();
    TextInputLayout input_email, input_password;
    String email, password;


    public LoginFragment() {
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        input_email = (TextInputLayout) v.findViewById(R.id.text_input_email);
        input_password = (TextInputLayout) v.findViewById(R.id.text_input_password);
        Button btn_login = (Button) v.findViewById(R.id.login_button);
        ProgressBar loginProgress = (ProgressBar) v.findViewById(R.id.login_progress);

        btn_login.setOnClickListener(loginButton -> {
            email = Objects.requireNonNull(input_email.getEditText()).getText().toString().trim();
            password = Objects.requireNonNull(input_password.getEditText()).getText().toString().trim();

            if (validate()) {
                loginProgress.setVisibility(View.VISIBLE);
                signIn();
                loginProgress.setVisibility(View.GONE);
            }
        });
        return v;
    }

    private boolean validate() {
        return emailValidation() && passwordValidation();
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

    private void signIn() {

        authViewModel.signIn(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getContext(), HomeActivity.class));
                    } else {
                        Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}