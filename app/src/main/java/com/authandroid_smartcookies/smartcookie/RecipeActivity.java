package com.authandroid_smartcookies.smartcookie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.ChangeBounds;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.bumptech.glide.Glide;

import java.util.Objects;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Fade fade = new Fade();
        fade.setDuration(200);
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(new Explode());

        ImageView imgView = findViewById(R.id.cocktailImage_recipe);
        TextView titleTV = findViewById(R.id.title_recipe);
        TextView descriptionTV = findViewById(R.id.description_recipe);

        CocktailRecipe recipe = getIntent().getParcelableExtra("recipe");

        int rid = getResources().getIdentifier(
                recipe.get_imageid(), "drawable", getPackageName());
        Glide.with(this).load(rid).into(imgView);

        titleTV.setText(recipe.get_title());
        descriptionTV.setText(recipe.get_description());
    }

}