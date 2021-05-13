package com.authandroid_smartcookies.smartcookie.Database;

public class TableCreateSQL {
    private static final String CREATE_RECIPES_TABLE = "CREATE TABLE RECIPES (\n" +
            "id integer primary key autoincrement," +
            "title varchar," +
            "description varchar," +
            "steps varchar," +
            "drink varchar," +
            "imageid varchar," +
            "color varchar," +
            "timer int)";
    private static final String CREATE_RECIPE_INGREDIENTS_TABLE = "CREATE TABLE RECIPE_INGREDIENTS (" +
            "id integer primary key autoincrement," +
            "recipeId integer," +
            "ingredients varchar," +
            "measure varchar," +
            "ingredients_values varchar)";
    private static final String CREATE_FAVORITES_TABLE = " CREATE TABLE FAVORITES (" +
            "id integer primary key autoincrement," +
            "recipeid integer)";

    public static String[] getSQLStrings(){
        return new String[] { CREATE_RECIPES_TABLE, CREATE_RECIPE_INGREDIENTS_TABLE, CREATE_FAVORITES_TABLE };
    }
}
