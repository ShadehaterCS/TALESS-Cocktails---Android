package com.authandroid_smartcookies.smartcookie.Menu.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import com.authandroid_smartcookies.smartcookie.HomeActivity;
import com.authandroid_smartcookies.smartcookie.Launcher;
import com.authandroid_smartcookies.smartcookie.R;

import java.io.File;
import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private final ArrayList<CocktailRecipe> recipes;
    private final ArrayList<Integer> favorites;
    private final SenpaiDB db;


    public RecipeAdapter(Context context, ArrayList<CocktailRecipe> recipes) {
        this.recipes = recipes;
        db = SenpaiDB.getInstance(context);
        favorites = db.getFavorites();
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_mainmenu_item, parent, false);
        return new ViewHolder(view);
    }

    //Fill the view
    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {

        CocktailRecipe recipe = recipes.get(position);

        holder.favorited = favorites.contains(recipe.get_id());
        holder.getTitleTV().setText(recipe.get_title());
        holder.getDescTV().setText(recipe.get_description());
        holder.getImgView().setImageBitmap(
                scaleImage(holder.view.getContext(), 1080, 1080, recipe.get_imageid()));

        holder.getFavoriteButton().setOnClickListener(v -> {
            if (holder.favorited)
                db.removeRecipeFromFavorites(recipe);
            else
                db.insertRecipeIntoFavorites(recipe);
            holder.favorited = !holder.favorited;
            holder.setFavoriteButtonImage(v, holder.favorited);
        });

        holder.setFavoriteButtonImage(holder.getFavoriteButton(), holder.favorited);
    }

    private Bitmap scaleImage(Context context, int x, int y, String imageId) {
        final int rid = context.getResources()
                .getIdentifier(imageId, "drawable", context.getPackageName());
        Bitmap bMap = BitmapFactory.decodeResource(context.getResources(),
                rid);
        return Bitmap.createScaledBitmap(bMap, x, y, true);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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

            //To handle moving to a new activity
            view.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), HomeActivity.class);
                v.getContext().startActivity(intent);
            });
        }

        private void setFavoriteButtonImage(View v, boolean favorited) {
            if (favorited)
                v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_favorite_button_on));
            else
                v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.ic_favorite_button_off));
        }

        public ImageButton getFavoriteButton() {
            return favoriteButton;
        }

        public View getView() {
            return view;
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
