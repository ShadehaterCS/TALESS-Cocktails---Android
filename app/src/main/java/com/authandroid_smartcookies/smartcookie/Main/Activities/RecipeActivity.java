package com.authandroid_smartcookies.smartcookie.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.Main.Adapters.RecipeActivityAdapter;
import com.authandroid_smartcookies.smartcookie.R;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Objects;

public class RecipeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Fade fade = new Fade();
        fade.setDuration(300);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        ImageView imgView = findViewById(R.id.cocktailImage_recipe);
        TextView titleTV = findViewById(R.id.title_recipe);
        TextView descriptionTV = findViewById(R.id.description_recipe);
        CocktailRecipe recipe = getIntent().getParcelableExtra("recipe");

        int rid = getResources().getIdentifier(
                recipe.get_imageid(), "drawable", getPackageName());
        Glide.with(this).load(rid).into(imgView);

        titleTV.setText(recipe.get_title());
        descriptionTV.setText(recipe.get_description());

        //RecyclerView stuff
        recyclerView = findViewById(R.id.recipeActivityRecyclerView);
        recyclerView.setAdapter(new RecipeActivityAdapter(recipe));

        //DEBUG ONLY
        TextView v = findViewById(R.id.testingtext);
        SenpaiDB db = SenpaiDB.getInstance(this);
        HashMap<String,String> m = db.getIngredients(recipe);
        String a = "";
        String b = "";
        String c = "";
        for (String s : m.keySet()){
            a = s;
            b = m.get(s);
            c += a + " " + b + "\n";
        }
        v.setText(c);
    }

}