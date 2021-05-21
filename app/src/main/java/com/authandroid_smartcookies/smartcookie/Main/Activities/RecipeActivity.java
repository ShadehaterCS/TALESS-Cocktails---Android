package com.authandroid_smartcookies.smartcookie.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.R;
import com.authandroid_smartcookies.smartcookie.Util.Utilities;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class RecipeActivity extends AppCompatActivity {
    private CocktailRecipe recipe;
    private final HashMap<String, String> ingredients = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Utilities.setAnimationAndExcludeTargets(getWindow());

        recipe = (CocktailRecipe) getIntent().getSerializableExtra("recipe");

        ImageView imgView = findViewById(R.id.cocktailImage_recipe);
        TextView titleTV = findViewById(R.id.title_recipe);
        TextView descriptionTV = findViewById(R.id.description_recipe);
        recipe = getIntent().getParcelableExtra("recipe");

        int rid = getResources().getIdentifier(
                recipe.get_imageid(), "drawable", getPackageName());
        Glide.with(this).load(rid).transform(new CenterCrop()).into(imgView);
        //MAIN
        titleTV.setText(recipe.get_title());
        descriptionTV.setText(recipe.get_description());

        //BUTTONS
        Button calorieButton = findViewById(R.id.caloriesButton);
        Button preptimeButton = findViewById(R.id.preptime_button);
        calorieButton.setText(recipe.get_calories());
        preptimeButton.setText(recipe.get_preptime());
        //INGREDIENTS
        //todo optimization
        TextView v = findViewById(R.id.ingredientsTextView);
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

        //STEPS(PREPARATION)
        TextView stepsTextView = findViewById(R.id.stepsTextView);
        stepsTextView.setText(parseSteps());

        //TIMER
        TextView timerText = findViewById(R.id.timerTextView);
        ProgressBar progressBar = findViewById(R.id.timerProgressBar);
        CardView timerCard = findViewById(R.id.timerCardView);
        AtomicBoolean timerAlreadyPressed = new AtomicBoolean(false);
        if (recipe.get_timer() != -1 && !timerAlreadyPressed.get()) {
            timerCard.setOnClickListener(view -> {
                CountDownTimer countDownTimer = new CountDownTimer(
                        recipe.get_timer() * 1000 + 500, 1000) {
                    private final int future = recipe.get_timer();
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int time = Math.round(millisUntilFinished / 1000);
                        double percent = ((double) time / (double) future) * 100;
                        progressBar.setProgress((int) percent, true);
                        timerText.setText(String.format("%d", time));
                    }
                    @Override
                    public void onFinish() {
                        timerText.setText(String.format("%d",recipe.get_timer()));
                        progressBar.setProgress(100, true);
                        timerAlreadyPressed.set(false);
                    }
                }.start();
                timerAlreadyPressed.set(true);
            });
        }
        else{
            progressBar.setVisibility(View.GONE);
            timerCard.setVisibility(View.INVISIBLE);
        }
    }

    private String parseSteps(){
        StringBuilder builder = new StringBuilder();
        String[] steps = recipe.get_steps().split("([1-9])\\.");
        for (int i = 1; i<steps.length;i++) {
            builder.append(i);
            builder.append(". ");
            builder.append(steps[i]);
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }
}

