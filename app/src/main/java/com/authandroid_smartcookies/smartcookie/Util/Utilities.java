package com.authandroid_smartcookies.smartcookie.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
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
}
