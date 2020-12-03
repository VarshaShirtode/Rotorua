package com.nz.nztravelmate.map;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nz.nztravelmate.R;
import com.nz.nztravelmate.utils.PrefConstants;
import com.nz.nztravelmate.utils.Preferences;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Context context=this;
    private GoogleMap mMap;
    String Lattitude="";
    String Longitude="";
    String LatLong="";
    Preferences preferences;
    String Label="Marker";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        preferences=new Preferences(context);
       Intent intent=getIntent();
        if (intent.hasExtra("LABEL")) {
            Label=intent.getExtras().getString("LABEL");
        }
        if (intent.hasExtra("LATLONG")) {
            LatLong = intent.getExtras().getString("LATLONG");
            //LatLong="40.9006° S, 174.8860° E";
            Log.v("@RESPMAP", "MAPLatlong "+LatLong);
           // LatLong="nelson";
            if (!LatLong.equals(""))
            {
                if (LatLong.contains("° S")) {
                    LatLong = LatLong.replace("° S", "");
                    LatLong="-"+LatLong;
                }
                LatLong = LatLong.replace("° N", "");
                if (LatLong.contains("° W")) {
                    LatLong = LatLong.replace("° W", "");
                    LatLong="-"+LatLong;
                }
                LatLong = LatLong.replace("° E", "");
                String[] values = LatLong.split(",");
                Log.v("@RESPMAP", "Converted " + values.length);
                if(values.length!=2)
                {
                    Lattitude="";
                    Longitude="";
                    Toast.makeText(MapsActivity.this, "Invalid Location,", Toast.LENGTH_SHORT).show();
                }else {
                    Lattitude = values[0];
                    Longitude = values[1];
                }
                Log.v("@RESPMAP", "Converted " + Lattitude + " " + Longitude);
            }else{
               // Lattitude="-37.7891";
               // Longitude="175.2597";
                Toast.makeText(MapsActivity.this, "Location is not available", Toast.LENGTH_SHORT).show();
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
        if(!Lattitude.equals("")&&!Longitude.equals("")) {
            double lat = Double.parseDouble(Lattitude);
            double lng = Double.parseDouble(Longitude);

            // Add a marker in Sydney and move the camera-41.2767048,174.7719213
            // LatLng sydney = new LatLng(-38.1534,176.2403);
            LatLng sydney = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(sydney).title(Label));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}