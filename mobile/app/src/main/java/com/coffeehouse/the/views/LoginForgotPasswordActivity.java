package com.coffeehouse.the.views;

import android.os.Bundle;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.LoginForgotPasswordActivityBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginForgotPasswordActivity extends AppCompatActivity {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private LoginForgotPasswordActivityBinding binding;
    private String _email = "";

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login_forgot_password_activity);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setUpListeners();
    }

    private void setUpListeners() {
        binding.appBar.setNavigationOnClickListener(v -> this.finish());
        binding.btnSendRequestResetPwd.setOnClickListener(v -> {
            if (emailValidation()) {
                auth.sendPasswordResetEmail(_email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Checkout your email to reset password...", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String e = task.getException().getMessage();
                        Toast.makeText(getApplicationContext(), "Some thing happened! " + e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean emailValidation() {
        _email = binding.inputEmailResetPwd.getEditText().getText().toString();
        EditText email = binding.inputEmailResetPwd.getEditText();
        if (_email.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
            email.setError("Invalid Email");
            email.requestFocus();
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
}
