package com.auth.TALESS.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.auth.TALESS.R;
import com.auth.TALESS.Util.Utilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Used as the entry point to the application
 * Shared preferences are initialized here and are able to be accessed throughout the application
 * An intent is created dynamically based on if the app is opened for the first time.
 * If it's the first time then the user is redirected to the Intro Activity before going to MainActivity
 */
public class LauncherActivity extends AppCompatActivity {
    public static boolean pref_paintTitles;
    private final int DELAY = 1500;

    private CountDownTimer stringTimer;
    private TextView title;
    Animation launcher_shaker;

    //SHARED PREFERENCES INITIALIZATIONS
    private void initSharedPrefs() {
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
        initSharedPrefs();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        title = findViewById(R.id.launcherTV);

        launcher_shaker = AnimationUtils.loadAnimation(this, R.anim.shaker);
        final ImageView shakerImage = findViewById(R.id.launcher_shaker_imageView);
        shakerImage.setAnimation(launcher_shaker);

        Intent intent = Utilities.restorePrefData(this.getApplicationContext())
                ? new Intent(LauncherActivity.this, MainActivity.class)
                : new Intent(LauncherActivity.this, IntroActivity.class);

        Intent testingIntroIntent = new Intent(LauncherActivity.this, IntroActivity.class);

        new Handler().postDelayed(() -> {
            startActivity(intent);
            finish();
        }, DELAY);

        List<String> strings = Arrays.asList("Classic Aviation", "Floradora",
                "Bloody Caesar", "Blue Hawaii", "Brown Derby", "Whiskey Smash",
                "Classic Daiquiri", "Limoncello Mojito", "Apple Martini", "Bloody Maria",
                "Agave Margarita", "Basil Gimlet", "Classic Mojito",
                "Tequila Sour", "TALES FOR MALES");
        Collections.shuffle(strings);

        stringTimer = new CountDownTimer(DELAY, 200) {
            int i = 0;
            @Override
            public void onTick(long millisUntilFinished) {
                title.setText(strings.get(i));
                i++;
            }
            @Override
            public void onFinish() {}
        }.start();
    }

    @Override
    protected void onDestroy() {
        stringTimer.cancel();
        super.onDestroy();
    }
}