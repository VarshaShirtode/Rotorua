 package com.nz.nztravelmate.map;

 import android.Manifest;
 import android.content.Context;
 import android.content.Intent;
 import android.content.pm.PackageManager;
 import android.graphics.Bitmap;
 import android.graphics.Canvas;
 import android.graphics.Color;
 import android.graphics.drawable.Drawable;
 import android.net.Uri;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.widget.ImageView;
 import android.widget.Toast;

 import androidx.annotation.DrawableRes;
 import androidx.appcompat.content.res.AppCompatResources;
 import androidx.core.content.ContextCompat;
 import androidx.core.graphics.drawable.DrawableCompat;
 import androidx.fragment.app.FragmentActivity;

 import com.google.android.gms.maps.CameraUpdateFactory;
 import com.google.android.gms.maps.GoogleMap;
 import com.google.android.gms.maps.OnMapReadyCallback;
 import com.google.android.gms.maps.SupportMapFragment;
 import com.google.android.gms.maps.model.BitmapDescriptor;
 import com.google.android.gms.maps.model.BitmapDescriptorFactory;
 import com.google.android.gms.maps.model.CameraPosition;
 import com.google.android.gms.maps.model.LatLng;
 import com.google.android.gms.maps.model.Marker;
 import com.google.android.gms.maps.model.MarkerOptions;
 import com.nz.nztravelmate.R;
 import com.nz.nztravelmate.dashboard.ServiceDetailsActivity;
 import com.nz.nztravelmate.model.Data;
 import com.nz.nztravelmate.model.Maps;
 import com.nz.nztravelmate.utils.PrefConstants;
 import com.nz.nztravelmate.utils.Preferences;

 import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    Context context=this;
    private GoogleMap mMap;
    String Lattitude="";
    String Longitude="";
    String LatLong="";
    Preferences preferences;
    String Label="Marker";
    ArrayList<Maps> mapList;
    ArrayList<Data> latlongList;
    ArrayList<Maps> latlongListMap;

    ArrayList<Data> businessList;

    boolean fromList=false;
    ImageView imgDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        imgDirection=findViewById(R.id.imgDirection);

        preferences=new Preferences(context);
        Intent intent=getIntent();
        if (intent.hasExtra("LABEL")) {
            Label=intent.getExtras().getString("LABEL");
        }
        if (intent.hasExtra("MapList")) {
            businessList= (ArrayList<Data>) intent.getSerializableExtra("MapList");
            //Log.v("@MAPLISTSIZE",""+mapList.size());
        }
        if (intent.hasExtra("LATLONG")) {
            LatLong = intent.getExtras().getString("LATLONG");
            if (LatLong.equals("LISTMAP"))
            {
                fromList=true;
                latlongList=new ArrayList<Data>();
                latlongListMap=new ArrayList<Maps>();

                if(businessList.size()!=0) {
                    for (int i = 0; i < businessList.size(); i++) {
                        Log.v("@MPALIST", "Maps " + businessList.get(i).getMap());
                    }
                }
                for (int i=0;i<businessList.size();i++)
                {
                   // Log.v("@MAPLISTSIZE",businessList.get(i).getCategory_id()+" sdd "+preferences.getString(PrefConstants.CATEGORY_ID));
                    if (businessList.get(i).getCategory_id().trim().equals(preferences.getString(PrefConstants.CATEGORY_ID))) {

                        if (!businessList.get(i).getMap().equals("")) {
                            LatLong = businessList.get(i).getMap();
                            if (LatLong.contains("° S")) {
                                LatLong = LatLong.replace("° S", "");
                                LatLong = "-" + LatLong;
                            }
                            LatLong = LatLong.replace("° N", "");
                            if (LatLong.contains("° W")) {
                                LatLong = LatLong.replace("° W", "");
                                LatLong = "-" + LatLong;
                            }
                            String LatLongs = LatLong.replace("° E", "");
                            Maps m = new Maps();
                            m.setName(businessList.get(i).getName());
                            m.setMap(LatLongs);
                            m.setCategory_id(businessList.get(i).getCategory_id());
                            latlongListMap.add(m);
                            latlongList.add(businessList.get(i));
                           // Log.v("@MAPLISTSIZE","Same "+latlongList.size()+ " "+mapList.get(i).getCategory_id()+" sdd "+preferences.getString(PrefConstants.CATEGORY_ID));

                        }
                    }
                }
            }else if (LatLong.equals("ALL"))
            {
                fromList=true;
                latlongList=new ArrayList<>();

                latlongListMap=new ArrayList<Maps>();
                for (int i=0;i<businessList.size();i++)
                {

                        if (!businessList.get(i).getMap().equals("")) {
                            LatLong = businessList.get(i).getMap();
                            if (LatLong.contains("° S")) {
                                LatLong = LatLong.replace("° S", "");
                                LatLong = "-" + LatLong;
                            }
                            LatLong = LatLong.replace("° N", "");
                            if (LatLong.contains("° W")) {
                                LatLong = LatLong.replace("° W", "");
                                LatLong = "-" + LatLong;
                            }
                            String LatLongs = LatLong.replace("° E", "");
                           Maps m = new Maps();
                            m.setName(businessList.get(i).getName());
                            m.setMap(LatLongs);
                            m.setCategory_id(businessList.get(i).getCategory_id());
                            latlongListMap.add(m);
                            latlongList.add(businessList.get(i));
                         //   Log.v("@MAPLISTSIZE","Same "+latlongList.size()+ " "+mapList.get(i).getCategory_id()+" sdd "+preferences.getString(PrefConstants.CATEGORY_ID));

                        }
                    }

            }else {
                // LatLong="-38.1915498,176.0346385";
                Log.v("@RESPMAP", "MAPLatlong " + LatLong);
                // LatLong="nelson";
                if (!LatLong.equals("")) {
                    if (LatLong.contains("° S")) {
                        LatLong = LatLong.replace("° S", "");
                        LatLong = "-" + LatLong;
                    }
                    LatLong = LatLong.replace("° N", "");
                    if (LatLong.contains("° W")) {
                        LatLong = LatLong.replace("° W", "");
                        LatLong = "-" + LatLong;
                    }
                    LatLong = LatLong.replace("° E", "");
                    String[] values = LatLong.split(",");
                    Log.v("@RESPMAP", "Converted " + values.length);
                    if (values.length != 2) {
                        Lattitude = "";
                        Longitude = "";
                        Toast.makeText(MapsActivity.this, "Invalid Location,", Toast.LENGTH_SHORT).show();
                    } else {
                        Lattitude = values[0];
                        Longitude = values[1];
                    }
                    Log.v("@RESPMAP", "Converted " + Lattitude + " " + Longitude);
                } else {
                    // Lattitude="-37.7891";
                    // Longitude="175.2597";
                    Toast.makeText(MapsActivity.this, "Location is not available", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
           // Lattitude="-37.7891";
           // Longitude="175.2597";
            LatLng sydney = new LatLng(-34, 151);
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(true);
        //mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        mMap.setOnMarkerClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if(fromList==true&&latlongListMap.size()!=0)
        {
            for (int i=0;i<latlongListMap.size();i++)
            {
                String[] values = latlongListMap.get(i).getMap().split(",");
                Log.v("@RESPMAP", latlongListMap.get(i).getName()+" Converted " + values.length);
                if (values.length != 2) {
                    Lattitude = "";
                    Longitude = "";
                    Toast.makeText(MapsActivity.this, "Invalid Location,", Toast.LENGTH_SHORT).show();
                } else {
                    Lattitude = values[0];
                    Longitude = values[1];

                    if(!Lattitude.equals("")&&!Longitude.equals("")) {
                        double lat = Double.parseDouble(Lattitude);
                        double lng = Double.parseDouble(Longitude);

                        // Add a marker in Sydney and move the camera-41.2767048,174.7719213
                        // LatLng sydney = new LatLng(-38.1534,176.2403);
                        LatLng sydney = new LatLng(lat, lng);
                        LatLng city=new LatLng(-38.1534,176.2403);
                        Log.v("@RESPMAP","In ready "+sydney.latitude+" "+sydney.longitude);
                        if(latlongListMap.get(i).getCategory_id().equals("Activities")||latlongListMap.get(i).getCategory_id().equals("活动项目")) {
                          //  mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(sydney).title(latlongList.get(i).getName()));
                            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.activity)).position(sydney).title(latlongList.get(i).getName()));

                        }else if(latlongListMap.get(i).getCategory_id().equals("Food")||latlongListMap.get(i).getCategory_id().equals("餐饮")) {
                            mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(this, R.drawable.food,Color.parseColor("#008000"))).position(sydney).title(latlongList.get(i).getName()));
                           // mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(sydney).title(latlongList.get(i).getName()));
                        }else if(latlongListMap.get(i).getCategory_id().equals("Shopping")||latlongListMap.get(i).getCategory_id().equals("购物")) {
                            mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(this, R.drawable.shops, Color.parseColor("#FF0000"))).position(sydney).title(latlongList.get(i).getName()));

                            //mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).position(sydney).title(latlongList.get(i).getName()));
                        }else if(latlongListMap.get(i).getCategory_id().equals("Accommodation")||latlongListMap.get(i).getCategory_id().equals("住所")) {
                            mMap.addMarker(new MarkerOptions().icon(bitmapDescriptorFromVector(this, R.drawable.bed, Color.parseColor("#000000"))).position(sydney).title(latlongList.get(i).getName()));

                            //mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.black)).position(sydney).title(latlongList.get(i).getName()));
                        }
                        // mMap.addMarker(new MarkerOptions().position(city).title("Label"));
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                // TODO Auto-generated method stub
                                if (fromList==true) {
                                    LatLng latLong = marker.getPosition();
                                    String title = marker.getTitle();
                                    for (int i = 0; i < latlongList.size(); i++) {
                                        //  Log.v("LATLIST",latlongList.get(i).getName()+" "+latlongList.get(i).getStaff());
                                        if (latlongList.get(i).getName().equals(title)) {
                                            Intent intent = new Intent(context, ServiceDetailsActivity.class);
                                            intent.putExtra("ItemObject", latlongList.get(i));
                                            context.startActivity(intent);
                                        }
                                    }
                                }
                            }
                        });
                    }

                    /*mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            return false;
                        }

                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            String venueID = .get(marker.getId());
                            String venueName = marker.getTitle();
                            Intent intent = new Intent(MapActivity.this, NewActivity.class);
                            intent.putExtra(VENUE_NAME, venueName);
                            intent.putExtra(VENUE_ID, venueID);
                            startActivity(intent);

                            return false;
                        }
                    });*/
                }

            }

        }else{
            if(!Lattitude.equals("")&&!Longitude.equals("")) {
                double lat = Double.parseDouble(Lattitude);
                double lng = Double.parseDouble(Longitude);
                // Add a marker in Sydney and move the camera-41.2767048,174.7719213
              //  LatLng sydney = new LatLng(17.6805,74.0183);
                LatLng sydney = new LatLng(lat, lng);

                MarkerOptions markeroption=new MarkerOptions()
                        .position(sydney)
                        .title(Label);
                Marker marker=mMap.addMarker(markeroption);

                 imgDirection.setVisibility(View.VISIBLE);
                 imgDirection.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Uri navigationIntentUri = Uri.parse("google.navigation:q=" + lat +"," + lng);//creating intent with latlng
                         Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                         mapIntent.setPackage("com.google.android.apps.maps");
                         startActivity(mapIntent);
                     }
                 });

                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(16).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        if (!marker.isInfoWindowShown())
                        {
                            marker.showInfoWindow();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });


                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        marker.showInfoWindow();
                     //   Toast.makeText(context,"CLciked",Toast.LENGTH_SHORT).show();
                       /* Uri navigationIntentUri = Uri.parse("google.navigation:q=" + lat +"," + lng);//creating intent with latlng
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);*/
                        return true;
                    }
                });

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        // TODO Auto-generated method stub
                        if (fromList==true) {
                            LatLng latLong = marker.getPosition();
                            String title = marker.getTitle();
                            for (int i = 0; i < latlongList.size(); i++) {
                                //  Log.v("LATLIST",latlongList.get(i).getName()+" "+latlongList.get(i).getStaff());
                                if (latlongList.get(i).getName().equals(title)) {
                                    Intent intent = new Intent(context, ServiceDetailsActivity.class);
                                    intent.putExtra("ItemObject", latlongList.get(i));
                                    context.startActivity(intent);
                                }
                            }
                        }else{
                           // Toast.makeText(context,"CLciked",Toast.LENGTH_SHORT).show();
                            Uri navigationIntentUri = Uri.parse("google.navigation:q=" + lat +"," + lng);//creating intent with latlng
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    }
                });
            }
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

      /*  if (fromList==true) {
            LatLng latLong = marker.getPosition();
            String title = marker.getTitle();
            for (int i = 0; i < businessList.size(); i++) {
                //  Log.v("LATLIST",latlongList.get(i).getName()+" "+latlongList.get(i).getStaff());
                if (businessList.get(i).getName().equals(title)) {
                    Intent intent = new Intent(context, ServiceDetailsActivity.class);
                    intent.putExtra("ItemObject", businessList.get(i));
                    context.startActivity(intent);
                }
            }
        }*/

        return false;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId, int color) {
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.marker_ic);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        Drawable background = ContextCompat.getDrawable(context, R.drawable.marker_ic);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
       // double left = (background.getIntrinsicWidth() - vectorDrawable.getIntrinsicWidth()) / 2 ;
        //double top = (background.getIntrinsicHeight() - vectorDrawable.getIntrinsicHeight()) / 3;
        vectorDrawable.setBounds((background.getIntrinsicWidth() - vectorDrawable.getIntrinsicWidth()) / 2, (background.getIntrinsicHeight() - vectorDrawable.getIntrinsicHeight()) / 3, ((background.getIntrinsicHeight() - vectorDrawable.getIntrinsicWidth()) / 2) + vectorDrawable.getIntrinsicWidth(), ((background.getIntrinsicHeight() - vectorDrawable.getIntrinsicHeight()) / 3) + vectorDrawable.getIntrinsicHeight()-10);
      //  vectorDrawable.setBounds((background.getIntrinsicWidth() - vectorDrawable.getIntrinsicWidth()) / 2, (background.getIntrinsicHeight() - vectorDrawable.getIntrinsicHeight()) / 3, vectorDrawable.getIntrinsicWidth()+10, vectorDrawable.getIntrinsicHeight()+10);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}