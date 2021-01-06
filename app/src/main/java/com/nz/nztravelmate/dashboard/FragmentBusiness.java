package com.nz.nztravelmate.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nz.nztravelmate.R;
import com.nz.nztravelmate.model.Category;
import com.nz.nztravelmate.model.Data;
import com.nz.nztravelmate.model.UserResponse;
import com.nz.nztravelmate.utils.NetworkUtils;
import com.nz.nztravelmate.utils.PrefConstants;
import com.nz.nztravelmate.utils.Preferences;
import com.nz.nztravelmate.webservice.ApiService;
import com.nz.nztravelmate.webservice.RetrofitClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;


public class FragmentBusiness extends Fragment {
    Context context;
    View rootView;
    RecyclerView RecyclerFood;
    ArrayList<Data> foodlist;
    ArrayList<Category> categoryList;
    //Overriden method onCreateView
    Preferences preferences;
    TextView txtMap;

    Category category;
    String category_id="";
    ImageView imgTab;
    ArrayList<String> locationList;

    public FragmentBusiness(String category_id) {

        this.category_id=category_id;
        //preferences=new Preferences(getActivity());

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=getActivity();
        preferences=new Preferences(this.context);

    }

   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        context=getActivity();
        rootView=inflater.inflate(R.layout.fragment_food, container, false);
      // preferences=new Preferences(context);
        initUI();
        getData();

        return rootView;
    }

    /*@Override
    public void onResume() {
        super.onResume();
getData();
    }*/

   /* private void loadImage(ImageView profileImageView, String imagePath) {
       // String filePath = ApiConstants.GET_IMAGE + imagePath;
        String filePath ="https://nztravelmate.com/uploads/city_images/1604153405.jpg";
        Log.i("@RESP", "ImageUrlFragmentFood: " + filePath);
        Picasso.with(context)
                .load(filePath.replace(" ", "%20"))
                .placeholder(R.drawable.splash_lake)
                .into(profileImageView);
    }*/
    private void setData() {
        BusinessAdpater adapter = new BusinessAdpater(context,foodlist);
        RecyclerFood.setHasFixedSize(true);
        RecyclerFood.setLayoutManager(new LinearLayoutManager(context));
        RecyclerFood.setAdapter(adapter);

        Gson gson = new Gson();
        String json =preferences.getString(PrefConstants.CATEGORY_LIST);
        Type type = new TypeToken<List<Category>>() {}.getType();
        categoryList = gson.fromJson(json, type);

      /*  for (int i=0;i<foodlist.size();i++)
        {
            locationList.add(foodlist.get(i).getMap());
        }*/
       /* txtMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"CLicked "+locationList.size()+ foodlist.size(), LENGTH_SHORT).show();
             *//* Intent intent=new Intent(DashboardActivity.this, MapsActivity.class);
              intent.putExtra("LATLONG",preferences.getString(PrefConstants.CITY_MAP));
              intent.putExtra("LABEL",preferences.getString(PrefConstants.CITY_NAME));
              startActivity(intent);*//*
            }
        }); */
    }

    private void getData() {
        foodlist=new ArrayList<Data>();

       /* Food food=new Food();
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
        foodlist.add(food1);*/

        if (!NetworkUtils.getConnectivityStatusString(getActivity()).equals("Not connected to Internet")) {
            callBusinessDetailsWs();
        }else{
            //DialogManager.showAlert("Network Error, Check your internet connection", SignUpActivity.this);
            NetworkUtils.showAlert(getActivity());
        }
    }

    private void callBusinessDetailsWs() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);



        Map<String,String> params = new HashMap<String, String>();
       params.put("city_id", preferences.getString(PrefConstants.CITY_ID));
        params.put("category_id", category_id);
        params.put("language_id", ""+preferences.getInt(PrefConstants.LANGUAGE_ID));
        Log.v("@RESP","Request "+" city_id="+preferences.getString(PrefConstants.CITY_ID)+" category_id="+category_id+" language_id="+preferences.getInt(PrefConstants.LANGUAGE_ID));

       /* params.put("city_id",""+ 8);
        params.put("category_id",""+ 8);
        params.put("language_id",""+ 1);*/


        ApiService apiService = RetrofitClient.getClient(context).create(ApiService.class);
        Call<UserResponse> call = apiService.getBusinessDetails(params);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                try {
                    Log.v("@RESP", "RespFood: " + response);
                    Log.v("@RESP", "RespFood: " + response.body().getData().size());
                    locationList=new ArrayList<>();
                    UserResponse userResponse = response.body();
                    if (response.body() != null && userResponse.getStatus().equals("Success")) {
                        //    cityList.clear();
                        for (int i = 0; i < userResponse.getData().size(); i++) {
                            foodlist.add(userResponse.getData().get(i));
                           // locationList.add(userResponse.getData().get(i).getMap());
                          //  Log.v("@MAP",""+locationList.size());
                        }
                        setData();

                    } else {
                        Toast.makeText(context, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.v("@RESP", "RespFoodException: " + ex.getLocalizedMessage());
                    Toast.makeText(context, ex.getLocalizedMessage(), LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Log.v("@RESP", "RespFoodFail: "+t.getLocalizedMessage());
                Toast.makeText(context, t.getLocalizedMessage(), LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }



    private void initUI() {
        RecyclerFood=rootView.findViewById(R.id.RecyclerFood);
        imgTab=getActivity().findViewById(R.id.imgTab);
        txtMap=getActivity().findViewById(R.id.txtMap);


    }
}
