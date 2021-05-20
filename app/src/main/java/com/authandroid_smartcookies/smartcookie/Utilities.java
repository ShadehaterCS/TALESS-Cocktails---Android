package com.authandroid_smartcookies.smartcookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utilities {
    public static void make_show_SnackBar(View view, String message, int duration){
        Snackbar snackbar = Snackbar.make(view, message, duration)
                .setAction("Undo", new undoListener());
        snackbar.show();
    }
    public static  boolean restorePrefData(Context context) {

        SharedPreferences pref = context.getSharedPreferences("myPrefs",context.MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened",false);
        return  isIntroActivityOpenedBefore;
    }

    public static void savePrefsData(Context context) {

        SharedPreferences pref = context.getSharedPreferences("myPrefs",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();


    }
    public static class undoListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {


        }
    }
}
