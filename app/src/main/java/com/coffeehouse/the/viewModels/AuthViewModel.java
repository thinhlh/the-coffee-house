package com.coffeehouse.the.viewModels;

import androidx.lifecycle.ViewModel;

import com.coffeehouse.the.models.CustomUser;
import com.coffeehouse.the.services.UserRepo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class AuthViewModel extends ViewModel {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private UserRepo userRepo = new UserRepo();

    public Task<CustomUser> signIn(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password).continueWithTask(task -> userRepo.fetchUser(task.getResult().getUser().getUid()));
    }

    public Task<CustomUser> signUp(String email, String name, String password, String phone, Date birthday) {
        return mAuth.createUserWithEmailAndPassword(email, password).continueWithTask(task -> {
            CustomUser user = new CustomUser(email, name, phone, birthday);
            return userRepo.createUser(task.getResult().getUser().getUid(), user);
        });
    }

    public void signOut() {
        mAuth.signOut();
    }

}
