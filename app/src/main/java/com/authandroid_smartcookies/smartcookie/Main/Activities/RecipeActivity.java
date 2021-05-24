package com.authandroid_smartcookies.smartcookie.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class RecipeActivity extends AppCompatActivity {
    private CocktailRecipe recipe;
    private Button calorieButton;
    private Button prepTimeButton;
    private ProgressBar progressBar;
    private TextView titleTV;
    private SenpaiDB db;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        db = SenpaiDB.getInstance(this);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Utilities.setAnimationAndExcludeTargets(getWindow());

        recipe = (CocktailRecipe) getIntent().getSerializableExtra("recipe");

        ImageView imgView = findViewById(R.id.cocktailImage_recipe);
        titleTV = findViewById(R.id.title_recipe);
        TextView descriptionTV = findViewById(R.id.description_recipe);
        recipe = getIntent().getParcelableExtra("recipe");
        int rid = getResources().getIdentifier(
                recipe.get_imageid(), "drawable", getPackageName());
        Glide.with(this).load(rid).transform(new CenterCrop(), new GranularRoundedCorners(15,0,0,15)).into(imgView);
        //MAIN
        titleTV.setText(recipe.get_title());
        descriptionTV.setText(recipe.get_description());

        //BUTTONS
        calorieButton = findViewById(R.id.caloriesButton);
        prepTimeButton = findViewById(R.id.preptime_button);
        calorieButton.setText(recipe.get_calories());
        prepTimeButton.setText(recipe.get_preptime());

        //INGREDIENTS CARD
        String[] ingredients = parseIngredients();
        TextView ingredientNamesTv = findViewById(R.id.ingredientNamesTV);
        TextView ingredientValuesTV = findViewById(R.id.ingredientValuesTV);

        ingredientNamesTv.setText(ingredients[0]);
        ingredientValuesTV.setText(ingredients[1]);

        //STEPS(PREPARATION) CARD
        TextView stepsTextView = findViewById(R.id.stepsTextView);
        String[] preparationInfo = parseStepsData();
        stepsTextView.setText(preparationInfo[0]);

        //TIMER CARD
        TextView timerTitle = findViewById(R.id.timerTitle);
        TextView timerText = findViewById(R.id.timerTextView);
        progressBar = findViewById(R.id.timerProgressBar);
        CardView timerCard = findViewById(R.id.timerCardView);
        AtomicBoolean timerAlreadyPressed = new AtomicBoolean(false);

        timerTitle.setText(preparationInfo[1]);
        ImageView timerImage = findViewById(R.id.timerTypeImageView);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shaker);

        if (preparationInfo[1].equals("Stir")){
            timerImage.setVisibility(View.INVISIBLE);
        }

        String audioFileName = "timersound";
        MediaPlayer mediaPlayer =
                MediaPlayer.create(this, this.getResources().getIdentifier(
                        audioFileName, "raw", this.getPackageName()));

        if (recipe.get_timer() != -1) {
            timerCard.setOnClickListener(view -> {
                if (!timerAlreadyPressed.get()) {
                    timer = new CountDownTimer(
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
                            timerText.setText(String.format("%d", recipe.get_timer()));
                            progressBar.setProgress(100, true);
                            timerAlreadyPressed.set(false);
                            //Timer sound on finish
                            mediaPlayer.start();
                            mediaPlayer.seekTo(0);
                            if (timerImage.getVisibility() == View.VISIBLE)
                                timerImage.clearAnimation();
                        }
                    }.start();
                    if (timerImage.getVisibility()==View.VISIBLE)
                        timerImage.startAnimation(shake);
                    timerAlreadyPressed.set(true);
                }
                //ENDIF
            });
        } else {
            progressBar.setVisibility(View.GONE);
            timerCard.setVisibility(View.INVISIBLE);
        }
        //ENDIF
        setUpColors();
    }

    private String[] parseIngredients() {
        StringBuilder namesBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();
        HashMap<String, String> ingredientsMap = db.getIngredients(recipe);
        for (String ingredient : ingredientsMap.keySet()) {
            namesBuilder.append("• ");
            namesBuilder.append(ingredient);
            namesBuilder.append("\n");
            valuesBuilder.append(ingredientsMap.get(ingredient));
            valuesBuilder.append("\n");
        }

        return new String[] {namesBuilder.toString(), valuesBuilder.toString() };
    }

    private void setUpColors(){
        int colorId = Utilities.getColor(this,recipe.get_color());
        titleTV.setTextColor(colorId);
        calorieButton.setBackgroundColor(colorId);
        prepTimeButton.setBackgroundColor(colorId);

        LayerDrawable progressBarBG = (LayerDrawable) ResourcesCompat.getDrawable(getResources(),R.drawable.circular_progress,getTheme());
        GradientDrawable shape = (GradientDrawable)progressBarBG.getDrawable(0);
        shape.setColors(new int[]{colorId, colorId, colorId});

        if (recipe.get_color().equals("White")){
            int snowColorId = getResources().getColor(R.color.cocktail_white,getTheme());
            calorieButton.setTextColor(Color.DKGRAY);
            prepTimeButton.setTextColor(Color.DKGRAY);
            calorieButton.setBackgroundColor(snowColorId);
            prepTimeButton.setBackgroundColor(snowColorId);
            shape.setColors(new int[]{colorId, colorId, colorId});
            titleTV.setTextColor(Color.DKGRAY);
        }

    }

    private String[] parseStepsData() {
        StringBuilder builder = new StringBuilder();
        String[] steps = recipe.get_steps().split("([1-9])\\.");
        for (int i = 1; i < steps.length; i++) {
            builder.append(i);
            builder.append(". ");
            builder.append(steps[i]);
            builder.append(System.getProperty("line.separator"));
        }
        long timerType = Arrays.stream(steps)
                .filter(s -> s.contains("shake") || s.contains("shaker"))
                .count();
        String[] result = new String[2];
        result[0] = builder.toString();
        result[1] = timerType > 0 ? "Shake" : "Stir";
        return result;
    }
//todo add a favorite this recipe cardview
    /**
     * Stops the timer if it has started to avoid sound playing even though activity is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null)
            timer.cancel();
    }
}

