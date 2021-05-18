package com.authandroid_smartcookies.smartcookie.Menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.Menu.Home.MainMenuFragment;
import com.authandroid_smartcookies.smartcookie.R;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    private SenpaiDB db;
    private ArrayList<CocktailRecipe> recipes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = SenpaiDB.getInstance(this.requireContext());
        recipes = db.getAllRecipes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }
}