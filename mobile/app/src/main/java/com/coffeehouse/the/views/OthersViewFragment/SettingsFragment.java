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
import com.coffeehouse.the.databinding.SettingFragmentBinding;
import com.coffeehouse.the.services.repositories.UserRepo;
import com.coffeehouse.the.viewModels.SettingsViewModel;

import org.jetbrains.annotations.NotNull;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    private SettingFragmentBinding settingFragmentBinding;
    private SettingsViewModel viewModel;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        settingFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment, container, false);
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        initListeners();
        settingFragmentBinding.notificationSwitch.setChecked(UserRepo.user.getSubscribeToNotifications());

        return settingFragmentBinding.getRoot();
    }

    private void initListeners() {
        settingFragmentBinding.closeSettings.setOnClickListener(this);
        settingFragmentBinding.aboutUs.setOnClickListener(this);
        settingFragmentBinding.notificationSwitch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()) {
            case R.id.close_settings:
                fragment = new OthersFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
            case R.id.about_us:
                fragment = new AboutUsFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
            case R.id.notification_switch:
                UserRepo.user.setSubscribeToNotifications(!UserRepo.user.getSubscribeToNotifications());
                viewModel.changeSubscriptionStatus().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        settingFragmentBinding.notificationSwitch.setChecked(UserRepo.user.getSubscribeToNotifications());
                    }
                });
                break;

        }
    }
}
