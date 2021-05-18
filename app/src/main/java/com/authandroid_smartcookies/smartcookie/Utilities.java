package com.authandroid_smartcookies.smartcookie;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utilities {
    public static void make_show_SnackBar(View view, String message, int duration){
        Snackbar snackbar = Snackbar.make(view, message, duration)
                .setAction("Undo", new undoListener());
        snackbar.show();
    }

    public static class undoListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

}
