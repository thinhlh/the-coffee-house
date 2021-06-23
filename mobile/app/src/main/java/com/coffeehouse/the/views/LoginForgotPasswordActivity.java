package com.coffeehouse.the.views;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.coffeehouse.the.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LoginForgotPasswordActivity extends AppCompatActivity {
    private EditText email;
    private String _email;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_forgot_password_activity);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        email = findViewById(R.id.input_email_reset_pwd);
        (findViewById(R.id.btn_send_request_reset_pwd)).setOnClickListener(l -> {
            _email = email.getText().toString();
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

        (findViewById(R.id.close_forget_password)).setOnClickListener(l -> {
            finish();
        });
    }

    private boolean emailValidation() {
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
