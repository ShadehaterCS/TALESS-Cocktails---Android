package com.authandroid_smartcookies.smartcookie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.transition.Explode;
import android.view.View;

import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.Menu.Home.MainMenuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //clearApplicationDataDebugOnly();

        SenpaiDB db = SenpaiDB.getInstance(this);
        db.openDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Objects.requireNonNull(getSupportActionBar()).hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.mainFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        FragmentManager manager = this.getSupportFragmentManager();
        //Don't reload a fragment if it's already there
        bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
            if (item.getTitle().toString().contentEquals("Home")){
                MainMenuFragment fragment =
                        (MainMenuFragment) manager.findFragmentById(R.id.mainFragment)
                        .getChildFragmentManager().getFragments().get(0);
                fragment.scrollToTop();
            }
        });

        //todo add decor thing
        Explode explode = new Explode();
        getWindow().setEnterTransition(explode);
        getWindow().setExitTransition(explode);
    }

    private void clearApplicationDataDebugOnly(){
        try {
            String pName = this.getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+pName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SenpaiDB.getInstance(this.getApplicationContext()).close();
    }
}

