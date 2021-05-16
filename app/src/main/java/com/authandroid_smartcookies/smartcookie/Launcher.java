package com.authandroid_smartcookies.smartcookie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        launcher_shaker = AnimationUtils.loadAnimation(this,R.anim.shaker);


        final ImageView image1 = findViewById(R.id.image1);
        image1.setAnimation(launcher_shaker);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Launcher.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}