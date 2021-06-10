package com.coffeehouse.the.services;

import com.coffeehouse.the.models.CustomUser;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepo extends Fetching {

    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String uid;

    public static CustomUser user = null;


    private Task<Boolean> isRegistered(String uid) {
        return db.collection("users").document(uid).get().continueWith(task -> {
            if (task.isSuccessful()) {
                return task.getResult().exists();
            }
            return false;
        });

    }

    public static void fetchUser() {
        FirebaseFirestore.getInstance().collection("users")
                .document(mAuth.getCurrentUser().getUid()).get()
                .continueWith(task -> user = task.getResult().toObject(CustomUser.class));
    }


    public Task<CustomUser> signIn(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password).continueWithTask(task -> {
            if (task.isSuccessful()) {
                uid = task.getResult().getUser().getUid();
                return db.collection("users").document(uid).get().continueWith(task1 -> user = task1.getResult().toObject(CustomUser.class));
            }
            return null;
        });
    }

    public Task<CustomUser> createUser(String email, String password, CustomUser givenUser) {
        user = givenUser;
        return mAuth.createUserWithEmailAndPassword(email, password).continueWithTask(task -> {
            uid = task.getResult().getUser().getUid();
            return db.collection("users").document(uid).set(user).continueWith(task1 -> user);
        });
    }

    //User with some information for Google Sign In
    public Task<CustomUser> googleSignIn(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        return mAuth.signInWithCredential(credential).continueWithTask(task -> {
            uid = task.getResult().getUser().getUid();
            return isRegistered(uid).continueWithTask(isRegister -> {
                if (!isRegister.getResult()) {
                    CustomUser signUpUser = new CustomUser();
                    signUpUser.setName(account.getDisplayName());
                    signUpUser.setEmail(account.getEmail());
                    user = signUpUser;
                    return db.collection("users").document(uid).set(signUpUser).continueWith(task1 -> signUpUser);
                } else {
                    return db.collection("users").document(uid).get().continueWith(task1 ->
                            user = task1.getResult().toObject(CustomUser.class)
                    );
                }
            });
        });
    }

    public void signOut() {
        user = null;
        mAuth.signOut();
    }

    public Task<Void> toggleFavorite(String productId) {
        if (user.getFavoriteProducts().contains(productId)) {
            user.getFavoriteProducts().remove(productId);
            return db.collection("users").document(mAuth.getCurrentUser().getUid()).update("favoriteProducts", user.getFavoriteProducts());
        } else {
            user.getFavoriteProducts().add(productId);
            return db.collection("users").document(mAuth.getCurrentUser().getUid()).update("favoriteProducts", user.getFavoriteProducts());
        }
    }

    public Task<Void> updateUserPoint(Integer point) {
        return db.collection("users").document(mAuth.getCurrentUser().getUid()).update("point", (UserRepo.user.getPoint() + point));
    }

}
