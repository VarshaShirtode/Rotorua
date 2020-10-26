package com.nz.nztravelmate.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nz.nztravelmate.R;
import com.nz.nztravelmate.model.Discount;

import java.util.ArrayList;


public class FragmentDiscount extends Fragment {
    Context context=getActivity();
    View rootView;
   RecyclerView RecyclerDiscount;
    ArrayList<Discount> discountList;
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        rootView=inflater.inflate(R.layout.fragment_discount, container, false);
        initUI();
        getData();
        setData();
        return rootView;
    }

    private void setData() {
        DiscountAdapter adapter = new DiscountAdapter(context,discountList);
        RecyclerDiscount.setHasFixedSize(true);
        RecyclerDiscount.setLayoutManager(new LinearLayoutManager(context));
        RecyclerDiscount.setAdapter(adapter);
    }

    private void getData() {
        discountList=new ArrayList<Discount>();

        Discount Discount=new Discount();
        Discount.setName("9折优惠券");
        Discount.setDiscount("免费领取");

        Discount Discount1=new Discount();
        Discount1.setName("满100减10代金券");
        Discount1.setDiscount("免费领取");

        Discount Discount2=new Discount();
        Discount2.setName("丁p礼品测试券");
        Discount2.setDiscount("免费领取");

        Discount Discount3=new Discount();
        Discount3.setName("红酒测试折扣券");
        Discount3.setDiscount("免费领取");

       /* Discount.setAddress("Sr No 36/3/3, Gangawas Society, Erandwane, Pune 411038");
        Discount.setDistance("Takes 30 minutes");
        Discount.setWeekTime("Mon-Fri 8:30am-8:30pm");
        Discount.setWeekendTime("Sat-Sun 7:30am-8:30pm");
        Discount.setService("Alipay Payment|Free wifi|Free Parking| Service");
*/

       /* Discount Discount1=new Discount();
        Discount1.setName("Discount 2 Service");*/
       /* Discount1.setAddress("B wing, Aditya Business Center, Kondhawa, Pune 411048");
        Discount1.setDistance("Takes 45 minutes");
        Discount1.setWeekTime("Mon-Fri 9:30am-8:30pm");
        Discount1.setWeekendTime("Sat-Sun 8:30am-8:30pm");
        Discount1.setService("Alipay Payment|Free wifi|Free Parking| Service");
*/
        discountList.add(Discount);
        discountList.add(Discount1);
        discountList.add(Discount2);
        discountList.add(Discount3);
    }

    private void initUI() {
        RecyclerDiscount=rootView.findViewById(R.id.RecyclerDiscount);
    }
}
