package com.nz.nztravelmate.startup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nz.nztravelmate.R;
import com.nz.nztravelmate.dashboard.PagerAdapter;
import com.nz.nztravelmate.dashboard.ServiceDetailsActivity;
import com.nz.nztravelmate.map.MapsActivity;
import com.nz.nztravelmate.model.Banner;
import com.nz.nztravelmate.model.Category;
import com.nz.nztravelmate.model.CategoryDetails;
import com.nz.nztravelmate.model.Data;
import com.nz.nztravelmate.model.Maps;
import com.nz.nztravelmate.utils.PrefConstants;
import com.nz.nztravelmate.utils.Preferences;
import com.nz.nztravelmate.webservice.ApiConstants;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FragmentAttraction extends Fragment {
    Context context;
    View rootView;
    TabLayout tabLayout;
    ImageView imgTab;
    TextView txtMap;

    Preferences preferences;
    ArrayList<Category> categoryList;
    ArrayList<String> locationList=new ArrayList<>();

    int images[]=new int[]{R.drawable.resize,R.drawable.splash_lake,R.drawable.lake_rotorua,R.drawable.rsz_splash};
    ArrayList<CategoryDetails> categoryDetails;
    ArrayList<Maps> mapList;
    ArrayList<Data> businessList;
    TextView txtTitle;
    private ArrayList<Banner> bannerList;

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
        txtMap=getActivity().findViewById(R.id.txtMap);
        txtTitle=getActivity().findViewById(R.id.txtTitle);
        tabLayout= (TabLayout)rootView.findViewById(R.id.tab_layout);

        categoryList=new ArrayList<>();
        bannerList=new ArrayList<>();




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

       /* String jsonr =preferences.getString(PrefConstants.BANNER_LIST);
        Type typer = new TypeToken<List<Banner>>() {}.getType();
        bannerList = gson.fromJson(jsonr, typer);
        for(int i=0;i<4;i++)
        {
            categoryList.get(i).setBanner(bannerList.get(i).getImage());
        }*/


        String json2=preferences.getString(PrefConstants.MAP_LIST);
        Type type2 = new TypeToken<List<Maps>>() {}.getType();
       mapList = gson.fromJson(json2, type2);

        String json3=preferences.getString(PrefConstants.BUSINESS_LIST);
        Type type3 = new TypeToken<List<Data>>() {}.getType();
        businessList = gson.fromJson(json3, type3);

        if(businessList.size()!=0) {
            for (int i = 0; i < businessList.size(); i++) {
                Log.v("@MPALIST","businessLIST "+businessList.get(i).getStaff());
            }
        }

        Log.v("@RESP","CAT "+categoryList.size());

        if(categoryList.size()!=0) {

            for (int i = 0; i < 4; i++) {
                if (!categoryList.get(i).getCategory_details().isEmpty())
                {
                    categoryDetails=categoryList.get(i).getCategory_details();

                    for (int j=0;j<categoryDetails.size();j++)
                    {
                        preferences.putString(PrefConstants.CATEGORY_ID,categoryList.get(0).getCategory_details().get(0).getName());
                        String name= categoryDetails.get(j).getName();
                        Log.v("@RESP","CAT "+categoryDetails.get(j).getName());
                        tabLayout.addTab(tabLayout.newTab().setText(categoryDetails.get(j).getName()));//Luxury and Duty Fee

                        /* ArrayList<String> map1=new ArrayList<>();
                        ArrayList<String> map2=new ArrayList<>();
                        ArrayList<String> map3=new ArrayList<>();
                        ArrayList<String> map4=new ArrayList<>();
                        map1=callBusinessDetailsWs(categoryDetails.get(i).getCategory_id());
*/
                    }
                }

             //   Log.v("@RESP","CAT "+categoryList.get(i).getName());
             //   tabLayout.addTab(tabLayout.newTab().setText(categoryList.get(i).getName()));//Luxury and Duty Fee
            }
        }
        //tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        if (preferences.getInt(PrefConstants.LANGUAGE_ID)==1) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }else if (preferences.getInt(PrefConstants.LANGUAGE_ID)==2) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#33B6FF"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#A9A9A9"), Color.parseColor("#33B6FF"));

        if (categoryList.get(0).getBanner().length()!=0) {
            loadImageBanner(imgTab, categoryList.get(0).getBanner());
        }else{
            loadImage(imgTab, categoryList.get(0).getImage());
        }

        final PagerAdapter adapter = new PagerAdapter
                (getActivity(),getActivity().getSupportFragmentManager(), tabLayout.getTabCount(),categoryList);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        int limit = (adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        viewPager.setOffscreenPageLimit(limit);
        
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //viewPager.setCurrentItem(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.v("@RESP","TAB Pos "+tab.getPosition());
                switch (tab.getPosition())
                {
                    case 0:
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#33B6FF"));
                        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
                        tabLayout.setTabTextColors(Color.parseColor("#A9A9A9"), Color.parseColor("#33B6FF"));
                        if (categoryList.get(0).getBanner().length()!=0) {
                            loadImageBanner(imgTab, categoryList.get(tab.getPosition()).getBanner());
                            preferences.putString(PrefConstants.CATEGORY_ID,categoryList.get(tab.getPosition()).getCategory_details().get(0).getName());

                        } else {
                            loadImage(imgTab, categoryList.get(tab.getPosition()).getImage());
                            preferences.putString(PrefConstants.CATEGORY_ID,categoryList.get(tab.getPosition()).getCategory_details().get(0).getName());

                        }
                        break;
                    case 1:
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#008000"));
                        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
                        tabLayout.setTabTextColors(Color.parseColor("#A9A9A9"), Color.parseColor("#008000"));
                        if (categoryList.get(1).getBanner().length()!=0) {
                            loadImageBanner(imgTab, categoryList.get(tab.getPosition()).getBanner());
                            preferences.putString(PrefConstants.CATEGORY_ID,categoryList.get(tab.getPosition()).getCategory_details().get(0).getName());

                        } else {
                            loadImage(imgTab, categoryList.get(tab.getPosition()).getImage());
                            preferences.putString(PrefConstants.CATEGORY_ID,categoryList.get(tab.getPosition()).getCategory_details().get(0).getName());

                        }
                        break;
                    case 2:
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
                        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
                        tabLayout.setTabTextColors(Color.parseColor("#A9A9A9"), Color.parseColor("#FF0000"));
                        if (categoryList.get(2).getBanner().length()!=0) {
                            loadImageBanner(imgTab, categoryList.get(tab.getPosition()).getBanner());
                            preferences.putString(PrefConstants.CATEGORY_ID,categoryList.get(tab.getPosition()).getCategory_details().get(0).getName());

                        } else {
                            loadImage(imgTab, categoryList.get(tab.getPosition()).getImage());
                            preferences.putString(PrefConstants.CATEGORY_ID,categoryList.get(tab.getPosition()).getCategory_details().get(0).getName());

                        }
                        break;
                    case 3:
                        if (categoryList.get(3).getBanner().length()!=0) {
                            loadImageBanner(imgTab, categoryList.get(tab.getPosition()).getBanner());
                            preferences.putString(PrefConstants.CATEGORY_ID,categoryList.get(tab.getPosition()).getCategory_details().get(0).getName());

                        } else {
                            loadImage(imgTab, categoryList.get(tab.getPosition()).getImage());
                            preferences.putString(PrefConstants.CATEGORY_ID,categoryList.get(tab.getPosition()).getCategory_details().get(0).getName());

                        }
                        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));
                        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
                        tabLayout.setTabTextColors(Color.parseColor("#A9A9A9"), Color.parseColor("#000000"));
                        break;
                }



            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        txtMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMap("Category");
            }
        });

        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMap("All");
            }
        });

        imgTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentTab=tabLayout.getSelectedTabPosition();
                if(categoryList.get(currentTab).getBanner().length()!=0) {
                    Data food = null;
                    for (int i = 0; i < businessList.size(); i++) {
                        if (businessList.get(i).getId().equals(categoryList.get(currentTab).getBussiness_id())) {
                            food = businessList.get(i);
                        }
                    }
                    Intent intent = new Intent(getActivity(), ServiceDetailsActivity.class);
                    intent.putExtra("ItemObject", food);
                    getActivity().startActivity(intent);
                }
            }
        });

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
    private void loadImageBanner(ImageView profileImageView, String imagePath) {
        String filePath = ApiConstants.GET_CATEGORY_BANNER_IMAGE + imagePath;
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
     void viewMap(String from)
     {
         if (from.equals("All"))
         {
             Intent intent = new Intent(getActivity(), MapsActivity.class);
             intent.putExtra("LATLONG", "ALL");
             intent.putExtra("LABEL", preferences.getString(PrefConstants.CITY_NAME));
             intent.putExtra("MapList", businessList);
             context.startActivity(intent);
         }else {
             Intent intent = new Intent(getActivity(), MapsActivity.class);
             intent.putExtra("LATLONG", "LISTMAP");
             intent.putExtra("LABEL", preferences.getString(PrefConstants.CITY_NAME));
             intent.putExtra("MapList", businessList);
             context.startActivity(intent);
         }
     }

    }

