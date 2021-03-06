package com.auth.TALESS.Main.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auth.TALESS.DataClasses.CocktailRecipe;
import com.auth.TALESS.Database.SenpaiDB;
import com.auth.TALESS.Main.Adapters.GeneralAdapter;
import com.auth.TALESS.R;

import java.util.ArrayList;
/**
 *This fragment sets a background if favorites are empty
 * and if they are not it takes all favorites from SenpaiDB
 * and shows them in a RecyclerView
 */
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
        db = SenpaiDB.getInstance(requireContext());
        recipes = db.getFavoriteRecipes();
        background = view.findViewById(R.id.FavoritesConstraintLayout);
        if (!recipes.isEmpty())
            background.setBackground(null);
        RecyclerView rv = view.findViewById(R.id.favoritesRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(requireActivity()));
        rv.setAdapter(new GeneralAdapter(requireContext(), recipes));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}