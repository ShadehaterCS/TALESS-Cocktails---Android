package com.authandroid_smartcookies.smartcookie.Main.Home;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;

/**
 * Adapter that adapts multiple different views
 * Somehow?
 * Views won't get recycled but the scrolling behavior helps
 */
public class DetailedRecipeAdapter extends RecyclerView.Adapter<DetailedRecipeAdapter.BaseViewHolder>{

    private final CocktailRecipe recipe;

    public DetailedRecipeAdapter(CocktailRecipe recipe) {
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    protected class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    protected class IngredientsBaseViewHolder extends BaseViewHolder {
        public IngredientsBaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected class InstructionsBaseViewHolder extends BaseViewHolder {
        public InstructionsBaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
