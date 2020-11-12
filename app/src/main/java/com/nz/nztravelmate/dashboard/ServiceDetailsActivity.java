package com.nz.nztravelmate.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.nz.nztravelmate.R;
import com.nz.nztravelmate.map.MapsActivity;
import com.nz.nztravelmate.model.Data;
import com.nz.nztravelmate.utils.LocaleManager;
import com.nz.nztravelmate.utils.PrefConstants;
import com.nz.nztravelmate.webservice.ApiConstants;
import com.squareup.picasso.Picasso;

public class ServiceDetailsActivity extends AppCompatActivity implements View.OnClickListener {
Context context=this;
    TabLayout tabLayout;
    FragmentIntroduction fragmentDetail;
    FragmentDiscount fragmentDiscount;
    FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    LinearLayout llA,llB;
    View vA,vB;
    RelativeLayout rlContainer;
    TextView txtA,txtB;
    TextView txtName, txtAddress,txtDistance,txtTime,txtService,txtStatus;
    static Data food;
    ImageView imgBack,imgCall,imgLocation,imgTab;
    TextView txtTitle;

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
        tabLayout.addTab(tabLayout.newTab().setText("Data"));// Data And Drink
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
        imgBack.setOnClickListener(this);
        llA.setOnClickListener(this);
        llB.setOnClickListener(this);
        imgCall.setOnClickListener(this);
        imgLocation.setOnClickListener(this);
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
        txtStatus=findViewById(R.id.txtStatus);
        imgBack=findViewById(R.id.imgBack);
        txtTitle=findViewById(R.id.txtTitle);
        imgLocation=findViewById(R.id.imgLocation);
        imgCall=findViewById(R.id.imgCall);
        imgTab=findViewById(R.id.imgTab);


//GetData from list and display
        food=new Data();
        Intent intent=getIntent();
        food= (Data) intent.getExtras().getSerializable("ItemObject");
        txtName.setText(food.getBusiness_name_english());
        txtAddress.setText(food.getAddress());
        txtTitle.setText(food.getCategory_id());
        txtStatus.setText(food.getAvg_doller());
       // txtDistance.setText(food.getAvg_time());
       // txtTime.setText(food.getBusiness_hrs());
        if (food.getAvg_time()==null)
        {
            txtDistance.setText("No Data Available");
            //  holder.txtDistance.setVisibility(View.GONE);
        }else{
            txtDistance.setText(food.getAvg_time());
            // holder.txtDistance.setVisibility(View.VISIBLE);
        }

        if (food.getBusiness_hrs()==null)
        {
            txtTime.setText("No Data Available");
            //  holder.txtTime.setVisibility(View.GONE);
        }else{
            String name=food.getBusiness_hrs().trim();
            name=name.replace("<p>","");
            name=name.replace("</p>","");
            txtTime.setText(name);

        }

        String Service="";
       /* if (food.getPayment()!=null)
        {
            String data=food.getPayment();
            int available= Integer.parseInt(data.substring(0,1));

            if (available==1)
            {
                if (data.length()!=1) {
                    String msg = data.substring(2, data.length() - 1);
                    Service =Service+ "Payment" + " : " + "Yes," + msg + "\n";
                }else{

                    Service = Service+"Payment" + " : " + "Yes"+ "\n";
                }
            }else{
                if (data.length()!=1) {
                    String msg = data.substring(2, data.length() - 1);
                    Service =Service+ "Payment" + " : " + "No," + msg + "\n";
                }else{
                    Service =Service+ "Payment" + " : " + "No"+ "\n";
                }
            }
        }*/


        if (!food.getPayment_id().isEmpty())
        {
            Service = Service+ "Payment" + " : " ;

            for (int i=0;i<food.getPayment_id().size();i++)
            {
                Service = Service+ food.getPayment_id().get(i).getPayment_method_name()+ ", ";
            }
            if (!Service.equals(""))
            {
                char e=Service.charAt(Service.length()-1);
                if (e==' ')
                {
                    Service=Service.substring(0,Service.length()-2);
                }
            }
            Service = Service+ "\n" ;

        }else{
            // Service = Service+ "Payment" + " : " + "Yes"+ "\n";

        }
        if (food.getWifi()!=null)
        {
            String data=food.getWifi();
            int available= Integer.parseInt(data.substring(0,1));
            if (available==1)
            {
                if (data.length()!=1) {
                    String msg = data.substring(2, data.length() - 1);
                    Service =Service+ "Free Wifi" + " : " + "Yes," + msg + "\n";
                }else{
                    Service =Service+ "Free Wifi" + " : " + "Yes"+ "\n";
                }
            }else{
                if (data.length()!=1) {
                    String msg = data.substring(2, data.length() - 1);
                    Service =Service+ "Free Wifi" + " : " + "No," + msg + "\n";
                }else{
                    Service =Service+ "Free Wifi" + " : " + "No"+ "\n";
                }
            }
        }
        if (food.getParking()!=null)
        {
            String data=food.getParking();
            int available= Integer.parseInt(data.substring(0,1));
            if (available==1)
            {
                if (data.length()!=1) {
                    String msg = data.substring(2, data.length() - 1);
                    Service =Service+ "Free Parking" + " : " + "Yes," + msg + "\n";
                }else{
                    Service =Service+ "Free Parking" + " : " + "Yes"+ "\n";
                }
            }else{
                if (data.length()!=1) {
                    String msg = data.substring(2, data.length() - 1);
                    Service =Service+ "Free Parking" + " : " + "No," + msg + "\n";
                }else{
                    Service =Service+ "Free Parking" + " : " + "No"+ "\n";
                }
            }
        }
        if (food.getMandarin_speaking_staff()!=null)
        {
            String data=food.getMandarin_speaking_staff();
            int available= Integer.parseInt(data.substring(0,1));
            if (available==1)
            {
                if (data.length()!=1) {
                    String msg = data.substring(2, data.length() - 1);
                    Service =Service+ "Mandarin Speaking Staff" + " : " + "Yes," + msg + "\n";
                }else{
                    Service =Service+ "Mandarin Speaking Staff" + " : " + "Yes"+ "\n";
                }
            }else{
                if (data.length()!=1) {
                    String msg = data.substring(2, data.length() - 1);
                    Service =Service+ "Mandarin Speaking Staff" + " : " + "No," + msg + "\n";
                }else{
                    Service =Service+ "Mandarin Speaking Staff" + " : " + "No"+ "\n";
                }
            }
        }

        if (!Service.equals(""))
        {
            Service=Service.trim();
           /* char s=Service.charAt(0);
            char e=Service.charAt(Service.length()-1);
            if (s==' ')
            {
                Service=Service.substring(3);
            }
            if (e==' ')
            {
                Service=Service.substring(0,Service.length()-3);
            }*/
          txtService.setText(Service);
        }else{
            txtService.setText("No Service Available");
        }
        if (food.getImages()!=null) {
            loadImage(imgTab, food.getImages());
        }

        //txtProfile = findViewById(R.id.txtProfile);
    }
    private void loadImage(ImageView profileImageView, String imagePath) {
        String filePath = ApiConstants.GET_BUSINESS_LAND_IMAGE + imagePath;
        // String filePath ="https://nztravelmate.com/uploads/city_images/1604153405.jpg";
        Log.i("@RESP", "ImageUrl: " + filePath);
        Picasso.with(context)
                .load(filePath.replace(" ", "%20"))
                .placeholder(R.drawable.splash)
                //.transform(new CircleTransform())
                .into(profileImageView);
    }
    private void onCall(String finalMobile) {
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    (Activity) context,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            context.startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + finalMobile)));
        }
    }
    public Data getJob(){
        return food;
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgBack:
                finish();
                break;

            case R.id.imgCall:
                if (food.getPhone()!=null) {
                    onCall(food.getPhone());
                }else{
                    Toast.makeText(context, "Phone number is not available", Toast.LENGTH_SHORT).show();
                }
                //onCall("7757037538");
                break;

            case R.id.imgLocation:
                if (food.getMap()!=null)
                {
                    Intent intent=new Intent(context, MapsActivity.class);
                    intent.putExtra("LATLONG",food.getMap());
                    intent.putExtra("LABEL",food.getBusiness_name_english());

                    startActivity(intent);
                }else {
                     Toast.makeText(context, "Location is not available", Toast.LENGTH_SHORT).show();
                }break;

            case R.id.llA:
                /*Bundle bundle = new Bundle();
                bundle.putString("Description", String.valueOf(food));
                fragmentDetail.setArguments(bundle);*/
                callFragment("C", fragmentDetail);
                vA.setVisibility(View.VISIBLE);
                vB.setVisibility(View.GONE);
                txtA.setTextColor(getResources().getColor(R.color.colorSkyBlue));
                txtB.setTextColor(getResources().getColor(R.color.colorBlack));
                break;

            case R.id.llB:
                callFragment("B", fragmentDiscount);
                vA.setVisibility(View.GONE);
                vB.setVisibility(View.VISIBLE);

                txtA.setTextColor(getResources().getColor(R.color.colorBlack));
                txtB.setTextColor(getResources().getColor(R.color.colorSkyBlue));
                break;

            default:
        }

    }

    public static Data getData() {
        
        return food;
    }
}
