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
    ArrayList<String> category_id_list=new ArrayList<>();

    public PagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<Category> categoryList) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.categoryList=categoryList;
        if (categoryList.size()!=0) {
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getCategory_details().size()!=0) {
                    category_id_list.add(categoryList.get(i).getCategory_details().get(0).getCategory_id());
                    Log.v("@RESP", "Category " + categoryList.get(i).getCategory_details().get(0).getCategory_id() + " " + categoryList.get(i).getName());
                }
            }
        }
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentBusiness tab1 = new FragmentBusiness(category_id_list.get(position));
                return tab1;

            case 1:
                FragmentBusiness tab2 = new FragmentBusiness(category_id_list.get(position));
                return tab2;

            case 2:
                FragmentBusiness tab3 = new FragmentBusiness(category_id_list.get(position));
                return tab3;

            case 3:
                FragmentBusiness tab4 = new FragmentBusiness(category_id_list.get(position));
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
