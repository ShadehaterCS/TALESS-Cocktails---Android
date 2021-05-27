package com.auth.TALESS.Database;

import android.database.Cursor;

import com.auth.TALESS.DataClasses.CocktailRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @apiNote This class handles transformations from the raw SQL queries to objects and vice versa
 * @implNote All functions should be named after their transformations and accept Cursor objects
 * Always return a whole non-null object even if the cursor failed (such as empty ArrayList)
 */
public class DataclassTransformations {
    public static ArrayList<CocktailRecipe> transformToCocktailRecipeList(Cursor cursor) {
        ArrayList<CocktailRecipe> recipes = new ArrayList<>(15);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            do
                recipes.add(transformToCocktailRecipe(cursor, false));
            while (cursor.moveToNext());
        cursor.close();
        return recipes;
    }

    /**
     * @param single Use when only a single result is to be returned
     */
    public static CocktailRecipe transformToCocktailRecipe(Cursor cursor, boolean single) {
        CocktailRecipe recipe = new CocktailRecipe();
        recipe.set_id(Integer.parseInt(cursor.getString(0)));
        recipe.set_title(cursor.getString(1));
        recipe.set_description(cursor.getString(2));
        recipe.set_steps(cursor.getString(3));
        recipe.set_drink(cursor.getString(4));
        recipe.set_imageid(cursor.getString(5));
        recipe.set_color(cursor.getString(6));
        recipe.set_preptime(cursor.getString(7));
        recipe.set_calories(cursor.getString(8));
        recipe.set_timer(Integer.parseInt(cursor.getString(9)));
        if (single)
            cursor.close();
        return recipe;
    }

    public static ArrayList<Integer> transformFavoritesToList(Cursor cursor) {
        ArrayList<Integer> array = new ArrayList<>();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            int id = Integer.parseInt(cursor.getString(0));
            if (!array.contains(id))
                array.add(id);
            cursor.moveToNext();
        }
        cursor.close();
        return array;
    }

    public static HashMap<String, String> transformToIngredientsHashMap(Cursor cursor){
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String ingredient = cursor.getString(0) + "\t";
            String amount = cursor.getString(1);
            if (amount.equals("FG"))
                amount = "(For Garnish)";
            if (amount.equals("FS"))
                amount = "(For Serving)";
            map.put(ingredient,amount);
            cursor.moveToNext();
        }
        cursor.close();
        return map;
    }
}
