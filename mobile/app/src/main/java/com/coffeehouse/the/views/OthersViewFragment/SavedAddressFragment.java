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
import com.coffeehouse.the.views.OrderDetailFragment;

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
        v.findViewById(R.id.fix_homeaddress).setOnClickListener(this::onClick);
        v.findViewById(R.id.fix_companyaddress).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_saved_address:
                Fragment fragment = new OrderDetailFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;

            case R.id.fix_homeaddress:
                Fragment fragment1 = new fix_savedaddress();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment1).commit();
                break;

            case R.id.fix_companyaddress:
                Fragment fragment2 = new fix_savedaddress();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment2).commit();
                break;
        }
    }
}
