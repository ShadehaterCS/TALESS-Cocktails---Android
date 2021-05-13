package com.authandroid_smartcookies.smartcookie.Database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class MyDatabaseHelper extends SQLiteOpenHelper{

    private Context context;
    private static final String DATABASE_NAME= "smartcookie.db";
    private static final int DATABASE_VERSION= 1;
    private static final String TABLE_NAME="recipesofcocktails";
    private static final String COLUMN_ID= "_id";
    private static final String COLUMN_TITLE="nameofcocktail";
    private static final String COLUMN_DESCRIPTION="description";
    private static final String COLUMN_STEPS="steps";
    private static final String COLUMN_MAINLIQ="mainliquor";
    private static final String COLUMN_IMAGEID="imageid";
    private static final String COLUMN_COLOR="colorofcocktail";
    private static final String COLUMN_TIMER="timer";

    public MyDatabaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE"+ TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_TITLE + " TEXT " +
                        COLUMN_DESCRIPTION +" TEXT " +
                        COLUMN_STEPS + " TEXT " +
                        COLUMN_MAINLIQ + " TEXT " +
                        COLUMN_IMAGEID + " INTEGER " +
                        COLUMN_COLOR + " TEXT " +
                        COLUMN_TIMER + " INTEGER;";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
