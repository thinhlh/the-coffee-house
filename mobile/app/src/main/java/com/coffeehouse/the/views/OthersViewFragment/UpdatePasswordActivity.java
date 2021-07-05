package com.coffeehouse.the.views.OthersViewFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.ActivityUpdatePasswordBinding;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.coffeehouse.the.viewModels.AuthViewModel;
import com.coffeehouse.the.views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class UpdatePasswordActivity extends AppCompatActivity {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private ActivityUpdatePasswordBinding binding;
    private String _oldPassword = "";
    private String _newPassword = "";
    private String _confirmNewPassword = "";
    private AuthViewModel authViewModel = new AuthViewModel();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_password);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setUpListeners();
    }

    private void setUpListeners() {
        binding.appBar.setNavigationOnClickListener(v -> this.finish());
        binding.btnSendRequestUpdatePwd.setOnClickListener(l -> {
            if (validation()) {
                updatePassword();
            }
        });
    }

    private void updatePassword() {
        FirebaseUser user = auth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(UserRepo.user.getEmail(), _oldPassword);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(_newPassword).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Đăng nhập với mật khẩu mới", Toast.LENGTH_LONG).show();
                            authViewModel.signOut(getApplicationContext()).addOnCompleteListener(task2 -> {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            });
                        } else {
                            String e = task1.getException().getMessage();
                            Toast.makeText(getApplicationContext(), "Update Failed cause by " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    String e = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validation() {
        return validateOldPassword() && validateNewPassword() && validateConfirmOldPassword();
    }

    private boolean validateOldPassword() {
        _oldPassword = binding.inputOldPwd.getEditText().getText().toString();
        EditText oldPassword = binding.inputOldPwd.getEditText();
        if (_oldPassword.isEmpty()) {
            oldPassword.setError("Old Password is required");
            oldPassword.requestFocus();
            return false;
        } else if (_oldPassword.length() < 6) {
            oldPassword.setError("Password required at least 6 characters");
            oldPassword.requestFocus();
            return false;
        } else {
            oldPassword.setError(null);
            return true;
        }
    }

    private boolean validateNewPassword() {
        _newPassword = binding.inputNewPwd.getEditText().getText().toString();
        EditText newPassword = binding.inputNewPwd.getEditText();
        if (_newPassword.isEmpty()) {
            newPassword.setError("New Password is required");
            newPassword.requestFocus();
            return false;
        } else if (_newPassword.length() < 6) {
            newPassword.setError("Password required at least 6 characters");
            newPassword.requestFocus();
            return false;
        } else {
            newPassword.setError(null);
            return true;
        }
    }

    private boolean validateConfirmOldPassword() {
        _confirmNewPassword = binding.inputConfirmNewPwd.getEditText().getText().toString();
        EditText confirmNewPassword = binding.inputConfirmNewPwd.getEditText();
        if (_confirmNewPassword.isEmpty()) {
            confirmNewPassword.setError("Confirm new Password is required");
            confirmNewPassword.requestFocus();
            return false;
        } else if (_confirmNewPassword.length() < 6) {
            confirmNewPassword.setError("Password required at least 6 characters");
            confirmNewPassword.requestFocus();
            return false;
        } else if (!_confirmNewPassword.equals(_newPassword)) {
            confirmNewPassword.setError("Confirm new Password does not match new Password");
            confirmNewPassword.requestFocus();
            return false;
        } else {
            confirmNewPassword.setError(null);
            return true;
        }
    }
}
