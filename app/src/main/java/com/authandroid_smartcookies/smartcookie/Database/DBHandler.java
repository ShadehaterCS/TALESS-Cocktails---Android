package com.authandroid_smartcookies.smartcookie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;

import java.io.File;
import java.lang.reflect.Array;
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

    public CocktailRecipe getRecipePlease() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM RECIPES";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        CocktailRecipe recipe = DataclassTransformations.transformToCocktailRecipe(cursor,true);
        cursor = db.rawQuery(query, null);
        ArrayList<CocktailRecipe> recipes = DataclassTransformations.transformToCocktailRecipeList(cursor);

        return recipe;
    }
}
