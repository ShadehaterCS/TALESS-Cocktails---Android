package com.authandroid_smartcookies.smartcookie.Menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.DataclassTransformations;
import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainMenuFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    SenpaiDB db;
    Random random = new Random();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recyclerView=requireActivity().findViewById(R.id.recyclerview);
        add_button=requireActivity().findViewById(R.id.add_button);

        db = SenpaiDB.getInstance(this.requireContext());
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //If database doesn't exist, create it and always open the connection
        if (!db.openDatabase())
            db.createDatabase(requireContext().getApplicationContext());
        db.openDatabase();

        TextView tv = requireActivity().findViewById(R.id.textView4);
        tv.setOnClickListener(v -> {
            ArrayList<CocktailRecipe> recipes = getRandomRecipes();
            db.removeRecipeFromFavorites(recipes.get(0));
            if (!recipes.isEmpty())
                tv.setText(recipes.get(random.nextInt(recipes.size())).get_title());
        });
    }

    private ArrayList<CocktailRecipe> getRandomRecipes(){
        ArrayList<CocktailRecipe> allRecipes = db.getAllRecipes();
        ArrayList<CocktailRecipe> recipes = new ArrayList<>();
        for (int i=0;i<5;i++){
            int rand = random.nextInt(allRecipes.size());
            recipes.add(allRecipes.get(rand));
            allRecipes.remove(rand);
        }
        return recipes;
    }

}