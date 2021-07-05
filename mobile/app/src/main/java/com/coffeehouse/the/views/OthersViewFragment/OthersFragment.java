package com.coffeehouse.the.views.OthersViewFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.coffeehouse.the.R;
import com.coffeehouse.the.viewModels.AuthViewModel;
import com.coffeehouse.the.views.MainActivity;
import com.facebook.login.LoginManager;

public class OthersFragment extends Fragment implements View.OnClickListener {

    private final AuthViewModel authViewModel = new AuthViewModel();


    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.others_fragment, container, false);
        v.findViewById(R.id.logout).setOnClickListener(this::onClick);
        v.findViewById(R.id.contact).setOnClickListener(this::onClick);
        v.findViewById(R.id.cardview_news).setOnClickListener(this::onClick);
        v.findViewById(R.id.feedback).setOnClickListener(this::onClick);
        v.findViewById(R.id.user_info).setOnClickListener(this::onClick);
        v.findViewById(R.id.saved_address).setOnClickListener(this::onClick);
        v.findViewById(R.id.settings).setOnClickListener(this::onClick);
        v.findViewById(R.id.term).setOnClickListener(this::onClick);
        v.findViewById(R.id.order_history).setOnClickListener(this::onClick);

        return v;
    }


    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()) {
            case R.id.logout:
                LoginManager.getInstance().logOut();
                authViewModel.signOut(v.getContext()).addOnCompleteListener(task -> {
                    startActivity(new Intent(getContext(), MainActivity.class));
                });
                break;
            case R.id.contact:
                fragment = new ContactFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
            case R.id.cardview_news:
                fragment = new NewsPromotionFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
            case R.id.feedback:
                fragment = new FeedbackFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
            case R.id.user_info:
                fragment = new UserInformationFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
            case R.id.saved_address:
//                fragment = new SavedAddressFragment();
//                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.home_fragment_container, new SavedAddressFragment()).addToBackStack(null).commit();
                break;
            case R.id.settings:
                fragment = new SettingsFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
            case R.id.term:
                fragment = new TermFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
            case R.id.order_history:
                fragment = new OrderHistoryFragment();
                getFragmentManager().beginTransaction().replace(this.getId(), fragment).commit();
                break;
        }
    }
}