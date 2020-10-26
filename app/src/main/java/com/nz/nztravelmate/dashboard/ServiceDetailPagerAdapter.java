package com.nz.nztravelmate.dashboard;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nz.nztravelmate.dashboard.accommodation.FragmentAccommodation;
import com.nz.nztravelmate.dashboard.clothing.FragmentClothing;

public class ServiceDetailPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ServiceDetailPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                FragmentIntroduction tab1 = new FragmentIntroduction();
                return tab1;
            case 0:
                FragmentIntroduction tab2 = new FragmentIntroduction();
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
