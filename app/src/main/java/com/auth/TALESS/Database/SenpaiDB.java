package com.auth.TALESS.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.auth.TALESS.DataClasses.CocktailRecipe;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class works as the connection between the application and the database file.
 * Force upgrade of database by incrementing DATABASE_VERSION
 */
public class SenpaiDB extends SQLiteOpenHelper {
    private static final String TAG = "SENPAI";
    public static String DB_PATH;
    public static String DB_NAME = "database.db";
    public static final int DATABASE_VERSION = 1;
    public static boolean updated = false;

    public static SenpaiDB instance;
    private SQLiteDatabase database;
    private WeakReference<Context> context;
    /**
     * Using singleton pattern to avoid leaks and constant openings
     * only one object will be statically accessible throughout the lifetime of the app
    */
    public static SenpaiDB getInstance(Context context) {
        if (instance == null) {
            instance = new SenpaiDB(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * The only constructor needed
     * Path to the database is dynamically created
     * @param context creates a WeakReference to the context to avoid context leaking
     */
    @SuppressLint("SdCardPath")
    public SenpaiDB(@NonNull Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        this.context = new WeakReference<>(context);
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    /**
     * Opens a connection to the database. Always closes the previous connection if it exists.
     * @throws SQLException if database object fails to be created from method upgradeDatabase()
     * Database versioning works by setting the final int to a higher number than the previous.
     * @link upgradeDatabase()
     * @return true if connection was established
     */
    public boolean openDatabase() {
        try {
            String path = DB_PATH + DB_NAME;
            if (database != null)
                database.close();
            database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
            int version = database.getVersion();
            if (version < DATABASE_VERSION)
                upgradeDatabase(context.get(), path, true);
            return true;
        } catch (SQLException s) {
            s.printStackTrace();
            return false;
        }
    }

    /**
     * Upgrade the database to the next version by deleting the previous .db file inside the app's
     * internal folder. Then calls createDatabase() to copy the new .db file.
     * @param context always use applicationContext
     * @param path path to the database, will exists through the constructor
     * @param saveFavorites saves the favorites ArrayList through upgrading.
     */
    public void upgradeDatabase(Context context, String path, boolean saveFavorites) {
        ArrayList<Integer> favorites = null;
        if (saveFavorites)
            favorites = getFavoritesIds();
        context.deleteDatabase(DB_NAME);
        createDatabase(context);
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        database.setVersion(DATABASE_VERSION);
        updated = true;
        if (saveFavorites)
            restoreFavoritesDuringUpgrade(favorites);
    }

    /**
     * Closes the connection to the database.
     * Synchronized as it could be executed from a background thread
     */
    public synchronized void close() {
        assert database != null;
        database.close();
        super.close();
    }

    /**
     * Creates the database if it doesn't already exists.
     * Should only be used if openDatabase() failed.
     * @param context always use application context
     * @throws SQLException if copying of .db file failed
     */
    public void createDatabase(Context context) {
        //this is some magic line idk? remove it and first load fails
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
    }

    /**
     * Copies the .db filed from the assets folder into the internal app directory on the device
     * @param context always use application context
     */
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

    /**
     * @param favorites the ArrayList containing the favorites
     *                  Whenever the database upgrades the file needs to be deleted and the new schema is to be
     *                  deployed.
     *                  Assuming FAVORITES table won't change (why would it) then this won't be a problem
     * @caution if FAVORITES schema changes this needs to be refactored to reflect that change
     */
    private void restoreFavoritesDuringUpgrade(ArrayList<Integer> favorites) {
        if (database != null)
            for (int recipe_id : favorites)
                database.execSQL("INSERT INTO FAVORITES(recipeid) values (" + recipe_id + ")");
    }

    /* All methods below use the same pattern of running an SQL Query on the database object
       and passing the Cursor returned from that Query to the DataclassTransformations class
       to be transformed to a usable object for the application
    */

    /**
     * Runs an SQL query on the database object
     * @return a DataClassTransformation method by passing the Cursor returned from the Query.
     */
    public ArrayList<CocktailRecipe> getAllRecipes() {
        if (database == null)
            return new ArrayList<>();
        String query = "SELECT * FROM RECIPES";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        return DataclassTransformations.transformToCocktailRecipeList(cursor);
    }

    public void addRecipeIntoFavorites(CocktailRecipe recipe) {
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
        getReadableDatabase();
        if (database == null || !database.isOpen())
            openDatabase();
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
                "where recipeid = " + recipe.get_id() + " "
                + "order by amount";
        Cursor cursor = database.rawQuery(query, null);
        return DataclassTransformations.transformToIngredientsHashMap(cursor);
    }
}
