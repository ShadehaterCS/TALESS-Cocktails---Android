package com.authandroid_smartcookies.smartcookie.Main.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Main.Activities.LauncherActivity;
import com.authandroid_smartcookies.smartcookie.Main.Activities.RecipeActivity;
import com.authandroid_smartcookies.smartcookie.R;
import com.authandroid_smartcookies.smartcookie.Util.Utilities;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ViewHolder> {
        private final ArrayList<CocktailRecipe> dataset;

        public GeneralAdapter(Context context, ArrayList<CocktailRecipe> dataset) {
            this.dataset = dataset;
        }


        @NonNull
        @Override
        public GeneralAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_favorites_and_search_item, parent, false);
            return new GeneralAdapter.ViewHolder(view);
        }


        //Fill the view
        @Override
        public void onBindViewHolder(@NonNull GeneralAdapter.ViewHolder holder, int position) {
            CocktailRecipe recipe = dataset.get(position);
            holder.recipe = recipe;
            holder.getTitleTV().setText(recipe.get_title());
            holder.getDescTV().setText(recipe.get_description());

            if (LauncherActivity.pref_paintTitles)
                Utilities.setTitleColorForTextView(holder.view.getContext(),holder.titleTV, recipe);

            int rid = holder.view.getContext().getResources()
                    .getIdentifier(recipe.get_imageid(), "drawable",
                            holder.view.getContext().getPackageName());
            Glide.with(holder.view).load(rid).into(holder.getImgView());
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
            public CocktailRecipe recipe;

            public ViewHolder(View view) {
                super(view);
                this.view = view;

                titleTV = view.findViewById(R.id.favoritesRVTItle);
                descTV = view.findViewById(R.id.favoritesRVDescription);
                imgView = view.findViewById(R.id.favoritesRecipeImage);

                //To handle moving to a new activity through shared element
                //todo move this inside utilities as static. used too many times
                view.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), RecipeActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                            ((Activity)view.getContext(),
                                    imgView, "cocktail_recipe_transition");
                    intent.putExtra("recipe", recipe);
                    v.getContext().startActivity(intent,options.toBundle());
                });
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