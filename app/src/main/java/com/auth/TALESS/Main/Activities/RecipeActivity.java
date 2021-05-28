package com.auth.TALESS.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

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

import com.auth.TALESS.DataClasses.CocktailRecipe;
import com.auth.TALESS.Database.SenpaiDB;
import com.auth.TALESS.R;
import com.auth.TALESS.Util.Utilities;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class implements the Activity that opens when a CocktailRecipe is to be displayed
 * SenpaiDB object is required to load the ingredients for that object since they live on a separate
 * table.
 */
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

        //Load the sound file from res/raw
        String audioFileName = "timersound";
        MediaPlayer mediaPlayer =
                MediaPlayer.create(this, this.getResources().getIdentifier(
                        audioFileName, "raw", this.getPackageName()));

        TextView longPressIndicator = findViewById(R.id.pressAgainTextView);
        if (recipe.get_timer() != -1) {
            timerText.setText(Integer.toString(recipe.get_timer()));
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
                            longPressIndicator.setVisibility(View.INVISIBLE);
                        }
                    }.start();
                    if (timerImage.getVisibility()==View.VISIBLE)
                        timerImage.startAnimation(shake);

                    longPressIndicator.setVisibility(View.VISIBLE);
                    timerAlreadyPressed.set(true);
                }
                //ENDIF
            });
            timerCard.setOnLongClickListener(view -> {
                if (timerAlreadyPressed.get()){
                    timer.cancel();
                    timer.start();
                }
                return true;
            });
        } else {
            progressBar.setVisibility(View.GONE);
            timerCard.setVisibility(View.INVISIBLE);
        }
        //ENDIF

        setUpColors();
    }

    /**
     * Goes through the ingredients HashMap and creates two different Strings for the two
     * TextViews that live inside the IngredientsCardView.
     * @return a String[2], [0] are the ingredient titles, [1] are the values for each ingredient
     * Both Strings will be the same size and have the same newLines in order to be equal in height.
     */
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

    /**
     * Method handles the different colors for every CocktailRecipe
     * Access any object that should be a different color and assign the appropriate colors to the
     * background and/or text.
     * Special case for recipes that have "White" as their color as that should be handled differently
     * for light / dark theme.
     */
    private void setUpColors(){
        int colorId = Utilities.getColor(this,recipe.get_color());
        titleTV.setTextColor(colorId);
        calorieButton.setBackgroundColor(colorId);
        prepTimeButton.setBackgroundColor(colorId);

        LayerDrawable progressBarBG = (LayerDrawable) ResourcesCompat.getDrawable(getResources(),R.drawable.circular_progress,getTheme());
        GradientDrawable shape = (GradientDrawable)progressBarBG.getDrawable(0);
        shape.setColors(new int[]{colorId, colorId, colorId});

        if (recipe.get_color().equals("White")){
            int snowColorId = getResources().getColor(R.color.cocktail_contrast,getTheme());
            calorieButton.setBackgroundColor(snowColorId);
            prepTimeButton.setBackgroundColor(snowColorId);
            shape.setColors(new int[]{colorId, colorId, colorId});
        }

    }

    /**
     * Goes through the "_steps" String of the CocktailRecipe object.
     * Every different step starts with the pattern "X. " where X a number between 1-9 thus
     * the regular expression "([1-9])\\." is used to split that string.
     * timerType is used to find out what type of timer should be presented to the user by
     * going through every word in the steps and if it encounters "shake" or "shaker" it sets
     * the appropriate String to the returning array.
     * @return a String[2] where [0] are the Steps as a String with newLines and [1] the timerType.
     */
    private String[] parseStepsData() {
        StringBuilder builder = new StringBuilder();
        String[] steps = recipe.get_steps().split("([1-9])\\.");
        for (int i = 1; i < steps.length; i++) {
            builder.append(i);
            builder.append(". ");
            builder.append(steps[i]);
            builder.append(System.getProperty("line.separator"));
            if (i+1 !=steps.length)
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

