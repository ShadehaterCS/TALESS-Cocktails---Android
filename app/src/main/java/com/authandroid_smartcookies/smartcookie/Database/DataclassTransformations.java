package com.authandroid_smartcookies.smartcookie.Database;

import android.content.ContentValues;
import android.database.Cursor;
import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import java.util.ArrayList;
/**
 * @apiNote This class handles transformations from the raw SQL queries to objects and vice versa
 * @implNote All functions should be named after their transformations
 *           Always return a whole non-null object even if the cursor failed.
 */
public class DataclassTransformations {

    public static ArrayList<CocktailRecipe> transformToCocktailRecipeList(Cursor cursor) {
        ArrayList<CocktailRecipe> recipes = new ArrayList<>(15);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do{ recipes.add(transformToCocktailRecipe(cursor,false)); }
            while (cursor.moveToNext());
        }
        cursor.close();
        return recipes;
    }
/**
 * @param single Use when only a single result is to be returned
 */
    public static CocktailRecipe transformToCocktailRecipe(Cursor cursor,boolean single) {
        CocktailRecipe recipe = new CocktailRecipe();

        recipe.set_id(Integer.parseInt(cursor.getString(0)));
        recipe.set_title(cursor.getString(1));
        recipe.set_description(cursor.getString(2));
        recipe.set_steps(cursor.getString(3));
        recipe.set_drink(cursor.getString(4));
        recipe.set_imageid(cursor.getString(5));
        recipe.set_color(cursor.getString(6));
        recipe.set_timer(Integer.parseInt(cursor.getString(7)));
        if (single)
            cursor.close();
        return recipe;
    }

    /*
    Use this for easier creation of insertion statements
    @ex String[] test = new String[] { "Aviation", "gin" .... };
     */
    public static ContentValues transformStringArrayToContentValues(String[] sValues){
        ContentValues values = new ContentValues();
        values.put("title", sValues[0]);
        values.put("description", sValues[1]);
        values.put("steps", sValues[2]);
        values.put("drink", sValues[3]);
        values.put("imageid", sValues[4]);
        values.put("color",  sValues[5]);
        values.put("timer", sValues[6]);

        return values;
    }

    public static ContentValues transformRecipeToContentValues(CocktailRecipe recipe){
        ContentValues values = new ContentValues();
        values.put("title", recipe.get_title());
        values.put("description", recipe.get_description());
        values.put("steps", recipe.get_steps());
        values.put("drink", recipe.get_drink());
        values.put("imageid", recipe.get_imageid());
        values.put("color",  recipe.get_color());
        values.put("timer", String.valueOf(recipe.get_timer()));

        return values;
    }

}
