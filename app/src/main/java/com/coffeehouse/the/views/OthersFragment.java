package com.coffeehouse.the.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.coffeehouse.the.services.CustomGoogleSignInClient;
import com.coffeehouse.the.services.UserRepo;
import com.coffeehouse.the.viewModels.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class OthersFragment extends Fragment  {

    private GoogleSignInClient mGoogleSignInClient;
    private final AuthViewModel authViewModel = new AuthViewModel();


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.others_fragment, container, false);

        return v;
    }



}
