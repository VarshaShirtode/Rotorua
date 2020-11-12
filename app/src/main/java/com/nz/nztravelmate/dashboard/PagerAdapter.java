package com.nz.nztravelmate.dashboard;


import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nz.nztravelmate.model.Category;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    ArrayList<Category> categoryList;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<Category> categoryList) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.categoryList=categoryList;
        for(int i=0;i<categoryList.size();i++)
        {
            Log.v("@RESP","Category "+categoryList.get(i).getId()+" "+categoryList.get(i).getName());
        }

    }
    @Override
    public Fragment getItem(int position) {
        FragmentBusiness tab1 = new FragmentBusiness(""+(position+1));
        return tab1;
        /*switch (position) {
            case 0:
                FragmentLuxury tab2 = new FragmentLuxury("1");
                return tab2;

            case 1:
                FragmentFood tab1 = new FragmentFood("2");
                return tab1;

            case 2:
                FragmentClothing tab3 = new FragmentClothing("3");
                return tab3;

            case 3:
                FragmentAccommodation tab4 = new FragmentAccommodation("4");
                return tab4;

            default:
                return null;
        }*/
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
