/*
package com.authandroid_smartcookies.smartcookie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Menu.FavoritesFragment;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "database.db";
    public static String DB_PATH;

    public DBHandler(Context context, String name,
                     SQLiteDatabase.CursorFactory factory, int DATA) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DB_PATH = context.getPackageName() + "/databases/SmartCookie.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL Create Table statements
        for (String statement : TableCreateSQL.getSQLStrings())
            db.execSQL(statement);

        //Recipe Values to be inserted
        for (ContentValues recipeValues : RecipeInsertStrings.getAllRecipeQueries())
            db.insert("RECIPES", null, recipeValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS RECIPES");
        db.execSQL("DROP TABLE IF EXISTS RECIPE_INGREDIENTS");
        db.execSQL("DROP TABLE IF EXISTS FAVORITES");
        onCreate(db);
    }

    //SQL Statements
    public CocktailRecipe getRecipePlease() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM RECIPES";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return DataclassTransformations.transformToCocktailRecipe(cursor, true);
    }

    public ArrayList<CocktailRecipe> findAllBasedOnDrink(String drink) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM RECIPES WHERE drink = "+drink;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return DataclassTransformations.transformToCocktailRecipeList(cursor);
    }

    public boolean insertRecipeIntoFavorites(CocktailRecipe recipe) {
        ContentValues insertion = new ContentValues();
        insertion.put("recipeid", recipe.get_id());
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert("FAVORITES", null, insertion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
*/
