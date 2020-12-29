package com.nz.nztravelmate.startup;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nz.nztravelmate.R;
import com.nz.nztravelmate.model.City;
import com.nz.nztravelmate.model.UserResponse;
import com.nz.nztravelmate.utils.LocaleManager;
import com.nz.nztravelmate.utils.NetworkUtils;
import com.nz.nztravelmate.utils.PrefConstants;
import com.nz.nztravelmate.utils.Preferences;
import com.nz.nztravelmate.webservice.ApiService;
import com.nz.nztravelmate.webservice.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class CitySelectionActivity extends AppCompatActivity {
Context context=this;
    View rootView;
    RecyclerView RecyclerFood;
    ArrayList<City> cityList;
    Preferences preferences;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
        preferences=new Preferences(context);
        initUI();


    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }


    private void setData() {
        CityAdpater adapter=new CityAdpater(context,cityList);
        RecyclerFood.setHasFixedSize(true);
        RecyclerFood.setLayoutManager(new LinearLayoutManager(context));
        RecyclerFood.setAdapter(adapter);
    }

    private void getData() {
        cityList=new ArrayList<City>();

       /* City food2=new City();
        food2.setName("Rotorua");
        food2.setImgUrl("Sr No 36/3/3, Gangawas Society, Erandwane, Pune 411038");

        food3.setImgUrl("Sr No 36/3/3, Gangawas Society, Erandwane, Pune 411038");
        City food3=new City();
        food3.setName("MelBourne");


        City food=new City();
        food.setName("Hamilton");
        food.setImgUrl("Sr No 36/3/3, Gangawas Society, Erandwane, Pune 411038");

        City food1=new City();
        food1.setName("City 4");
        food1.setImgUrl("B wing, Aditya Business Center, Kondhawa, Pune 411048");

        cityList.add(food2);
        cityList.add(food3);
        cityList.add(food);
        cityList.add(food1);*/
        if (!NetworkUtils.getConnectivityStatusString(CitySelectionActivity.this).equals("Not connected to Internet")) {
            callGetCityWs();
        }else{
           //DialogManager.showAlert("Network Error, Check your internet connection", SignUpActivity.this);
             NetworkUtils.showAlert(CitySelectionActivity.this);
        }
    }


    private void callGetCityWs() {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

            ApiService apiService = RetrofitClient.getClient(context).create(ApiService.class);
            Call<UserResponse> call = apiService.getCityList(""+preferences.getInt(PrefConstants.LANGUAGE_ID));
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                    try {
                        Log.v("@RESP", "RespCity: " + response);
                        Log.v("@RESP", "RespCity: " + response.body().getCity().size());

                        UserResponse userResponse = response.body();
                        if (response.body() != null && userResponse.getError().equals("0")) {
                        //    cityList.clear();
                            for (int i = 0; i < userResponse.getCity().size(); i++) {
                                    cityList.add(userResponse.getCity().get(i));
                                Log.v("message", "RespCity: " + userResponse.getCity().get(i).getDescription());
                            }
                            setData();
                        } else {
                     Toast.makeText(context, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Log.v("@RESP", "RespCityException: " + ex.getLocalizedMessage());
                        Toast.makeText(context, ex.getLocalizedMessage(), LENGTH_SHORT).show();
                      progressDialog.dismiss();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                    Log.v("@RESP", "RespCityFail: "+t.getLocalizedMessage());
                    Toast.makeText(context, t.getLocalizedMessage(), LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }


    private void initUI() {
        RecyclerFood=findViewById(R.id.RecyclerCity);
    }


}