package com.coffeehouse.the.viewModels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.CustomUser;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

public class AuthViewModel extends ViewModel {

    private final UserRepo userRepo = new UserRepo();

    public Task<CustomUser> signIn(String email, String password) {
        return userRepo.signIn(email, password);
    }

    public Task<CustomUser> signUp(String email, String name, String password, String phone, Date birthday) {
        return userRepo.createUser(email, password, new CustomUser(email, name, phone, birthday));
    }


    //Google Sign In
    public Task<CustomUser> handleGoogleSignIn(Task<GoogleSignInAccount> completedTask) throws ApiException, GeneralSecurityException, IOException {
        GoogleSignInAccount account = completedTask.getResult(ApiException.class);
        if (account != null) {
            return firebaseAuthWithGoogle(account);
        }
        return null;
    }

    private Task<CustomUser> firebaseAuthWithGoogle(GoogleSignInAccount account) {
        return userRepo.googleSignIn(account);
    }

    public Task<CustomUser> handleFacebookAccessToken(AccessToken token) {
        return userRepo.facebookSignIn(token);
    }

    //Sign out
    public Task<Void> signOut(Context context) {
        return userRepo.signOut(context);
    }

}
