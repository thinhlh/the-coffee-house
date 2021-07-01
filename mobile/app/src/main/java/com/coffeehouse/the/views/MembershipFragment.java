package com.coffeehouse.the.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.coffeehouse.the.R;
import com.coffeehouse.the.adapter.MembershipTablayoutAdapter;
import com.google.android.material.tabs.TabLayout;

public class MembershipFragment extends Fragment implements View.OnClickListener {
    ViewPager viewPager;
    TabLayout tabLayout;
    View v;

    public MembershipFragment() {
    }


    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setUpViewPager(ViewPager viewPager) {
        MembershipTablayoutAdapter adapter = new MembershipTablayoutAdapter(getChildFragmentManager());
        adapter.addFragment(new screen1_membership(), "Tích điểm");
        adapter.addFragment(new screen2_membership(), "Phiếu ưu đãi");
        viewPager.setAdapter(adapter);

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.membership_fragment, container, false);
        viewPager = v.findViewById(R.id.viewpager_membership);
        tabLayout = v.findViewById(R.id.tablayout_membership);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
