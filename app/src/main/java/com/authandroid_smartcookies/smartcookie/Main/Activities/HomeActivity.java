package com.authandroid_smartcookies.smartcookie.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.Fade;
import android.view.View;

import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.Main.Fragments.MainMenuFragment;
import com.authandroid_smartcookies.smartcookie.R;
import com.authandroid_smartcookies.smartcookie.Util.Utilities;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    public static boolean pref_paintTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //clearApplicationDataDebugOnly();
        super.onCreate(savedInstanceState);
        SenpaiDB db = SenpaiDB.getInstance(this);
        db.openDatabase();
        if (SenpaiDB.updated)
            Utilities.clearGlideCache(getApplicationContext());

        setContentView(R.layout.activity_home);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Objects.requireNonNull(getSupportActionBar()).hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.mainFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        FragmentManager manager = this.getSupportFragmentManager();
        //Don't reload a fragment if it's already there
        
        bottomNavigationView.setOnNavigationItemSelectedListener(item->{
            //todo figure this out, although it's now working as intended tbh
            navController.popBackStack();
            navController.navigate(item.getItemId());
            return true;
        });

        bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
            if (item.getTitle().toString().contentEquals("Home")){
                MainMenuFragment fragment =
                        (MainMenuFragment) manager.findFragmentById(R.id.mainFragment)
                        .getChildFragmentManager().getFragments().get(0);
                fragment.scrollToTop();
            }
        });

        Utilities.setAnimationAndExcludeTargets(getWindow());
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        pref_paintTitles = sharedPreferences.getBoolean("coloredTitles", true);
    }

    /**
     * @why Clears the application data and crashes it
     * @caution Needs to be uncommented after ran once. Provides no use after that run anyway.
     */
    private void clearApplicationDataDebugOnly(){
        try {
            String pName = this.getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+pName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Close the database when app closes :)
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SenpaiDB.getInstance(this.getApplicationContext()).close();
    }
}

