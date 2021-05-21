package com.authandroid_smartcookies.smartcookie.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.R;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

public class Utilities {
    public static void make_show_SnackBar(View view, String message, int duration){
        Snackbar.make(view, message, duration).show();
    }

    public static  boolean restorePrefData(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPrefs",context.MODE_PRIVATE);
        return pref.getBoolean("isIntroOpened",false);
    }

    public static void savePrefsData(Context context) {
        SharedPreferences pref = context.getSharedPreferences("myPrefs",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.apply();
    }

    public static void setAnimationAndExcludeTargets(Window window){
        Fade fade = new Fade();
        View decor = window.getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        window.setEnterTransition(fade);
        window.setExitTransition(fade);
    }

    public static void hideKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null)
            view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int getColor(Context context, String colorName){
        int colorId;
        switch (colorName){
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
                colorId = ResourcesCompat.getColor(context.getResources(), R.color.white, context.getTheme());
        }
        return colorId;
    }
    public static void setTitleColor(Context context, TextView title, CocktailRecipe recipe){
        if (recipe.get_color().equals("White"))
            title.setTextColor(Color.DKGRAY);
        else
            title.setTextColor(getColor(context,recipe.get_color()));
    }
    public static void clearGlideCache(Context applicationContext){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Glide.get(applicationContext).clearDiskCache();
                return null;
            }
        };
    }
}
