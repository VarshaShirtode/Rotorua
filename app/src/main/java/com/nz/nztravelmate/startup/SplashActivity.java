package com.nz.nztravelmate.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nz.nztravelmate.R;
import com.nz.nztravelmate.dashboard.DashboardActivity;
import com.nz.nztravelmate.model.City;
import com.nz.nztravelmate.utils.LocaleManager;

public class SplashActivity extends AppCompatActivity {
   RelativeLayout rlMain;
   TextView txtEnter,txtMainTitle,txtCity;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        /*View backgroundImage = findViewById(R.id.rlMain);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(190);*/
        Intent intent=getIntent();
        City city= (City) intent.getExtras().getSerializable("CityObject");


        txtEnter=findViewById(R.id.txtEnter);
        txtMainTitle=findViewById(R.id.txtMainTitle);
        txtCity=findViewById(R.id.txtCity);

        txtMainTitle.setText(city.getName());
        txtCity.setText(city.getName());


        txtEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SplashActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

    }
}
