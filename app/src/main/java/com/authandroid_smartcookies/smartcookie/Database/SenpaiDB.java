package com.authandroid_smartcookies.smartcookie.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SenpaiDB extends SQLiteOpenHelper {
    private static final String TAG = "SENPAI";
    public static String DB_PATH;
    public static String DB_NAME = "database.db";
    public static final int DATABASE_VERSION = 2;
    public Context CONTEXT;

    public SenpaiDB(@NonNull Context context){
        super(context, DB_NAME, null, DATABASE_VERSION);
        DB_PATH = context.getFilesDir().getPath();
        CONTEXT = context;
    }

    public SenpaiDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        assert context != null;
        DB_PATH = context.getFilesDir().getPath();
        CONTEXT = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        copyDatabase(CONTEXT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public static void copyDatabase(Context context) {
        try {
            InputStream myInput = context.getAssets().open(DB_NAME);
            // Path to the just created empty db
            String outFileName = DB_PATH + DB_NAME;
            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CocktailRecipe getRecipePlease() {
        SQLiteDatabase db = this.getReadableDatabase();
        /*String query = "SELECT * FROM RECIPES";
        Cursor cursor = db.rawQuery(query, null);
        CocktailRecipe recipe = new CocktailRecipe();
        if (cursor.moveToFirst()) {
            recipe.set_id(Integer.parseInt(cursor.getString(0)));
            recipe.set_title(cursor.getString(1));
            recipe.set_description(cursor.getString(2));
            recipe.set_steps(cursor.getString(3));
            recipe.set_drink(cursor.getString(4));
            recipe.set_imageid(cursor.getString(5));
            recipe.set_color(cursor.getString(6));
            recipe.set_calories(cursor.getString(7));
            recipe.set_preptime(cursor.getString(8));
            recipe.set_timer(Integer.parseInt(cursor.getString(9)));
            cursor.close();
        } else
            recipe = null;*/


        return new CocktailRecipe();
    }

    public SQLiteDatabase checkDatabase(){
        return SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null,
                SQLiteDatabase.OPEN_READWRITE);
    }
}
