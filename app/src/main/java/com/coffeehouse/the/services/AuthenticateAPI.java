package com.coffeehouse.the.services;

import com.coffeehouse.the.models.CustomUser;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

/*
* Authenticate and register user Status for Firebase Auth, Google Sign In and Facebook Sign In
* */
public interface AuthenticateAPI {
    Task<Void> signIn(String email, String password);
    Task<Void> createUser(String email, String password);
    Task<Void> signInWithGoogle(GoogleSignInAccount account);
    Task<Void> signInWithFacebook(AccessToken token);
}
