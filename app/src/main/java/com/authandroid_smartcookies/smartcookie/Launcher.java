package com.authandroid_smartcookies.smartcookie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Launcher extends AppCompatActivity {

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

        launcher_shaker = AnimationUtils.loadAnimation(this,R.anim.shaker);
        final ImageView shakerImage = findViewById(R.id.image1);
        shakerImage.setAnimation(launcher_shaker);

        //todo change this to 1500, debug only 0 for now so we get inside the app quickly
        //check deprecation
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Launcher.this, Intro.class);
            startActivity(intent);
            finish();
        }, 1000);
    }
}