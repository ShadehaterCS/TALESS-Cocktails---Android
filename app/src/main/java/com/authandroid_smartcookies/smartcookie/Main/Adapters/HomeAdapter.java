package com.authandroid_smartcookies.smartcookie.Main.Adapters;

import android.content.Context;
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
import com.authandroid_smartcookies.smartcookie.Main.Activities.LauncherActivity;
import com.authandroid_smartcookies.smartcookie.R;
import com.authandroid_smartcookies.smartcookie.Util.Utilities;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.util.ArrayList;

/**
 * Adapts an ArrayList of CocktailRecipe objects
 * Uses recyclerview_home_item.xml for its elements' layout
 * @link RecipeActivity when an element is clicked on
 * @link SenpaiDB when the FavoriteButton is clicked on an element
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final ArrayList<CocktailRecipe> dataset;
    private ArrayList<Integer> favorites;
    private final Context context;
    private final SenpaiDB db;

    public HomeAdapter(Context context, ArrayList<CocktailRecipe> dataset) {
        this.dataset = dataset;
        this.context = context;
        db = SenpaiDB.getInstance(context);
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_home_item, parent, false);
        return new ViewHolder(view);
    }

    //Fill the view
    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        CocktailRecipe recipe = dataset.get(position);

        holder.favorited = favorites.contains(recipe.get_id());
        holder.getTitleTV().setText(recipe.get_title());
        holder.getDescTV().setText(recipe.get_description());

        if (LauncherActivity.pref_paintTitles)
            Utilities.setTitleColorForTextView(holder.view.getContext(),holder.titleTV, recipe);

        int rid = holder.view.getContext().getResources()
                .getIdentifier(recipe.get_imageid(), "drawable",
                        holder.view.getContext().getPackageName());
        Glide.with(holder.view).load(rid).transform(
                new CenterCrop(), new GranularRoundedCorners(20,20,20,20))
                .into(holder.getImgView());

        Utilities.setOnClickListenerOnViewForIntentToRecipeActivity(
                context, holder.view, holder.getImgView(), recipe);

        //Handle the user clicking on the heart shaped button on the element
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
                        "Added "+recipe.get_title() + " to favorites",
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

        public boolean favorited;
        public ViewHolder(View view) {
            super(view);
            this.view = view;

            titleTV = view.findViewById(R.id.titleTextView);
            descTV = view.findViewById(R.id.descriptionTextView);
            imgView = view.findViewById(R.id.cocktailImage);
            favoriteButton = view.findViewById(R.id.imageButton);
            //To handle moving to a new activity through shared element
        }

        private void setFavoriteButtonImage(View v, boolean favorited) {
            if (favorited)
                v.setForeground(ContextCompat.getDrawable(v.getContext(), R.drawable.icon_favorite_button_on));
            else
                v.setForeground(ContextCompat.getDrawable(v.getContext(), R.drawable.icon_favorite_button_off));
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
