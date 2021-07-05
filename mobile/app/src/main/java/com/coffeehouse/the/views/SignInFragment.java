package com.coffeehouse.the.views;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.LoginFragmentBinding;
import com.coffeehouse.the.models.CustomUser;
import com.coffeehouse.the.services.local.FCMService;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.coffeehouse.the.utils.helper.CustomGoogleSignInClient;
import com.coffeehouse.the.utils.helper.WaitingHandler;
import com.coffeehouse.the.viewModels.AuthViewModel;
import com.coffeehouse.the.views.admin.AdminHomeActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SignInFragment extends Fragment implements View.OnClickListener, WaitingHandler {

    private static final int RC_SIGN_IN = 9000;
    private LoginFragmentBinding binding;
    private AuthViewModel viewModel;

    private GoogleSignInClient googleSignInClient;

    private CallbackManager facebookCallBackManager;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false);
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        setUpComponents();
        return binding.getRoot();
    }

    private void setUpComponents() {
        setUpListeners();
        setUpGoogleSignIn();
        setUpFacebookSignIn();
    }

    private void setUpListeners() {
        binding.loginButton.setOnClickListener(this);
        binding.googleSignIn.setOnClickListener(this);
        binding.btnForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.loginButton) {
            signInByEmailAndPassword();
        } else if (v == binding.btnForgotPassword) {
            startActivity(new Intent(getContext(), LoginForgotPasswordActivity.class));
        } else if (v == binding.googleSignIn) {
            signInByGoogle();
        }
    }

    private void navigateToHome(boolean isAdmin) {
        if (UserRepo.user.getSubscribeToNotifications()) {
            FirebaseMessaging.getInstance().subscribeToTopic(FCMService.TOPIC)
                    .addOnCompleteListener(
                            task -> startActivity(
                                    new Intent(getContext(),
                                            isAdmin ?
                                                    AdminHomeActivity.class :
                                                    HomeActivity.class)
                            ));
        } else {
            startActivity(new Intent(getContext(),
                    isAdmin ?
                            AdminHomeActivity.class :
                            HomeActivity.class));
        }
    }

    private void setUpGoogleSignIn() {
        binding.googleSignIn.setSize(SignInButton.SIZE_WIDE);
        googleSignInClient = CustomGoogleSignInClient.mGoogleSignInClient(binding.getRoot().getContext());

    }

    private void setUpFacebookSignIn() {
        facebookCallBackManager = CallbackManager.Factory.create();

        LoginButton facebookLoginButton = binding.facebookLoginButton;
        facebookLoginButton.setReadPermissions("email", "public_profile");
        facebookLoginButton.setFragment(this);

        facebookLoginButton.registerCallback(facebookCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                invokeWaiting();
                Log.d("FACEBOOK_SIGN_IN", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult).addOnCompleteListener(signInTask -> {
                    if (signInTask.isSuccessful()) {
                        Toast.makeText(getContext(), "Welcome " + signInTask.getResult().getName(), Toast.LENGTH_SHORT).show();
                        navigateToHome(signInTask.getResult().getAdmin());
                    } else {
                        Toast.makeText(getContext(), "Error occurred: " + signInTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        dispatchWaiting();
                    }
                });
            }

            @Override
            public void onCancel() {
                Log.d("FACEBOOK_SIGN_IN", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FACEBOOK_SIGN_IN", "facebook:onError", error);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallBackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            invokeWaiting();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                viewModel.handleGoogleSignIn(task).addOnCompleteListener(signInTask -> {
                    if (signInTask.isSuccessful()) {
                        Toast.makeText(this.getContext(), "Welcome " + signInTask.getResult().getName(), Toast.LENGTH_SHORT).show();
                        navigateToHome(signInTask.getResult().getAdmin());
                    }
                });
            } catch (ApiException | GeneralSecurityException | IOException e) {
                e.printStackTrace();
                dispatchWaiting();
            }
        }
    }

    private void signInByEmailAndPassword() {
        String email = binding.textInputEmail.getEditText().getText().toString();
        String password = binding.textInputPassword.getEditText().getText().toString();
        if (validate()) {
            invokeWaiting();
            viewModel.signIn(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                binding.textInputEmail.setError(null);
                                binding.textInputPassword.setError(null);
                                Toast.makeText(this.getContext(), "Welcome " + task.getResult().getName(), Toast.LENGTH_SHORT).show();
                                navigateToHome(task.getResult().getAdmin());
                            } else {
                                dispatchWaiting();
                                new AlertDialog.Builder(getContext())
                                        .setMessage("Wrong email or password, please try again ")
                                        .setTitle("Autentication Error")
                                        .setPositiveButton("Okay", (dialog, which) ->
                                                dialog.dismiss()).show();
                            }
                        } else {
                            dispatchWaiting();
                        }
                    });
        }
    }

    private void signInByGoogle() {
        startActivityForResult(googleSignInClient.getSignInIntent(), RC_SIGN_IN);
    }

    private Task<CustomUser> handleFacebookAccessToken(LoginResult loginResult) {
        return viewModel.handleFacebookAccessToken(loginResult.getAccessToken());
    }

    private boolean validate() {
        return passwordValidation() && emailValidation();
    }

    private boolean emailValidation() {
        TextInputLayout email = binding.textInputEmail;
        if (email.getEditText().getText().toString().isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getEditText().getText()).matches()) {
            email.setError("Invalid email");
            email.requestFocus();
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean passwordValidation() {
        TextInputLayout password = binding.textInputPassword;

        if (password.getEditText().getText().toString().isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return false;
        } else if (password.getEditText().getText().toString().length() < 6) {
            password.setError("Password required at least 6 characters");
            password.requestFocus();
            return false;
        } else {
            password.setError(null);
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
