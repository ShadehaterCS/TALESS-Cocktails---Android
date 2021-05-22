package com.authandroid_smartcookies.smartcookie.Main.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.Main.Adapters.GeneralAdapter;
import com.authandroid_smartcookies.smartcookie.R;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {
    private SenpaiDB db;
    private ArrayList<CocktailRecipe> recipes;
    private ConstraintLayout background;
    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        background = view.findViewById(R.id.FavoritesConstraintLayout);
        if (!recipes.isEmpty())
            background.setBackground(null);
        RecyclerView rv = view.findViewById(R.id.favoritesRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rv.setAdapter(new GeneralAdapter(requireContext(), recipes));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        db = SenpaiDB.getInstance(context);
        recipes = db.getFavoriteRecipes();
    }
}