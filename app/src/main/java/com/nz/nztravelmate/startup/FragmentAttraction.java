package com.nz.nztravelmate.startup;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nz.nztravelmate.R;
import com.nz.nztravelmate.dashboard.PagerAdapter;
import com.nz.nztravelmate.model.Category;
import com.nz.nztravelmate.model.UserResponse;
import com.nz.nztravelmate.utils.PrefConstants;
import com.nz.nztravelmate.utils.Preferences;
import com.nz.nztravelmate.webservice.ApiConstants;
import com.nz.nztravelmate.webservice.ApiService;
import com.nz.nztravelmate.webservice.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class FragmentAttraction extends Fragment {
    Context context;
    View rootView;
    TabLayout tabLayout;
    ImageView imgTab;
    Preferences preferences;
    ArrayList<Category> categoryList;
    int images[]=new int[]{R.drawable.resize,R.drawable.splash_lake,R.drawable.lake_rotorua,R.drawable.rsz_splash};

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=getActivity();
        preferences=new Preferences(context);
    }

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        rootView=inflater.inflate(R.layout.fragment_attraction_activity, container, false);
        imgTab=rootView.findViewById(R.id.imgTab);
        tabLayout= (TabLayout)rootView.findViewById(R.id.tab_layout);
        categoryList=new ArrayList<>();

     /*   tabLayout.addTab(tabLayout.newTab().setText(R.string.tabattractions));//Luxury and Duty Fee
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tabgourmet));// Food And Drink
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tabshopping));//Clothing and Souvenirs
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tabStay));//Accommodation
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
     */   //wrapTabIndicatorToTitle(tabLayout,0,00);


        Gson gson = new Gson();
        String json =preferences.getString(PrefConstants.CATEGORY_LIST);
        Type type = new TypeToken<List<Category>>() {}.getType();
        categoryList = gson.fromJson(json, type);
        if(categoryList.size()!=0) {
            for (int i = 0; i < 4; i++) {
                tabLayout.addTab(tabLayout.newTab().setText(categoryList.get(i).getName()));//Luxury and Duty Fee
            }
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        if (categoryList.get(0).getImage() != null) {
            loadImage(imgTab, categoryList.get(0).getImage());
        }

        final PagerAdapter adapter = new PagerAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount(),categoryList);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        int limit = (adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        viewPager.setOffscreenPageLimit(limit);
        
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.v("@RESP","TAB Pos "+tab.getPosition());
                   if (categoryList.get(tab.getPosition()).getImage() != null) {
                        loadImage(imgTab, categoryList.get(tab.getPosition()).getImage());
                    } else {
                        imgTab.setImageResource(images[tab.getPosition()]);
                    }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
//callBusinessDetailsWs();

        return rootView;
    }
    private void loadImage(ImageView profileImageView, String imagePath) {
        String filePath = ApiConstants.GET_CATEGORY_IMAGE + imagePath;
       //String filePath ="https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072823__340.jpg";
        Log.i("@RESP", "ImageUrl: " + filePath);
        Picasso.with(context)
                .load(filePath.replace(" ", "%20"))
                .placeholder(R.drawable.splash)
                .into(profileImageView);
    }

    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        settingMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        settingMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        settingMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }


            tabLayout.requestLayout();
        }
    }

    private void settingMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }
}
