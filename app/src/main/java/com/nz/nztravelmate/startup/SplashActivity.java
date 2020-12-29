package com.nz.nztravelmate.startup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.nz.nztravelmate.R;
import com.nz.nztravelmate.dashboard.DashboardActivity;
import com.nz.nztravelmate.model.Banner;
import com.nz.nztravelmate.model.Category;
import com.nz.nztravelmate.model.City;
import com.nz.nztravelmate.model.Data;
import com.nz.nztravelmate.model.Maps;
import com.nz.nztravelmate.model.UserResponse;
import com.nz.nztravelmate.utils.LocaleManager;
import com.nz.nztravelmate.utils.NetworkUtils;
import com.nz.nztravelmate.utils.PrefConstants;
import com.nz.nztravelmate.utils.Preferences;
import com.nz.nztravelmate.webservice.ApiConstants;
import com.nz.nztravelmate.webservice.ApiService;
import com.nz.nztravelmate.webservice.RetrofitClient;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class SplashActivity
        extends AppCompatActivity {
    Context context = this;
    RelativeLayout rlMain;
    TextView txtEnter, txtMainTitle, txtCity, txtDescription, txtVisit;
    Preferences preferences;
    ArrayList<Category> categoryList;
    private View animatedView;
    private ArrayList<Banner> bannerList;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        preferences = new Preferences(context);

        /*View backgroundImage = findViewById(R.id.rlMain);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(190);*/
        categoryList = new ArrayList<>();
        if (!NetworkUtils.getConnectivityStatusString(SplashActivity.this).equals("Not connected to Internet")) {

           // bannerList = new ArrayList<Banner>();
            callGetBannerWS();
        } else {
            //DialogManager.showAlert("Network Error, Check your internet connection", SignUpActivity.this);
            NetworkUtils.showAlert(SplashActivity.this);
        }


        Intent intent = getIntent();
        City city = (City) intent.getExtras().getSerializable("CityObject");


        txtEnter = findViewById(R.id.txtEnter);
        txtMainTitle = findViewById(R.id.txtMainTitle);
        txtCity = findViewById(R.id.txtCity);
        txtDescription = findViewById(R.id.txtDescription);
        txtVisit = findViewById(R.id.txtVisit);


        // txtMainTitle.setText(getResources().getString(R.string.app_name));

        txtCity.setText(city.getCity_details().get(0).getName());
        txtMainTitle.setText(city.getCity_details().get(0).getName());
        if (city.getDescription() != null) {
            txtDescription.setText(city.getCity_details().get(0).getDescription());
        } else {
            txtDescription.setText("");
        }
        //txtVisit.setText(Html.fromHtml("<small>Visit</small>")+"\n"+city.getName());

        txtVisit.setText(Html.fromHtml("<small>" + getResources().getString(R.string.Visit) + "</small><br>" + city.getCity_details().get(0).getName()));
        preferences.putString(PrefConstants.CITY_NAME, city.getCity_details().get(0).getName());
        preferences.putString(PrefConstants.CITY_ID, city.getCity_details().get(0).getCity_id());
        preferences.putString(PrefConstants.CITY_MAP, city.getMap());

        /*preferences.putString(PrefConstants.CITY_MAP,"18.5204° N, 73.8567° E");
        preferences.putString(PrefConstants.CITY_MAP,"");*/

        txtEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                intent.putExtra("CategoryList", categoryList);
                startActivity(intent);
            }
        });

        animatedView = findViewById(R.id.animated_view);
        if (city.getPanoramic_image() != null) {
            loadImage((ImageView) animatedView, city.getPanoramic_image());
        } else {
            animatedView.setBackground(getResources().getDrawable(R.drawable.wave_repeating_bg));
            setAnimation("default");
            // holder.imgCity.setImageResource(R.drawable.splash);
        }
      /*  final int screenWidth = getScreenDimensions(this).x;
        final int screenHeight = getScreenDimensions(this).y;

        final int waveImgWidth = animatedView.getBackground().getMinimumWidth();
       // final int waveImgWidth = screenWidth;

        int animatedViewWidth = 0;
        while (animatedViewWidth < screenWidth) {
            animatedViewWidth += waveImgWidth;

        }
        animatedViewWidth += waveImgWidth;


        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) animatedView.getLayoutParams();
        layoutParams.width = animatedViewWidth;
       // layoutParams.height=screenHeight;
        animatedView.setLayoutParams(layoutParams);


        Animation waveAnimation = new TranslateAnimation(0, -waveImgWidth, 0, 0);
        waveAnimation.setInterpolator(new LinearInterpolator());
        waveAnimation.setRepeatCount(Animation.REVERSE);
        waveAnimation.setDuration(40000);

        animatedView.startAnimation(waveAnimation);
*/

    }

    public static Point getScreenDimensions(Context context) {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        return new Point(width, height);
    }

    private void loadImage(ImageView animatedViewa, String imagePath) {
        String filePath = ApiConstants.GET_CITY_PANORAMA_IMAGE + imagePath;
        // String filePath =imagePath;
        Log.i("@RESP", "ImageUrl: " + filePath);
       /* Picasso.with(context)
                .load(filePath.replace(" ", "%20"))
                .into( profileImageView);*/

        Picasso.with(context).load(filePath.replace(" ", "%20")).into(new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                animatedView.setBackground(new BitmapDrawable(context.getResources(), bitmap));
                Log.v("PANORAMA", "success");
                setAnimation("server");
            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.v("PANORAMA", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.v("PANORAMA", "Prepare Load");
                // animatedView.setBackground(placeHolderDrawable);
               /* final int screenWidth = getScreenDimensions(context).x;
                final int screenHeight = getScreenDimensions(context).y;

                final int waveImgWidth = animatedView.getBackground().getMinimumWidth();
                // final int waveImgWidth = screenWidth;

                int animatedViewWidth = 0;
                while (animatedViewWidth < screenWidth) {
                    animatedViewWidth += waveImgWidth;

                }
                animatedViewWidth += waveImgWidth;


                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) animatedView.getLayoutParams();
                layoutParams.width = animatedViewWidth;
                // layoutParams.height=screenHeight;
                animatedView.setLayoutParams(layoutParams);


                Animation waveAnimation = new TranslateAnimation(0, -waveImgWidth, 0, 0);
                waveAnimation.setInterpolator(new LinearInterpolator());
                waveAnimation.setRepeatCount(Animation.REVERSE);
                waveAnimation.setDuration(40000);

                animatedView.startAnimation(waveAnimation);*/
            }
        });
    }

    private void setAnimation(String from) {
        final int screenWidth = getScreenDimensions(context).x;
        final int screenHeight = getScreenDimensions(context).y;

        final int waveImgWidth = animatedView.getBackground().getMinimumWidth();
        // final int waveImgWidth = screenWidth;

        int animatedViewWidth = 0;
        while (animatedViewWidth < screenWidth) {
            animatedViewWidth += waveImgWidth;

        }
        animatedViewWidth += waveImgWidth;


        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) animatedView.getLayoutParams();
        layoutParams.width = animatedViewWidth;
        // layoutParams.height=screenHeight;
        animatedView.setLayoutParams(layoutParams);


        Animation waveAnimation = new TranslateAnimation(0, -waveImgWidth, 0, 0);
        waveAnimation.setInterpolator(new LinearInterpolator());
        waveAnimation.setRepeatCount(Animation.REVERSE);
        if (from.equals("server")) {
            waveAnimation.setDuration(22000);
        } else {
            waveAnimation.setDuration(40000);
        }

        animatedView.startAnimation(waveAnimation);
    }

    private void callGetCategoryWs(ArrayList<Banner> bannerList) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        ApiService apiService = RetrofitClient.getClient(context).create(ApiService.class);
        Call<UserResponse> call = apiService.getCategoryList("" + preferences.getInt(PrefConstants.LANGUAGE_ID));
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                try {
                    Log.v("@RESP", "RespCity: " + response);
                    Log.v("@RESP", "RespCity: " + response.body().getCategory().size());

                    UserResponse userResponse = response.body();
                    if (response.body() != null && userResponse.getError().equals("0")) {
                        categoryList.clear();
                        for (int i = 0; i < userResponse.getCategory().size(); i++) {
                            Category category=userResponse.getCategory().get(i);
                            if (bannerList.size()!=0) {
                                if (bannerList.get(i).getImage() != null) {
                                    category.setBanner(bannerList.get(i).getImage());
                                }
                                if (bannerList.get(i).getBusiness_id() != null) {
                                    category.setBussiness_id(bannerList.get(i).getBusiness_id());
                                }
                            }
                            categoryList.add(category);
                            Log.v("@RESP", "Category " + categoryList.get(i).getId() + " " + categoryList.get(i).getName());

                        }
                        Gson gson = new Gson();
                        String json = gson.toJson(categoryList);
                        preferences.putString(PrefConstants.CATEGORY_LIST, json);

                        for (int i = 0; i < categoryList.size(); i++) {
                            if (!NetworkUtils.getConnectivityStatusString(SplashActivity.this).equals("Not connected to Internet")) {
                                callBusinessDetailsWs(categoryList.get(i).getId());
                            } else {
                                //DialogManager.showAlert("Network Error, Check your internet connection", SignUpActivity.this);
                                NetworkUtils.showAlert(SplashActivity.this);
                            }

                        }

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
                Log.v("@RESP", "RespCityFail: " + t.getLocalizedMessage());
                Toast.makeText(context, t.getLocalizedMessage(), LENGTH_SHORT).show();
            }
        });
    }

    ArrayList<Maps> mapList = new ArrayList<>();
    ArrayList<Data> businessList = new ArrayList<>();

    private void callBusinessDetailsWs(String category_id) {
        final ProgressDialog progressDialog = new ProgressDialog(SplashActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        Map<String, String> params = new HashMap<String, String>();
        params.put("city_id", preferences.getString(PrefConstants.CITY_ID));
        params.put("category_id", category_id);
        params.put("language_id", "" + preferences.getInt(PrefConstants.LANGUAGE_ID));
        Log.v("@RESP", "Request " + " city_id=" + preferences.getString(PrefConstants.CITY_ID) + " category_id=" + category_id + " language_id=" + preferences.getInt(PrefConstants.LANGUAGE_ID));

        ApiService apiService = RetrofitClient.getClient(context).create(ApiService.class);
        Call<UserResponse> call = apiService.getBusinessDetails(params);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                try {
                    Log.v("@RESP", "RespFood: " + response);
                    Log.v("@RESP", "RespFood: " + response.body().getData().size());
                    UserResponse userResponse = response.body();
                    if (response.body() != null && userResponse.getStatus().equals("Success")) {
                        for (int i = 0; i < userResponse.getData().size(); i++) {
                            businessList.add(userResponse.getData().get(i));
                            Maps maps = new Maps();
                            maps.setMap(userResponse.getData().get(i).getMap());
                            maps.setCategory_id(userResponse.getData().get(i).getCategory_id());
                            if (preferences.getInt(PrefConstants.LANGUAGE_ID) == 1) {
                                maps.setName(userResponse.getData().get(i).getName());
                            } else {
                                maps.setName(userResponse.getData().get(i).getName());
                            }

                            mapList.add(maps);
                        }
                        //  businessList= (ArrayList<Data>) userResponse.getData();
                        Gson gson = new Gson();
                        String json2 = gson.toJson(mapList);
                        preferences.putString(PrefConstants.MAP_LIST, json2);
                        String json3 = gson.toJson(businessList);
                        preferences.putString(PrefConstants.BUSINESS_LIST, json3);
                        /*Log.v("@MPALIST","MAPLIST "+mapList.size());

                        if(mapList.size()!=0) {
                            for (int i = 0; i < 4; i++) {
                                Log.v("@MPALIST","MAPLIST "+mapList.get(i));
                            }
                        }*/
                        // setData();

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
                Log.v("@RESP", "RespFoodFail: " + t.getLocalizedMessage());
                Toast.makeText(context, t.getLocalizedMessage(), LENGTH_SHORT).show();
            }
        });
    }

    private void callGetBannerWS() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        ApiService apiService = RetrofitClient.getClient(context).create(ApiService.class);
        Call<UserResponse> call = apiService.getBannerList();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                try {
                    if (response.body() != null) {
                        UserResponse userResponse = response.body();
                        if (userResponse.getError().equals("0")) {
                       //Toast.makeText(context,"inside response", Toast.LENGTH_SHORT).show();

                      bannerList=new ArrayList<>();
                       if (userResponse.getBanners().size() != 0) {

                           for (int i = 0; i < userResponse.getBanners().size(); i++) {
                                Banner banner = userResponse.getBanners().get(i);
                                if (banner.getCity_id().equals(preferences.getString(PrefConstants.CITY_ID))) {
                                    bannerList.add(banner);
                                }
                            }

                        }
                            callGetCategoryWs(bannerList);
                        } else {
                            Toast.makeText(context, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            callGetCategoryWs(bannerList);
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.v("@RESP", "RespCityException: " + ex.getLocalizedMessage());
                    Toast.makeText(context, ex.getLocalizedMessage(), LENGTH_SHORT).show();
                    Log.v("FCM_TOKEN", "RespCityexcePTION: " + ex.getLocalizedMessage() + " " + ex.getMessage() + "" + ex.toString());
                    bannerList=new ArrayList<>();
                    callGetCategoryWs(bannerList);
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Log.v("@RESP", "RespCityFail: " + t.getLocalizedMessage());
                Toast.makeText(context, t.getLocalizedMessage(), LENGTH_SHORT).show();
                Log.v("FCM_TOKEN", "RespCity: " + t.getLocalizedMessage());
                progressDialog.dismiss();
            }
        });
    }

    private final TimerTask spashScreenFinished = new TimerTask() {
        @Override
        public void run() {
          /*  Intent splash = new Intent(SplashActivity.this, HomeActivity.class);
            // We set these flags so the user cannot return to the SplashScreen
            splash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(splash);*/
        }
    };
}
