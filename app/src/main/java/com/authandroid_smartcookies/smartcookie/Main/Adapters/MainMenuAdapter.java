package com.authandroid_smartcookies.smartcookie.Main.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.Main.Activities.RecipeActivity;
import com.authandroid_smartcookies.smartcookie.R;
import com.authandroid_smartcookies.smartcookie.Util.Utilities;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;

//TODO long hold enlarges picture
public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder> {
    private final ArrayList<CocktailRecipe> dataset;
    private ArrayList<Integer> favorites;
    private final SenpaiDB db;

    public MainMenuAdapter(Context context, ArrayList<CocktailRecipe> dataset) {
        this.dataset = dataset;
        db = SenpaiDB.getInstance(context);
    }

    @NonNull
    @Override
    public MainMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_mainmenu_item, parent, false);
        return new ViewHolder(view);
    }

    //Fill the view
    @Override
    public void onBindViewHolder(@NonNull MainMenuAdapter.ViewHolder holder, int position) {
        CocktailRecipe recipe = dataset.get(position);

        holder.recipe = recipe;
        holder.favorited = favorites.contains(recipe.get_id());
        holder.getTitleTV().setText(recipe.get_title());
        holder.getDescTV().setText(recipe.get_description());

        int rid = holder.view.getContext().getResources()
                .getIdentifier(recipe.get_imageid(), "drawable",
                        holder.view.getContext().getPackageName());
        Glide.with(holder.view).load(rid).transform(new CenterCrop()).into(holder.getImgView());

        holder.getFavoriteButton().setOnClickListener(v -> {
            if (holder.favorited) {
                db.removeRecipeFromFavorites(recipe);
                Utilities.make_show_SnackBar(
                        holder.view,"Removed "+recipe.get_title() + " from favorites",
                        2000);
                favorites.removeIf(i -> i == recipe.get_id());
            }
            else {
                db.insertRecipeIntoFavorites(recipe);
                Utilities.make_show_SnackBar(holder.view,
                        "Added "+recipe.get_title() + " from favorites",
                        1000);
                favorites.add(recipe.get_id());
            }

            holder.favorited = !holder.favorited;

            holder.setFavoriteButtonImage(v, holder.favorited);
        });

        holder.setFavoriteButtonImage(holder.getFavoriteButton(), holder.favorited);
    }

    public void setFavorites(ArrayList<Integer> favorites){
        this.favorites = favorites;
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTV;
        private final TextView descTV;
        private final ImageView imgView;
        private final View view;
        private final ImageButton favoriteButton;
        public CocktailRecipe recipe;

        public boolean favorited;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            titleTV = view.findViewById(R.id.titleTextView);
            descTV = view.findViewById(R.id.descriptionTextView);
            imgView = view.findViewById(R.id.cocktailImage);
            favoriteButton = view.findViewById(R.id.imageButton);

            //To handle moving to a new activity through shared element
            view.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), RecipeActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                        ((Activity)view.getContext(),
                        imgView, "cocktail_recipe_transition");
                intent.putExtra("recipe", recipe);
                v.getContext().startActivity(intent,options.toBundle());
            });
        }

        private void setFavoriteButtonImage(View v, boolean favorited) {
            if (favorited)
                v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.icon_favorite_button_on));
            else
                v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.icon_favorite_button_off));
        }

        public ImageButton getFavoriteButton() {
            return favoriteButton;
        }

        public TextView getTitleTV() {
            return titleTV;
        }

        public TextView getDescTV() {
            return descTV;
        }

        public ImageView getImgView() {
            return imgView;
        }
    }
}
