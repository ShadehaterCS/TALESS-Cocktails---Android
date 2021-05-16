package com.authandroid_smartcookies.smartcookie.Menu.Home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.R;

import java.io.File;
import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private final ArrayList<CocktailRecipe> recipes;

    public RecipeAdapter(ArrayList<CocktailRecipe> recipes) {
        this.recipes = recipes;
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
        holder.getTitleTV().setText(recipe.get_title());
        holder.getDescTV().setText(recipe.get_description());
        holder.getImgView().setImageBitmap(
                scaleImage(holder.view.getContext(),200, 200, recipe.get_imageid()));
    }

    private Bitmap scaleImage(Context context, int x, int y, String imageId) {
        final int rid = context.getResources()
                .getIdentifier(imageId, "drawable", context.getPackageName());
        int a = R.drawable.ic_blue_hawaii;
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

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            titleTV = view.findViewById(R.id.titleTextView);
            descTV = view.findViewById(R.id.descriptionTextView);
            imgView = view.findViewById(R.id.cocktailImage);
            // Define click listener for the ViewHolder's View
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
