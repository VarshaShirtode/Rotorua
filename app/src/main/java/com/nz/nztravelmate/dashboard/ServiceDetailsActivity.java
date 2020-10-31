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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.nz.nztravelmate.R;
import com.nz.nztravelmate.model.Food;
import com.nz.nztravelmate.utils.LocaleManager;

public class ServiceDetailsActivity extends AppCompatActivity {
TabLayout tabLayout;
    FragmentIntroduction fragmentDetail;
    FragmentDiscount fragmentDiscount;
    FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    LinearLayout llA,llB;
    View vA,vB;
    RelativeLayout rlContainer;
    TextView txtA,txtB;
    TextView txtName, txtAddress,txtDistance,txtTime,txtService;
    Food food;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        initUI();
        initFragment();
        initListner();
      /* tabLayout= (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Luxury"));//Luxury and Duty Fee
        tabLayout.addTab(tabLayout.newTab().setText("Food"));// Food And Drink
      //  tabLayout.addTab(tabLayout.newTab().setText("Clothing"));//Clothing and Souvenirs
       // tabLayout.addTab(tabLayout.newTab().setText("Accommodation"));//Accommodation
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
       final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final ServiceDetailPagerAdapter adapter = new ServiceDetailPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


       tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            //    viewPager.setCurrentItem(tab.getPosition());
              //  imgTab.setImageResource(images[tab.getPosition()]);
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
llB.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        callFragment("B", fragmentDiscount);
        vA.setVisibility(View.GONE);
        vB.setVisibility(View.VISIBLE);

        txtA.setTextColor(getResources().getColor(R.color.colorBlack));
        txtB.setTextColor(getResources().getColor(R.color.colorSkyBlue));
    }
});
        llA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragment("C", fragmentDetail);
                vA.setVisibility(View.VISIBLE);
                vB.setVisibility(View.GONE);
                txtA.setTextColor(getResources().getColor(R.color.colorSkyBlue));
                txtB.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        });
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentDetail=new FragmentIntroduction();
        fragmentDiscount=new FragmentDiscount();
        callFragment("A", fragmentDetail);
        vA.setVisibility(View.VISIBLE);
        vB.setVisibility(View.GONE);
        txtA.setTextColor(getResources().getColor(R.color.colorSkyBlue));
        txtB.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    private void initUI() {
        rlContainer = findViewById(R.id.rlContainer);
        llA = findViewById(R.id.llA);
        llB = findViewById(R.id.llB);
        txtA = findViewById(R.id.txtA);
        txtB = findViewById(R.id.txtB);
        vA=findViewById(R.id.vA);
        vB=findViewById(R.id.vB);
        txtName=findViewById(R.id.txtName);
        txtAddress=findViewById(R.id.txtAddress);
        txtDistance=findViewById(R.id.txtDistance);
        txtTime=findViewById(R.id.txtTime);
        txtService=findViewById(R.id.txtService);


//GetData from list and display
        food=new Food();
        Intent intent=getIntent();
        food= (Food) intent.getExtras().getSerializable("ItemObject");
        txtName.setText(food.getName());
        txtAddress.setText(food.getAddress());
        txtDistance.setText(food.getDistance());
        txtTime.setText(food.getWeekTime()+"\n"+food.getWeekendTime());
        txtService.setText(food.getService());


        //txtProfile = findViewById(R.id.txtProfile);
    }
    public void callFragment(String fragName, Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.rlContainer, fragment, fragName).addToBackStack("SERVICE");

       /* if (fragName.equals("A"))
            fragmentTransaction.replace(R.id.rlContainer, fragment, fragName);
        else*/

            fragmentTransaction.replace(R.id.rlContainer, fragment);
        fragmentTransaction.commit();

        new Handler().postDelayed(new Runnable() {//shradha
            @Override
            public void run() {
                //Here you can send the extras.
                //  pd.dismiss();
            }
        }, 1000);

    }
}
