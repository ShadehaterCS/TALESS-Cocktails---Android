package com.authandroid_smartcookies.smartcookie.Database;

import android.content.ContentValues;

public class RecipeInsertStrings {
    /*TEMPLATE TEMPLATE TEMPLATE
    values.put("title", "");
         values.put("description", "");
         values.put("steps", "");
         values.put("drink", "");
         values.put("imageid", "NaN");
         values.put("color", "FFFFFF");
         values.put("timer", 40);
    */

    public static ContentValues[] getAllRecipeQueries(){
        return new ContentValues[]{
                aviationCocktail(), limeDaiquiri()
        };
    }
    public static ContentValues aviationCocktail(){
        ContentValues values = new ContentValues();
        values.put("title", "Classic Aviation");
        values.put("description", "The Aviation cocktail is a 1900''s mixed drink with a lovely purple hue! This sweet tart classic cocktail is so tasty, it''s now back in style.");
        values.put("steps", "Add the gin, lemon juice, maraschino liqueur, and crème de violette to a cocktail shaker. Fill it with ice and shake it until cold." +
                "|Strain into a cocktail glass. Garnish with a Luxardo cherry, if desired.");
        values.put("drink", "gin");
        values.put("imageid", "NaN");
        values.put("color", "FFFFFF");
        values.put("timer", 40);
        return values;
    }
     public static ContentValues limeDaiquiri(){
         ContentValues values = new ContentValues();
         values.put("title", "Lime Frozen Daiquiri");
         values.put("description", "This lime frozen daiquiri is easy to make and perfect for entertaining! Nothing says party like a frozen drink that’s tangy, sweet, and fruity all at once.");
         values.put("steps", "*The night before serving: Place everything in a freezer proof container and freeze overnight.|The day of serving, place the frozen ingredients into a blender and blend until smooth and fluffy. Pour into glasses and serve, garnished with lime slices.");
         values.put("drink", "rum");
         values.put("imageid", "NaN");
         values.put("color", "FFFFFF");
         values.put("timer", 40);
         return values;
     }


}
