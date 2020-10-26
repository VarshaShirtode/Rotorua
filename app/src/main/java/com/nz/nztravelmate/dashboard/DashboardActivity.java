package com.nz.nztravelmate.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.nz.nztravelmate.R;
import com.nz.nztravelmate.dashboard.accommodation.FragmentAccommodation;
import com.nz.nztravelmate.startup.FragmentAttraction;
import com.nz.nztravelmate.startup.MapActivity;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    TabLayout tabLayout;
    RelativeLayout rlContainer;
    TextView txtFacility, txtMAp, txtProfile;
    FragmentAttraction fragmentAttraction;
    FragmentAccommodation fragmentAccommodation;
    FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initUI();
        initFragment();
        initListner();
      /*  tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("SERVICE"));//Luxury and Duty Fee
        tabLayout.addTab(tabLayout.newTab().setText("MAP"));// Food And Drink
        tabLayout.addTab(tabLayout.newTab().setText("PROFILE"));//Clothing and Souvenirs
       // tabLayout.addTab(tabLayout.newTab().setText("Accommodation"));//Accommodation
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final FooterPagerAdapter adapter =
         new FooterPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });*/
    }

    private void initListner() {
        txtMAp.setOnClickListener(this);
        txtFacility.setOnClickListener(this);
        txtProfile.setOnClickListener(this);
    }

    private void initUI() {
        rlContainer = findViewById(R.id.rlContainer);
        txtFacility = findViewById(R.id.txtFacility);
        txtMAp = findViewById(R.id.txtMap);
        txtProfile = findViewById(R.id.txtProfile);
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentAttraction = new FragmentAttraction();
        fragmentAccommodation=new FragmentAccommodation();
        callFirstFragment("SERVICE", fragmentAttraction);

    }
    public void callFirstFragment(String fragName, Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rlContainer, fragment, fragName);
        fragmentTransaction.commit();

        new Handler().postDelayed(new Runnable() {//shradha
            @Override
            public void run() {
                //Here you can send the extras.
                // //pd.dismiss();
            }
        }, 1000);
    }

    public void callFragment(String fragName, Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.rlContainer, fragment, fragName).addToBackStack("SERVICE");

        if (fragName.equals("SERVICE"))
            fragmentTransaction.replace(R.id.rlContainer, fragment, fragName).addToBackStack("SERVICE");
        else
            fragmentTransaction.replace(R.id.rlContainer, fragment, fragName);
        fragmentTransaction.commit();

        new Handler().postDelayed(new Runnable() {//shradha
            @Override
            public void run() {
                //Here you can send the extras.
              //  pd.dismiss();
            }
        }, 1000);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.txtFacility:
                callFragment("SERVICE",fragmentAttraction);
                break;
            case R.id.txtMap:
              //  callFragment("MAP",fragmentAccommodation);
                Intent intent=new Intent(DashboardActivity.this, MapActivity.class);
                startActivity(intent);
                break;
            case R.id.txtProfile:
                break;
             default:
                break;
        }
    }
}