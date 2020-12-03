package com.nz.nztravelmate.startup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.nz.nztravelmate.R;
import com.nz.nztravelmate.utils.LocaleManager;
import com.nz.nztravelmate.utils.PrefConstants;
import com.nz.nztravelmate.utils.Preferences;

import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission_group.CAMERA;

public class LanguageSelectionActivity extends AppCompatActivity {
Context context=this;
Button btnChinese,btnEnglish;
Preferences preferences;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);
        preferences=new Preferences(context);
        initUI();
       // accessPermission();
      //  if (!checkPermission()) {
            requestPermission();
      //  }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CALL_PHONE}, PERMISSION_REQUEST_CODE);
    }
    private void initUI() {
        btnChinese=findViewById(R.id.btnChinese);
        btnEnglish=findViewById(R.id.btnEnglish);

        btnChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.LANGUAGE,"Chinese");
                preferences.putInt(PrefConstants.LANGUAGE_ID,2);
                setNewLocale(LocaleManager.LANGUAGE_CHINESE, true);
            }
        });
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.LANGUAGE,"English");
                preferences.putInt(PrefConstants.LANGUAGE_ID,1);
                setNewLocale(LocaleManager.LANGUAGE_ENGLISH, true);
            }
        });

    }


    private boolean setNewLocale(String language, boolean restartProcess) {
        LocaleManager.setNewLocale(context, language);

        Intent i = new Intent(context, CitySelectionActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

        if (restartProcess) {
            System.exit(0);
        } else {
           // Toast.makeText(this, getResources().getString(R.string.activityRestart) + "", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted)
                       // Snackbar.make(LanguageSelectionActivity.this, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(context,"Permission Granted, Now you can access location data and call.",Toast.LENGTH_SHORT).show();
                    else {
                     // requestPermission();
                        Toast.makeText(context,"Permission Denied, You cannot access location data and call.",Toast.LENGTH_SHORT).show();

                     //   Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CALL_PHONE},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LanguageSelectionActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    }

