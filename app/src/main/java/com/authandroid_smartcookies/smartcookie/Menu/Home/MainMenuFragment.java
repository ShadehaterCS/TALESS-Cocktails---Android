package com.authandroid_smartcookies.smartcookie.Menu.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainMenuFragment extends Fragment {
    private final String TAG = "MAIN_MENU_FRAGMENT";
    protected RecyclerView recyclerView;
    protected SenpaiDB db;
    protected Random random = new Random();

    protected RecipeAdapter adapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<CocktailRecipe> dataset;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If database doesn't exist, create it and always open the connection
        db = SenpaiDB.getInstance(this.requireContext());
        if (!db.openDatabase()) {
            db.createDatabase(requireContext().getApplicationContext());
            db.openDatabase();
        }
        dataset = db.getAllRecipes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main_menu, container, false);
        root.setTag(TAG);
        recyclerView=root.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new RecipeAdapter(dataset);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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