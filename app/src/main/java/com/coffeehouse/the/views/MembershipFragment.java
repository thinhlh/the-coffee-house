package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.MembershipViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MembershipFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.membership_fragment,container,false);
        tabLayout=v.findViewById(R.id.tablayout_membership);
        viewPager=v.findViewById(R.id.viewpager_membership);
        MembershipViewPagerAdapter membershipViewPagerAdapter = new MembershipViewPagerAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(membershipViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return  v;
    }
}
