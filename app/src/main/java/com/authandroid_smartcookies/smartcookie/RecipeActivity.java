package com.authandroid_smartcookies.smartcookie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.transition.Explode;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.bumptech.glide.Glide;

public class RecipeActivity extends AppCompatActivity {

    private ImageView imgView;
    private TextView titleTV;
    private TextView descriptionTV;

    private CocktailRecipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Explode explode = new Explode();
        getWindow().setEnterTransition(explode);
        getWindow().setExitTransition(explode);

        imgView = findViewById(R.id.cocktailImage_recipe);
        titleTV = findViewById(R.id.title_recipe);
        descriptionTV = findViewById(R.id.description_recipe);
        recipe = getIntent().getParcelableExtra("recipe");

        int rid = getResources().getIdentifier(
                recipe.get_imageid(), "drawable", getPackageName());
        Glide.with(this).load(rid).into(imgView);

        titleTV.setText(recipe.get_title());
        descriptionTV.setText(recipe.get_description());
    }

}