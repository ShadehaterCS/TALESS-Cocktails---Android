package com.authandroid_smartcookies.smartcookie.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.Main.Fragments.HomeFragment;
import com.authandroid_smartcookies.smartcookie.R;
import com.authandroid_smartcookies.smartcookie.Util.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        SenpaiDB db = SenpaiDB.getInstance(this);
        db.openDatabase();
        if (SenpaiDB.updated)
            Utilities.clearGlideCache(getApplicationContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //clearApplicationDataDebugOnly();
        super.onCreate(savedInstanceState);

        //ContentView
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.mainFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        FragmentManager manager = this.getSupportFragmentManager();
        //todo documentation pls
        //Don't reload the fragment if it's already there
        bottomNavigationView.setOnNavigationItemSelectedListener(item->{
            navController.popBackStack();
            navController.navigate(item.getItemId());
            return true;
        });

        bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
            if (item.getTitle().toString().contentEquals("Home")){
                HomeFragment fragment =
                        (HomeFragment) manager.findFragmentById(R.id.mainFragment)
                        .getChildFragmentManager().getFragments().get(0);
                fragment.scrollToTop();
            }
        });

        Utilities.setAnimationAndExcludeTargets(getWindow());
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

