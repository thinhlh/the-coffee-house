package com.coffeehouse.the.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.LoginFragmentBinding;
import com.coffeehouse.the.utils.helper.CustomGoogleSignInClient;
import com.coffeehouse.the.services.local.FCMService;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.coffeehouse.the.utils.helper.WaitingHandler;
import com.coffeehouse.the.viewModels.AuthViewModel;
import com.coffeehouse.the.views.admin.AdminHomeActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;


public class LoginFragment extends Fragment implements View.OnClickListener, WaitingHandler {
    private LoginFragmentBinding binding;
    private final AuthViewModel authViewModel = new AuthViewModel();
    private TextInputLayout input_email, input_password;
    private String email, password;

    private static final int RC_SIGN_IN = 9000;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        callbackManager = CallbackManager.Factory.create();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false);
        View v = inflater.inflate(R.layout.login_fragment, container, false);

        input_email = v.findViewById(R.id.text_input_email);
        input_password = v.findViewById(R.id.text_input_password);
        v.findViewById(R.id.login_button).setOnClickListener(this);

        //GOOGLE SIGN IN
        SignInButton googleSignInButton = v.findViewById(R.id.google_sign_in);
        googleSignInButton.setSize(SignInButton.SIZE_WIDE);
        googleSignInButton.setOnClickListener(this);

        mGoogleSignInClient = CustomGoogleSignInClient.mGoogleSignInClient(v.getContext());

        //FACEBOOK SIGN IN
        LoginButton facebookLoginButton = v.findViewById(R.id.facebook_login_button);
        facebookLoginButton.setReadPermissions("email", "public_profile");
        facebookLoginButton.setFragment(this);

        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                authViewModel.handleFacebookAccessToken(loginResult.getAccessToken())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("FBLOGIN", "SUCCESS");
                                Toast.makeText(v.getContext(), "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                                navigateToHome(
                                        task.getResult().getAdmin()
                                );
                            } else {
                                Log.d("", Objects.requireNonNull(task.getException()).getMessage());
                            }
                        });
            }

            @Override
            public void onCancel() {
                Log.d("", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("", "Error In Facebook Login: " + error.toString());
            }
        });

        //Forgot password
        v.findViewById(R.id.btn_forgot_password).setOnClickListener(l -> {
            startActivity(new Intent(getContext(), LoginForgotPasswordActivity.class));
        });

        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_sign_in:
                googleSignIn();
                break;
            case R.id.login_button:
                userPasswordSignIn();
                break;
        }
    }

    private void navigateToHome(boolean isAdmin) {
        if (UserRepo.user.getSubscribeToNotifications()) {
            FirebaseMessaging.getInstance().subscribeToTopic(FCMService.TOPIC).addOnCompleteListener(task -> {
                startActivity(new Intent(getContext(), isAdmin ? AdminHomeActivity.class : HomeActivity.class));
            });
        } else {
            startActivity(new Intent(getContext(), isAdmin ? AdminHomeActivity.class : HomeActivity.class));
        }
    }


    //EVENTS HANDLING
    private void userPasswordSignIn() {

        email = Objects.requireNonNull(input_email.getEditText()).getText().toString().trim();
        password = Objects.requireNonNull(input_password.getEditText()).getText().toString().trim();

        if (validate()) {
            invokeWaiting();
            authViewModel.signIn(email, password)
                    .addOnCompleteListener(task -> {
                        dispatchWaiting();
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                input_email.setError(null);
                                input_password.setError(null);
                                Toast.makeText(this.getContext(), "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                navigateToHome(task.getResult().getAdmin());
                            } else {
                                new AlertDialog.Builder(getContext())
                                        .setMessage("Wrong email or password, please try again ")
                                        .setTitle("Autentication Error").setPositiveButton("Okay", (dialog, which) -> {
                                    dialog.dismiss();
                                }).show();
                            }
                        } else {
                            Toast.makeText(this.getContext(), "Error occurred: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                authViewModel.handleGoogleSignIn(task).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        navigateToHome(task1.getResult().getAdmin());
                        Toast.makeText(this.getContext(), "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (ApiException | GeneralSecurityException | IOException e) {
                e.printStackTrace();
            }
        }
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

    @Override
    public void invokeWaiting() {
        binding.content.setVisibility(View.GONE);
        binding.progressCircular.setVisibility(View.VISIBLE);
    }

    @Override
    public void dispatchWaiting() {
        binding.content.setVisibility(View.VISIBLE);
        binding.progressCircular.setVisibility(View.GONE);

    }
}