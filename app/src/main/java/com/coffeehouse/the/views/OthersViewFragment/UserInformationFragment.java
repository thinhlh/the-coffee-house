package com.coffeehouse.the.views.OthersViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.PersonalinfoFragmentBinding;
import com.coffeehouse.the.services.UserRepo;
import com.coffeehouse.the.viewModels.UpdateUserInforViewModel;

import org.jetbrains.annotations.NotNull;

public class UserInformationFragment extends Fragment implements View.OnClickListener {
    private UserRepo userRepo = new UserRepo();
    private UpdateUserInforViewModel updateUserInforViewModel;
    public UserInformationFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        PersonalinfoFragmentBinding personalinfoFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.personalinfo_fragment, container, false);
        View v = personalinfoFragmentBinding.getRoot();


        updateUserInforViewModel = new ViewModelProvider(this).get(UpdateUserInforViewModel.class);
        personalinfoFragmentBinding.setLifecycleOwner(this);
        personalinfoFragmentBinding.setUserInfoViewModel(updateUserInforViewModel);


        v.findViewById(R.id.close_personal_information).setOnClickListener(this::onClick);
        return v;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()) {
            case R.id.close_personal_information:
                fragment = new OthersFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
        }
    }
}
