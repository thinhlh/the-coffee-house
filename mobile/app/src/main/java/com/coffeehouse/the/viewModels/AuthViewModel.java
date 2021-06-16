package com.coffeehouse.the.viewModels;

import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.CustomUser;
import com.coffeehouse.the.services.UserRepo;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Date;

public class AuthViewModel extends ViewModel {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private final UserRepo userRepo = new UserRepo();

    public Task<CustomUser> signIn(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password).continueWithTask(task -> userRepo.fetchUser(task.getResult().getUser().getUid()));
    }

    public Task<CustomUser> signUp(String email, String name, String password, String phone, Date birthday) {
        return mAuth.createUserWithEmailAndPassword(email, password).continueWithTask(task -> {
            CustomUser user = new CustomUser(email, name, phone, birthday);
            return userRepo.createUser(task.getResult().getUser().getUid(), user);
        });
    }


    //Google Sign In
    public Task<CustomUser> handleGoogleSignIn(Task<GoogleSignInAccount> completedTask) throws ApiException {
        GoogleSignInAccount account=completedTask.getResult(ApiException.class);
        if(account!=null){
            return firebaseAuthWithGoogle(account);
        }
        return null;
    }

    private Task<CustomUser> firebaseAuthWithGoogle(GoogleSignInAccount account){
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        return mAuth.signInWithCredential(credential).continueWithTask(task -> userRepo.fetchUser(account));
    }

    // Facebook Sign-in
    public Task<CustomUser> handleFacebookAccessToken(AccessToken accessToken){
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        return mAuth.signInWithCredential(credential).continueWithTask(task -> userRepo.fetchUser(accessToken, mAuth.getCurrentUser()));
    }

    //Sign out
    public void signOut() {
        mAuth.signOut();
    }

}
