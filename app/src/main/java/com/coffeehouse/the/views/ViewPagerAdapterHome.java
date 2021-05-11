package com.coffeehouse.the.views;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapterHome extends FragmentStatePagerAdapter {
    public ViewPagerAdapterHome(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               return new homefragment();
           case 1:
               return new orderfragment();
           case 2:
                   return new storelocationfragment();
           case 3:
               return new membershipfragment();
           case 4:
               return new othersfragment();
           default: return new homefragment();
       }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
