package com.coffeehouse.the.utils.helper;

import android.annotation.SuppressLint;
import android.content.Context;

import com.coffeehouse.the.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;

public abstract class CustomGoogleSignInClient {

    public static GoogleSignInClient mGoogleSignInClient(Context context) {
        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getResources().getString(R.string.google_default_web_client_id,"string",context.getPackageName()))
                .requestEmail()
                .requestProfile()
                .requestScopes(new Scope("https://www.googleapis.com/auth/user.birthday.read"))
                .build();

        return GoogleSignIn.getClient(context, gso);
    }
}
