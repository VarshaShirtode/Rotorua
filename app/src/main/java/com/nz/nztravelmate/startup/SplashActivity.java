package com.nz.nztravelmate.startup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nz.nztravelmate.R;
import com.nz.nztravelmate.dashboard.DashboardActivity;
import com.nz.nztravelmate.model.Category;
import com.nz.nztravelmate.model.City;
import com.nz.nztravelmate.model.UserResponse;
import com.nz.nztravelmate.utils.LocaleManager;
import com.nz.nztravelmate.utils.PrefConstants;
import com.nz.nztravelmate.utils.Preferences;
import com.nz.nztravelmate.webservice.ApiConstants;
import com.nz.nztravelmate.webservice.ApiService;
import com.nz.nztravelmate.webservice.RetrofitClient;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class SplashActivity extends AppCompatActivity {
    Context context=this;
   RelativeLayout rlMain;
   TextView txtEnter,txtMainTitle,txtCity,txtDescription,txtVisit;
   Preferences preferences;
   ArrayList<Category> categoryList;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        preferences=new Preferences(context);

        /*View backgroundImage = findViewById(R.id.rlMain);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(190);*/
        categoryList=new ArrayList<>();
        callGetCategoryWs();


        Intent intent=getIntent();
        City city= (City) intent.getExtras().getSerializable("CityObject");


        txtEnter=findViewById(R.id.txtEnter);
        txtMainTitle=findViewById(R.id.txtMainTitle);
        txtCity=findViewById(R.id.txtCity);
        txtDescription=findViewById(R.id.txtDescription);
        txtVisit=findViewById(R.id.txtVisit);


       // txtMainTitle.setText(getResources().getString(R.string.app_name));
        txtCity.setText(city.getName());
        txtMainTitle.setText(city.getName());
        if (city.getDescription()!=null)
        {
            txtDescription.setText(city.getDescription());
        }else{
            txtDescription.setText("");
        }
        //txtVisit.setText(Html.fromHtml("<small>Visit</small>")+"\n"+city.getName());
        txtVisit.setText(Html.fromHtml("<small>Visit</small><br>"+city.getName()));
        preferences.putString(PrefConstants.CITY_NAME,city.getName());
        preferences.putString(PrefConstants.CITY_ID,city.getId());
        preferences.putString(PrefConstants.CITY_MAP,city.getMap());

        /*preferences.putString(PrefConstants.CITY_MAP,"18.5204° N, 73.8567° E");
        preferences.putString(PrefConstants.CITY_MAP,"");*/

        txtEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SplashActivity.this, DashboardActivity.class);
                intent.putExtra("CategoryList",categoryList);
                startActivity(intent);
            }
        });

        View animatedView = findViewById(R.id.animated_view);
        if (city.getImage() != null) {
            loadImage((ImageView) animatedView, city.getImage());
        } else {
            // loadImage(animatedView, "https://st2.depositphotos.com/3104995/6728/i/950/depositphotos_67280377-stock-photo-rotorua-city-and-lake-landscape.jpg");
            // holder.imgCity.setImageResource(R.drawable.splash);
        }
        final int screenWidth = getScreenDimensions(this).x;
        final int waveImgWidth = animatedView.getBackground().getIntrinsicWidth();
        int animatedViewWidth = 0;
        while (animatedViewWidth < screenWidth) {
            animatedViewWidth += waveImgWidth;
        }
        animatedViewWidth += waveImgWidth;


        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) animatedView.getLayoutParams();
        layoutParams.width = animatedViewWidth;
        animatedView.setLayoutParams(layoutParams);


        Animation waveAnimation = new TranslateAnimation(0, -waveImgWidth, 0, 0);
        waveAnimation.setInterpolator(new LinearInterpolator());
        waveAnimation.setRepeatCount(Animation.INFINITE);
        waveAnimation.setDuration(15000);

        animatedView.startAnimation(waveAnimation);
    }

    public static Point getScreenDimensions(Context context) {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        return new Point(width, height);
    }
    private void loadImage(ImageView profileImageView, String imagePath) {
        String filePath = ApiConstants.GET_IMAGE + imagePath;
        // String filePath =imagePath;
        Log.i("@RESP", "ImageUrl: " + filePath);
        Picasso.with(context)
                .load(filePath.replace(" ", "%20"))
                .into( profileImageView);
    }
    private void callGetCategoryWs() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        ApiService apiService = RetrofitClient.getClient(context).create(ApiService.class);
        Call<UserResponse> call = apiService.getCategoryList();
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
                            categoryList.add(userResponse.getCategory().get(i));
                            Log.v("@RESP","Category "+categoryList.get(i).getId()+" "+categoryList.get(i).getName());
                        }
                        Gson gson = new Gson();
                        String json = gson.toJson(categoryList);
                        preferences.putString(PrefConstants.CATEGORY_LIST,json);
                    } else {
                        Toast.makeText(context, userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.v("@RESP", "RespCityException: " + ex.getLocalizedMessage());
                    Toast.makeText(context, ex.getLocalizedMessage(), LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Log.v("@RESP", "RespCityFail: "+t.getLocalizedMessage());
                Toast.makeText(context, t.getLocalizedMessage(), LENGTH_SHORT).show();
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
