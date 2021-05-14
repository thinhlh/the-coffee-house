package com.coffeehouse.the.views;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.coffeehouse.the.R;
import com.coffeehouse.the.services.CustomGoogleSignInClient;
import com.coffeehouse.the.viewModels.AuthViewModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.WebDialog;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.Executor;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private final AuthViewModel authViewModel = new AuthViewModel();
    private TextInputLayout input_email, input_password;
    private String email, password;

    private static final int RC_SIGN_IN = 9000;
    private GoogleSignInClient mGoogleSignInClient;

    private CallbackManager mCallbackManager;
    private LoginButton loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.login_fragment, container, false);
        FacebookSdk.sdkInitialize(getContext());

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
            case R.id.facebook_sign_in:
                facebookSignIn(v);
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                authViewModel.handleGoogleSignIn(task).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        navigateToHome();
                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }



    private void facebookSignIn(View v) {
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = v.findViewById(R.id.facebook_sign_in);
        loginButton.setReadPermissions("name", "email", "phone_number", "birthday");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FACEBOOK", "facebook:onSuccess:" + loginResult);
                authViewModel.handleFacebookAccessToken(loginResult.getAccessToken()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        navigateToHome();
                    } else {
                        Log.e("FACEBOOK SIGN IN FAILED", "FAILLLLLL");
                    }
                });

                //handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("FACEBOOK", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FACEBOOK", "facebook:onError", error);
            }
        });
    }

//    private void handleFacebookAccessToken(AccessToken token) {
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        FirebaseAuth.getInstance().signInWithCredential(credential)
//                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            navigateToHome();
//                        } else {
//                        }
//                    }
//                });
//    }


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