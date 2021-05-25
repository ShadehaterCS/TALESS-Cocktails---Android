package com.auth.TALESS.Main.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.auth.TALESS.DataClasses.CocktailRecipe;
import com.auth.TALESS.Main.Activities.LauncherActivity;
import com.auth.TALESS.R;
import com.auth.TALESS.Util.Utilities;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.util.ArrayList;

/**
 * Adapts the dataset of an ArrayList of CocktailRecipe objects
 * Uses recyclerview_favorites_and_search_item.xml for its elements layout
 * @link RecipeActivity when an element is clicked on.
 */
public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ViewHolder> {
        private final ArrayList<CocktailRecipe> dataset;
        private final Context context;
        public GeneralAdapter(Context context, ArrayList<CocktailRecipe> dataset) {
            this.dataset = dataset;
            this.context = context;
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
            holder.getTitleTV().setText(recipe.get_title());
            holder.getDescTV().setText(recipe.get_description());

            if (LauncherActivity.pref_paintTitles)
                Utilities.setTitleColorForTextView(context,holder.titleTV, recipe);

            int rid = holder.view.getContext().getResources()
                    .getIdentifier(recipe.get_imageid(), "drawable",
                            context.getPackageName());
            Glide.with(holder.view).load(rid).transform(new CenterCrop(),
                    new GranularRoundedCorners(15,0,0,15))
                    .into(holder.getImgView());

            Utilities.setOnClickListenerOnViewForIntentToRecipeActivity(
                    context, holder.view, holder.getImgView(), recipe);
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

            public ViewHolder(View view) {
                super(view);
                this.view = view;

                titleTV = view.findViewById(R.id.favoritesRVTitle);
                descTV = view.findViewById(R.id.favoritesRVDescription);
                imgView = view.findViewById(R.id.favoritesRecipeImage);
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