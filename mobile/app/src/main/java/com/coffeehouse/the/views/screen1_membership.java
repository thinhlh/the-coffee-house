package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.FragmentScreen1MembershipBinding;
import com.coffeehouse.the.databinding.MembershipFragmentBinding;
import com.coffeehouse.the.services.repositories.UserRepo;

import org.jetbrains.annotations.NotNull;

public class screen1_membership extends Fragment implements View.OnClickListener {
    private FragmentScreen1MembershipBinding binding;
    public screen1_membership() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_screen1__membership, container, false);
        binding.setUser(UserRepo.user);
        binding.cardviewChangepromotion.setOnClickListener(this::onClick);
        return binding.getRoot();
    }



    @Override
    public void onClick(View v) {
             switch (v.getId()){
                 case R.id.cardview_changepromotion:

                     break;
             }
    }
}
