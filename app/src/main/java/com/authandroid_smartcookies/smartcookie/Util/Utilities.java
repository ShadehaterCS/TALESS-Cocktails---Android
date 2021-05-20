package com.authandroid_smartcookies.smartcookie.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.R;
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
}
