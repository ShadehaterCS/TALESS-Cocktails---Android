package com.authandroid_smartcookies.smartcookie.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SenpaiDB extends SQLiteOpenHelper {
    private static final String TAG = "SENPAI";
    public static String DB_PATH;
    public static String DB_NAME = "database.db";
    public static final int DATABASE_VERSION = 3;

    public static SenpaiDB instance;
    private SQLiteDatabase database;
    public Context context;

    /*
    Using singleton pattern to avoid leaks and constant openings
    */
    public static SenpaiDB getInstance(Context context) {
        if (instance == null) {
            instance = new SenpaiDB(context.getApplicationContext());
        }
        return instance;
    }

    public boolean openDatabase() {
        try {
            String path = DB_PATH + DB_NAME;
            database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
            return true;
        } catch (SQLException s) {
            s.printStackTrace();
            return false;
        }
    }

    public synchronized void close() {
        assert database != null;
        database.close();
        super.close();
    }

    public SenpaiDB(@NonNull Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        this.context = context;
    }

    public void createDatabase(Context context) {
        //this is important for some reason? prevents an initialization crash idk why
        this.getReadableDatabase();
        try {
            copyDatabase(context);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Error copying database");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (instance != null) {
            instance.context.deleteDatabase(DB_NAME);
            createDatabase(context);
        }

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


    public ArrayList<CocktailRecipe> getAllRecipes() {
        if (database == null)
            return new ArrayList<>();
        String query = "SELECT * FROM RECIPES";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        return DataclassTransformations.transformToCocktailRecipeList(cursor);
    }

    public void insertRecipeIntoFavorites(CocktailRecipe recipe) {
        if (database != null)
            database.execSQL("INSERT INTO FAVORITES(recipeid) values (" + recipe.get_id() + ")");
    }

    public void removeRecipeFromFavorites(CocktailRecipe recipe) {
        if (database != null)
            database.execSQL("delete from FAVORITES where recipeid =" + recipe.get_id());
    }

    public ArrayList<Integer> getFavoritesIds() {
        if (database == null)
            return new ArrayList<>();
        String query = "SELECT recipeid FROM FAVORITES";
        Cursor cursor = database.rawQuery(query, null);
        return DataclassTransformations.transformFavoritesToList(cursor);
    }

    public ArrayList<CocktailRecipe> getFavoriteRecipes() {
        if (database == null)
            return new ArrayList<>();
        String query = "SELECT id, title,description,steps,drink,imageid, color, preptime,calories,timer" +
                " FROM RECIPES INNER JOIN FAVORITES ON RECIPES.recipeid= FAVORITES.recipeid";
        Cursor cursor = database.rawQuery(query, null);
        return DataclassTransformations.transformToCocktailRecipeList(cursor);
    }

    public HashMap<String, String> getIngredients(CocktailRecipe recipe) {
        if (database == null)
            return new HashMap<>();
        String query = "select ingredient, amount from INGREDIENTS_RECIPES\n" +
                "left join INGREDIENTS on INGREDIENTS_RECIPES.ingredientid = INGREDIENTS.ingredientid\n" +
                "where recipeid = " + recipe.get_id();
        Cursor cursor = database.rawQuery(query, null);
        return DataclassTransformations.transformToIngredientsHashMap(cursor);
    }
}
