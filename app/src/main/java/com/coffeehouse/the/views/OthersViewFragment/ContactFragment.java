package com.coffeehouse.the.views.OthersViewFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.coffeehouse.the.R;
import com.coffeehouse.the.databinding.ContactFragmentBinding;
import com.coffeehouse.the.viewModels.AuthViewModel;
import com.coffeehouse.the.views.MainActivity;

import org.jetbrains.annotations.NotNull;

public class ContactFragment extends Fragment implements View.OnClickListener {
    public ContactFragment() {
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ContactFragmentBinding contactFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.contact_fragment, container, false);
        View v = contactFragmentBinding.getRoot();

        v.findViewById(R.id.close_contact_fragment).setOnClickListener(this::onClick);
        v.findViewById(R.id.phone_contact).setOnClickListener(this::onClick);
        v.findViewById(R.id.email_contact).setOnClickListener(this::onClick);
        v.findViewById(R.id.website_contact).setOnClickListener(this::onClick);
        v.findViewById(R.id.logout).setOnClickListener(this::onClick);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_contact_fragment:
                Fragment fragment = new OthersFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
//            case R.id.phone_contact:
//                openPhone();
//                break;
//            case R.id.email_contact:
//                openEmail();
//                break;
//            case R.id.website_contact:
//                openWebsite();
//                break;
            case R.id.logout:
                AuthViewModel authViewModel = new AuthViewModel();
                authViewModel.signOut();
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
    }
}
