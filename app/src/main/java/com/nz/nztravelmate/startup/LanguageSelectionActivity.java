package com.nz.nztravelmate.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nz.nztravelmate.R;
import com.nz.nztravelmate.utils.LocaleManager;
import com.nz.nztravelmate.utils.PrefConstants;
import com.nz.nztravelmate.utils.Preferences;

import java.util.Locale;

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
    }

    private void initUI() {
        btnChinese=findViewById(R.id.btnChinese);
        btnEnglish=findViewById(R.id.btnEnglish);

        btnChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewLocale(LocaleManager.LANGUAGE_CHINESE, true);
            }
        });
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
