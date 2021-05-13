package com.authandroid_smartcookies.smartcookie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.MyDatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Objects.requireNonNull(getSupportActionBar()).hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }
}

