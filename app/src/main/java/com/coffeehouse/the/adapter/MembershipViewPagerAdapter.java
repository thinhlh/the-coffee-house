package com.coffeehouse.the.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.coffeehouse.the.views.screen1_MembershipFragment;
import com.coffeehouse.the.views.screen2_MembershipFragment;

import org.jetbrains.annotations.NotNull;

public class MembershipViewPagerAdapter extends FragmentStatePagerAdapter {

    public MembershipViewPagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new screen1_MembershipFragment();
            case 1:
                return new screen2_MembershipFragment();
            default:
                return new screen1_MembershipFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Tích Điểm";
                break;
            case 1:
                title = "Phiếu Ưu Đãi";
                break;
        }
        return title;
    }
}
