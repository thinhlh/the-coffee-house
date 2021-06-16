package com.coffeehouse.the.views.OthersViewFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.SavedAddressFragmentBinding;
import com.coffeehouse.the.databinding.SettingFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    public SettingsFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        SettingFragmentBinding settingFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.setting_fragment, container, false);
        View v = settingFragmentBinding.getRoot();

        v.findViewById(R.id.close_settings).setOnClickListener(this::onClick);
        v.findViewById(R.id.about_us).setOnClickListener(this::onClick);

        return v;
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
        }
    }
}
