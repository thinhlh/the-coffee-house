package com.coffeehouse.the.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.coffeehouse.the.services.CustomGoogleSignInClient;
import com.coffeehouse.the.viewModels.AuthViewModel;
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
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private final AuthViewModel authViewModel = new AuthViewModel();
    private TextInputLayout input_email, input_password;
    private String email, password;

    private static final int RC_SIGN_IN = 9000;
    private GoogleSignInClient mGoogleSignInClient;
    private final CallbackManager callbackManager = CallbackManager.Factory.create();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.login_fragment, container, false);

        input_email = (TextInputLayout) v.findViewById(R.id.text_input_email);
        input_password = (TextInputLayout) v.findViewById(R.id.text_input_password);
        ((Button) v.findViewById(R.id.login_button)).setOnClickListener(this);

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(v.getContext());
//        navigateToHome();

        //GOOGLE SIGN IN
        SignInButton googleSignInButton = (SignInButton) v.findViewById(R.id.google_sign_in);
        googleSignInButton.setSize(SignInButton.SIZE_WIDE);
        googleSignInButton.setOnClickListener(this);

        mGoogleSignInClient = CustomGoogleSignInClient.mGoogleSignInClient(v.getContext());

        //FACEBOOK SIGN IN
        LoginButton facebookLoginButton = (LoginButton) v.findViewById(R.id.facebook_login_button);
        facebookLoginButton.setReadPermissions("name", "email", "phone_number", "birthday");
        facebookLoginButton.setFragment(this);
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                authViewModel.handleFacebookAccessToken(loginResult.getAccessToken()).addOnCompleteListener(task -> {
                    Log.d("", "Login Complete");
                    if (task.isSuccessful()) {
                        Toast.makeText(v.getContext(), "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        navigateToHome();
                    } else {
                        Log.d("", Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("", "Error In Facebook Login: " + error.toString());
            }
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
            case R.id.facebook_login_button:
                break;
            case R.id.login_button:
                userPasswordSignIn();
                break;
        }
    }

    private void navigateToHome() {
        startActivity(new Intent(getContext(), HomeActivity.class));
    }


    //EVENTS HANDLING

    private void userPasswordSignIn() {

        email = Objects.requireNonNull(input_email.getEditText()).getText().toString().trim();
        password = Objects.requireNonNull(input_password.getEditText()).getText().toString().trim();

        if (validate()) {
            //loginProgress.setVisibility(View.VISIBLE);
            authViewModel.signIn(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this.getContext(), "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                            navigateToHome();
                        } else {
                            Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                        //loginProgress.setVisibility(View.GONE);
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
                        Toast.makeText(this.getContext(), "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        navigateToHome();
                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {

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
}