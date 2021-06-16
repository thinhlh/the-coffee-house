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

import org.jetbrains.annotations.NotNull;

public class SavedAddressFragment extends Fragment implements View.OnClickListener {
    public SavedAddressFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        SavedAddressFragmentBinding savedAddressFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.saved_address_fragment, container, false);
        View v = savedAddressFragmentBinding.getRoot();

        v.findViewById(R.id.close_saved_address).setOnClickListener(this::onClick);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_saved_address:
                Fragment fragment = new OthersFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
        }
    }
}
