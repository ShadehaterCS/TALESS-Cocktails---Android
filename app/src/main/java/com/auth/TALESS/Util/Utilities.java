package com.auth.TALESS.Util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;

import com.auth.TALESS.DataClasses.CocktailRecipe;
import com.auth.TALESS.Main.Activities.RecipeActivity;
import com.auth.TALESS.R;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

/**
 * @class A utilities class.
 * Used as the place to put obtrusive methods to help with the clutter inside other classes'
 * implementations and to reduce code rewriting.
 * All methods must be declared as public static
 * Methods should be usable by at least 2 classes.
 * @caution Contexts should never be preserved. Use it where you need and return, never save.
 */
public class Utilities {
    public static void make_show_SnackBar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    public static boolean restorePrefData(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        return pref.getBoolean("isIntroOpened", false);
    }

    public static void savePrefsData(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.apply();
    }

    public static void setAnimationAndExcludeTargets(Window window) {
        Fade fade = new Fade();
        View decor = window.getDecorView();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        fade.excludeTarget(decor.findViewById(R.id.bottomNavigationView), true);
        window.setEnterTransition(fade);
        window.setExitTransition(fade);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int getColor(Context context, String colorName) {
        int colorId;
        switch (colorName) {
            case "Purple":
                colorId = ResourcesCompat.getColor(context.getResources(), R.color.cocktail_purple, context.getTheme());
                break;
            case "Rose":
                colorId = ResourcesCompat.getColor(context.getResources(), R.color.cocktail_rose, context.getTheme());
                break;
            case "Blue":
                colorId = ResourcesCompat.getColor(context.getResources(), R.color.cocktail_blue, context.getTheme());
                break;
            case "Orange":
                colorId = ResourcesCompat.getColor(context.getResources(), R.color.cocktail_orange, context.getTheme());
                break;
            case "Crimson":
                colorId = ResourcesCompat.getColor(context.getResources(), R.color.cocktail_crimson, context.getTheme());
                break;
            case "Light green":
                colorId = ResourcesCompat.getColor(context.getResources(), R.color.cocktail_light_green, context.getTheme());
                break;
            case "Yellow":
                colorId = ResourcesCompat.getColor(context.getResources(), R.color.cocktail_yellow, context.getTheme());
                break;
            case "Brown":
                colorId = ResourcesCompat.getColor(context.getResources(), R.color.cocktail_brown, context.getTheme());
                break;
            case "Red":
                colorId = ResourcesCompat.getColor(context.getResources(), R.color.cocktail_red, context.getTheme());
                break;
            default:
                colorId = ResourcesCompat.getColor(context.getResources(), R.color.cocktail_contrast, context.getTheme());
                break;
        }
        return colorId;
    }

    public static void setOnClickListenerOnViewForIntentToRecipeActivity(Context context, View view, ImageView imgView, CocktailRecipe recipe) {
        view.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RecipeActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                    ((Activity) context,
                            imgView, "cocktail_recipe_transition");
            intent.putExtra("recipe", recipe);
            v.getContext().startActivity(intent, options.toBundle());
        });
    }

    public static void setTitleColorForTextView(Context context, TextView title, CocktailRecipe recipe) {
        title.setTextColor(getColor(context, recipe.get_color()));
    }

    public static void clearGlideCache(Context applicationContext) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Glide.get(applicationContext).clearDiskCache();
                return null;
            }
        };
    }

    /**
     * @param selectedMode the mode selected inside the Settings Fragment
     * @return false if a theme change should happen
     */
    public static void setDeviceThemeMode(Context context, String selectedMode) {
        int mode;
        if (selectedMode.equals("light"))
            mode = AppCompatDelegate.MODE_NIGHT_NO;
        else if (selectedMode.equals("dark"))
            mode = AppCompatDelegate.MODE_NIGHT_YES;
        else
            mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

        int currentMode = context.getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        switch (currentMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                if (mode != 1)
                    AppCompatDelegate.setDefaultNightMode(mode);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                if (mode != 2)
                    AppCompatDelegate.setDefaultNightMode(mode);
                break;
        }
        AppCompatDelegate.setDefaultNightMode(mode);
    }
}
