package com.nz.nztravelmate.startup;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nz.nztravelmate.R;
import com.nz.nztravelmate.dashboard.PagerAdapter;

public class FragmentAttraction extends Fragment {
    Context context=getActivity();
    View rootView;
    TabLayout tabLayout;
    ImageView imgTab;
    int images[]=new int[]{R.drawable.resize,R.drawable.splash_lake,R.drawable.lake_rotorua,R.drawable.rsz_splash};
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        rootView=inflater.inflate(R.layout.fragment_attraction_activity, container, false);
        imgTab=rootView.findViewById(R.id.imgTab);
        tabLayout= (TabLayout)rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tabattractions));//Luxury and Duty Fee
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tabgourmet));// Food And Drink
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tabshopping));//Clothing and Souvenirs
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tabStay));//Accommodation
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                imgTab.setImageResource(images[tab.getPosition()]);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return rootView;
    }
}
