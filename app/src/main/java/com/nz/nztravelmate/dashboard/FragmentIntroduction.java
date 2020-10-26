package com.nz.nztravelmate.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nz.nztravelmate.R;


public class FragmentIntroduction extends Fragment {
    Context context=getActivity();
    View rootView;
   /* RecyclerView RecyclerFood;
    ArrayList<Food> foodlist;*/
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        rootView=inflater.inflate(R.layout.fragment_introduction, container, false);
        /*initUI();
        getData();
        setData();*/
        return rootView;
    }

   /* private void setData() {
        FoodAdpater adapter = new FoodAdpater(context,foodlist);
        RecyclerFood.setHasFixedSize(true);
        RecyclerFood.setLayoutManager(new LinearLayoutManager(context));
        RecyclerFood.setAdapter(adapter);
    }

    private void getData() {
        foodlist=new ArrayList<Food>();

        Food food=new Food();
        food.setName("Food 1 Service");
        food.setAddress("Sr No 36/3/3, Gangawas Society, Erandwane, Pune 411038");
        food.setDistance("Takes 30 minutes");
        food.setWeekTime("Mon-Fri 8:30am-8:30pm");
        food.setWeekendTime("Sat-Sun 7:30am-8:30pm");
        food.setService("Alipay Payment|Free wifi|Free Parking| Service");


        Food food1=new Food();
        food1.setName("Food 2 Service");
        food1.setAddress("B wing, Aditya Business Center, Kondhawa, Pune 411048");
        food1.setDistance("Takes 45 minutes");
        food1.setWeekTime("Mon-Fri 9:30am-8:30pm");
        food1.setWeekendTime("Sat-Sun 8:30am-8:30pm");
        food1.setService("Alipay Payment|Free wifi|Free Parking| Service");

        foodlist.add(food);
        foodlist.add(food1);
    }

    private void initUI() {
        RecyclerFood=rootView.findViewById(R.id.RecyclerFood);
    }*/
}
