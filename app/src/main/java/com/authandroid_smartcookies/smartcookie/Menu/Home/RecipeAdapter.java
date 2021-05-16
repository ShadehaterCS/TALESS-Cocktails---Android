package com.authandroid_smartcookies.smartcookie.Menu.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.R;

import java.util.ArrayList;

//kurie katsane bro eiste kalos alla y no kotlin? gt na prepei na vlepw pragmata opws @nonnull @override public ..kai oxi apla override funs

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private ArrayList<CocktailRecipe> recipes;
    public RecipeAdapter(ArrayList<CocktailRecipe> recipes){
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_mainmenu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final CocktailRecipe recipe;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

        }

    }
}
