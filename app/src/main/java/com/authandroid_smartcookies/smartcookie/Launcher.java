package com.authandroid_smartcookies.smartcookie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Launcher extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Handler handler ;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(Launcher.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }, 3000);




    }
}