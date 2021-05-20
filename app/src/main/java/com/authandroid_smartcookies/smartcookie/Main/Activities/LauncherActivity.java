package com.authandroid_smartcookies.smartcookie.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.authandroid_smartcookies.smartcookie.R;
import com.authandroid_smartcookies.smartcookie.Utilities;

public class LauncherActivity extends AppCompatActivity {

    Animation launcher_shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        launcher_shaker = AnimationUtils.loadAnimation(this, R.anim.shaker);
        final ImageView shakerImage = findViewById(R.id.image1);
        shakerImage.setAnimation(launcher_shaker);

        Intent intent = Utilities.restorePrefData(this.getApplicationContext())
                ? new Intent(LauncherActivity.this, HomeActivity.class)
                : new Intent(LauncherActivity.this, IntroActivity.class);

        Intent testingIntroIntent = new Intent(LauncherActivity.this, IntroActivity.class);
        //todo change this to 1500, debug only 0 for now so we get inside the app quickly
        //check deprecation
        new Handler().postDelayed(() -> {
            startActivity(intent);
            finish();
        }, 1000);
    }
}