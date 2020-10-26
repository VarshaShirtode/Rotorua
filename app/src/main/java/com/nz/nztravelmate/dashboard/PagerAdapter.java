package com.nz.nztravelmate.dashboard;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nz.nztravelmate.dashboard.accommodation.FragmentAccommodation;
import com.nz.nztravelmate.dashboard.clothing.FragmentClothing;
import com.nz.nztravelmate.dashboard.food.FragmentFood;
import com.nz.nztravelmate.dashboard.luxury.FragmentLuxury;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                FragmentFood tab1 = new FragmentFood();
                return tab1;
            case 0:
                FragmentLuxury tab2 = new FragmentLuxury();
                return tab2;
            case 2:
                FragmentClothing tab3 = new FragmentClothing();
                return tab3;
            case 3:
                FragmentAccommodation tab4 = new FragmentAccommodation();
                return tab4;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
