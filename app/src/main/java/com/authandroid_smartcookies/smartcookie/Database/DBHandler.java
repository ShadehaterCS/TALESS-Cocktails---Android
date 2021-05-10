package com.authandroid_smartcookies.smartcookie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;


public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SmartCookie.db";

    public static final String TABLE_NAME = "recipes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE= "title";
    public static final String COLUMN_IMAGEID = "imageid";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DRINK = "drink";
    public static final String COLUMN_TIMER= "timer";

    public DBHandler(Context context, String name,
                     SQLiteDatabase.CursorFactory factory, int DATA) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_RECIPE_TABLE =
            "CREATE TABLE " +
                TABLE_NAME + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_IMAGEID + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_DRINK + " TEXT," +
                    COLUMN_TIMER + " TEXT" + ")";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RECIPE_TABLE);
    }

    public void addRecipe(CocktailRecipe recipe){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, recipe.get_id());
        values.put(COLUMN_TITLE, recipe.get_title());
        values.put(COLUMN_IMAGEID, recipe.get_imageID());
        values.put(COLUMN_DESCRIPTION, recipe.get_description());
        values.put(COLUMN_DRINK, recipe.get_drink());
        values.put(COLUMN_TIMER, recipe.get_timer());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
