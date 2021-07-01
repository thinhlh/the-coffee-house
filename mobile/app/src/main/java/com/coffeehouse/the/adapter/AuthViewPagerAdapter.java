package com.coffeehouse.the.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.coffeehouse.the.views.SignInFragment;
import com.coffeehouse.the.views.SignUpFragment;

public class AuthViewPagerAdapter extends FragmentStatePagerAdapter {

    public AuthViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return (position == 0) ? new SignInFragment() : new SignUpFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (position == 0) ? "Đăng nhập" : "Đăng ký";
    }
}
