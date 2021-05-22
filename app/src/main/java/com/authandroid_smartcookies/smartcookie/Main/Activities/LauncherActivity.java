package com.authandroid_smartcookies.smartcookie.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.R;
import com.authandroid_smartcookies.smartcookie.Util.Utilities;

public class LauncherActivity extends AppCompatActivity {
    //TODO have tales for males thing be dynamic from string array?
    public static boolean pref_paintTitles;
    Animation launcher_shaker;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initSharedPrefs();
    }
    //SHARED PREFERENCES INITIALIZATIONS
    private void initSharedPrefs(){
        //Colored Titles for recipes
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        LauncherActivity.pref_paintTitles = sharedPreferences.getBoolean("coloredTitles", true);
        //Application theme override
        Utilities.setDeviceThemeMode(this,
                sharedPreferences.getString("pref_theme", "auto"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);
        SenpaiDB db = SenpaiDB.getInstance(this);
        db.openDatabase();
        if (SenpaiDB.updated)
            Utilities.clearGlideCache(getApplicationContext());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        launcher_shaker = AnimationUtils.loadAnimation(this, R.anim.shaker);
        final ImageView shakerImage = findViewById(R.id.launcher_shaker_imageView);
        shakerImage.setAnimation(launcher_shaker);

        Intent intent = Utilities.restorePrefData(this.getApplicationContext())
                ? new Intent(LauncherActivity.this, MainActivity.class)
                : new Intent(LauncherActivity.this, IntroActivity.class);

        Intent testingIntroIntent = new Intent(LauncherActivity.this, IntroActivity.class);

        //todo change this to 1500, debug only 0 for now so we get inside the app quickly
        new Handler().postDelayed(() -> {
            startActivity(intent);
            finish();
        }, 1500);
    }
}