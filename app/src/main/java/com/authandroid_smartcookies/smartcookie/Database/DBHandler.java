package com.authandroid_smartcookies.smartcookie.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;

import java.io.File;

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SmartCookie.db";
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
        String q1 = "CREATE TABLE RECIPES (\n" +
                "    id integer primary key,\n" +
                "    title varchar,\n" +
                "    description varchar,\n" +
                "    steps varchar,\n" +
                "    drink varchar,\n" +
                "    imageid varchar,\n" +
                "    color varchar,\n" +
                "    calories varchar,\n" +
                "    preptime varchar,\n" +
                "    timer int)";

        String q2 = "CREATE TABLE RECIPE_INGREDIENTS (\n" +
                "    id integer primary key,\n" +
                "    recipeId integer,\n" +
                "    ingredients varchar,\n" +
                "    measure varchar,\n" +
                "    ingredients_values varchar)";


        String r1 = "insert into recipes (title, description, steps, drink, imageid, color, calories, perpime, timer)\n" +
                "values ('Classic Aviation', \n"
                +"'The Aviation cocktail is a 1900''s mixed drink with a lovely purple hue! This sweet tart classic cocktail is so tasty, it''s now back in style.',\n"
                +"'1.Add the gin, lemon juice, maraschino liqueur, and crème de violette to a cocktail shaker. Fill it with ice and shake it for 30sec.2.Strain into a cocktail glass. Garnish with a Luxardo cherry, if desired.', \n"
                +"'Gin',"
                +"'Aviation.jpg',"
                +"'Purple',"
                +"'165 calories',"
                +"'5 minutes',"
                +"30)";

        /*
            2 ounces (4 tablespoons) gin|3/4 ounce (1 1/2 tablespoons) fresh lemon juice|1/2 ounce (1 tablespoon) Maraschino liqueur|1/2 ounce (1 tablespoon) crème de violette|For the garnish: Luxardo cocktail cherry
        */

        String r2 = "insert into recipes (title, description, steps, drink, imageid, color, calories, perpime, timer)\n" +
                "values ('Floradora', \n"
                +"'The Floradora is a classic gin cocktail everyone will love! Raspberry, lime, and bubbly ginger beer are the perfect pairing with floral gin.',\n"
                +"'1.In a highball glass or stemmed glass, add the gin, raspberry syrup or Chambord, and lime juice.2.Fill with ice and top with ginger beer. Garnish with a lime wedge and raspberry, if desired.', \n"
                +"'Gin',"
                +"'Floradora.jpg',"
                +"'Rose',"
                +"'196 calories',"
                +"'3 minutes',"
                +"30)";

        /*
            2 ounces* gin|1/2 ounce raspberry syrup or Chambord liqueur|1/2 ounce lime juice|3 ounces ginger beer|Ice, for serving (try clear ice!)|For the garnish: Lime wedge, raspberry (optional)
        */

        String r3 = "insert into recipes (title, description, steps, drink, imageid, color, calories, perpime, timer)\n" +
                "values ('Bloody Caesar', \n"
                +"'The Floradora is a classic gin cocktail everyone will love! Raspberry, lime, and bubbly ginger beer are the perfect pairing with floral gin.',\n"
                +"'1.If time allows, chill the clamato juice and vodka. Shake the clamato juice before pouring.2.In a pitcher, combine the clamato juice, lemon juice, Worcestershire sauce, horseradish, Tabasco, celery salt and black pepper. Stir. Serve immediately or refrigerate up to 1 day.3.To serve, on a plate place a mixture of roughly half kosher salt and half Old Bay seasoning (or celery salt). Cut a notch in a lemon wedge, then run it around the rim of a glass. Dip the edge of the rim into a plate of salt.4.To each glass, add 2 ounces (1/4 cup) of vodka and 1/2 cup of bloody caesar mix and stir gently to combine. Fill the glass with ice and add the garnishes.', \n"
                +"'Vodka',"
                +"'Bloody Caesar.jpg',"
                +"'Rose',"
                +"'160 calories',"
                +"'5 minutes',"
                +"30)";

        /*
            2 cups clamato juice, chilled|1/4 cup fresh lemon juice|2 teaspoons Worcestershire sauce (vegan as desired)|1/2 teaspoon Tabasco hot sauce, or more to taste|1/2 teaspoon celery salt|1/8 teaspoon black pepper|1 cup (8 ounces) vodka, chilled|Ice, for serving (try clear ice!)|For the rim: Old Bay seasoning (purchased or homemade) and kosher salt|For the garnish: celery stalk, lemon wedge, olive, cocktail onion (use cocktail picks if desired)
        */

        String r4 = "insert into recipes (title, description, steps, drink, imageid, color, calories, perpime, timer)\n" +
                "values ('Blue Hawaii', \n"
                +"'Here are the ingredients you need to make the Blue Hawaii! Its ocean-tinted hue is thanks to blue curacao liqueur.',\n"
                +"'1.Place the rum, vodka, blue curacao, pineapple juice, lime juice, lemon juice and simple syrup in a cocktail shaker. Add 2 handfuls of ice and shake until cold.2.Strain the drink into a hurricane glass filled with crushed ice. Garnish with a pineapple wedge and cherry.', \n"
                +"'Vodka',"
                +"'Blue Hawaii.jpg',"
                +"'Blue',"
                +"'336 calories',"
                +"'5 minutes',"
                +"30)";
        /*
            1 ounce white rum|1 ounce vodka|3/4 ounce blue curacao|2 ounces pineapple juice|1/2 ounce lime juice (or use 1 1/2 ounces homemade sour mix for the lime, lemon and simple syrup)|1/2 ounce lemon juice|1/2 ounce simple syrup|Crushed ice, for serving|For the garnish: pineapple wedge, cocktail cherry
        */

        String r5 = "insert into recipes (title, description, steps, drink, imageid, color, calories, perpime, timer)\n" +
                "values ('Brown Derby', \n"
                +"'The retro Brown Derby cocktail pairs bourbon whiskey with grapefruit and honey syrup: it’s sweet, citrusy and refreshing!',\n"
                +"'1.Add the bourbon, grapefruit juice, and honey syrup to a cocktail shaker. Fill it with ice and shake it until cold.2.Strain into a cocktail glass. If desired, garnish with a grapefruit slice.', \n"
                +"'Bourbon',"
                +"'Brown Derby.jpg',"
                +"'Orange',"
                +"'182 calories',"
                +"'5 minutes',"
                +"30)";
        /*
            3 ounces (6 tablespoons) bourbon whiskey|1 1/2 ounces (3 tablespoons) fresh squeezed grapefruit juice|3/4 ounce (1 1/2 tablespoons) honey syrup|For the garnish: grapefruit slice
        */

        String r6 = "insert into recipes (title, description, steps, drink, imageid, color, calories, perpime, timer)\n" +
                "values ('Blackberry Bourbon Smash', \n"
                +"'Looking for a standout bourbon cocktail? This blackberry bourbon smash is refreshing and perfect for parties, featuring blackberries, lime, and fresh mint.',\n"
                +"'1.Place the lime, berries, and mint leaves in cocktail shaker. Muddle (gently mash) them 4 to 5 times to extract their flavor.2.Add the bourbon, maple syrup and 4 ice cubes. Shake until cold, then pour everything into a glass. If desired, add a splash of soda water. Serve with crushed ice.', \n"
                +"'Bourbon',"
                +"'Blackberry Bourbon Smash.jpg',"
                +"'Crimson',"
                +"'70 calories',"
                +"'5 minutes',"
                +"30)";
        /*
            1/4 lime, cut into 2 wedges|8 blackberries|5 large mint leaves|2 ounces (4 tablespoons) bourbon|1/2 ounce (1 tablespoon) or simple syrup or maple syrup|Crushed ice, for serving
        */

        String r7 = "insert into recipes (title, description, steps, drink, imageid, color, calories, perpime, timer)\n" +
                "values ('Classic Daiquiri', \n"
                +"'One of the most classic sour cocktails! This daiquiri recipe has the perfect balance of boozy, tart and sweet with rum, lime and sweetener.',\n"
                +"'1.Add the rum, lime juice, and syrup to a cocktail shaker. Fill it with ice and shake until cold.2.Strain into a cocktail glass. Serve garnished with a lime slice.', \n"
                +"'White rum',"
                +"'Classic Daiquiri.jpg',"
                +"'White',"
                +"'152 calories',"
                +"'5 minutes',"
                +"30)";
        /*
            1 1/2 ounces (3 tablespoons) best quality light or white rum|1 ounce (2 tablespoons) lime juice|1/2 ounce (1 tablespoon) simple syrup or maple syrup|Lime slice, for garnish
        */

        String r8 = "insert into recipes (title, description, steps, drink, imageid, color, calories, perpime, timer)\n" +
                "values ('Limoncello Mojito', \n"
                +"'Here’s the best limoncello cocktail: a Mojito! The lemon liqueur makes a incredibly refreshing drink paired with rum and fresh mint.',\n"
                +"'1.In a cocktail shaker, muddle the mint leaves with the lime juice and syrup.2.Add the limoncello and rum and fill the cocktail shaker with ice. Shake until cold.3.Fill a hurricane or highball glass with ice, then strain in the liquid. Top off the glass with soda water. Garnish with additional mint leaves.', \n"
                +"'White rum',"
                +"'Limoncello Mojito.jpg',"
                +"'Light green',"
                +"'125 calories',"
                +"'5 minutes',"
                +"30)";
        /*
            3 mint leaves, plus more for garnish|1 ounce (2 tablespoons) lime juice|1/2 ounce (1 tablespoon) simple syrup|1 ounces (2 tablespoons) limoncello|1 ounces (2 tablespoons) white rum|4 ounces (1/2 cup) soda water|Ice, for serving
        */

        String r9 = "insert into recipes (title, description, steps, drink, imageid, color, calories, perpime, timer)\n" +
                "values ('Bloody Maria', \n"
                +"'Swap out the vodka in a Bloody Mary for tequila, and you’ve got the Bloody Maria! This simple change makes for a Mexican style spin on the drink.',\n"
                +"'1.If time allows, chill the tomato juice and tequila. Shake the tomato juice before pouring.2.In a large cocktail shaker, combine the tomato juice, lemon juice, Worcestershire sauce, horseradish, hot sauce, celery salt and black pepper. Shake well to combine (without ice). Strain into a quart mason jar or small pitcher.3.To serve, on a plate place a mixture of roughly half kosher salt and half Old Bay seasoning (or chili powder). Cut a notch in a lime wedge, then run it around the rim of a glass. Dip the edge of the rim into a plate of salt.4.To each glass, add 2 ounces (1/4 cup) of tequila and 1/2 cup of Bloody Maria mix and stir gently to combine. Fill the glass with ice and add the garnishes. Squeeze in the lime wedge before serving.', \n"
                +"'Tequila',"
                +"'Bloody Maria.jpg',"
                +"'Red',"
                +"'160  calories',"
                +"'5 minutes',"
                +"30)";
        /*
            2 cups tomato juice, chilled|1/4 cup fresh lemon juice|2 teaspoons Worcestershire sauce|2 teaspoons prepared horseradish1/2 teaspoon Mexican hot sauce|1/2 teaspoon celery salt|1/8 teaspoon black pepper|1 cup (8 ounces) tequila, chilled|Ice, for serving|For the rim: Old Bay seasoning (purchased or homemade) or chili powder, kosher salt
        */

        String r10 = "insert into recipes (title, description, steps, drink, imageid, color, calories, perpime, timer)\n" +
                "values ('Agave Margarita', \n"
                +"'This agave margarita recipe is sweetened to perfection with agave nectar, giving this classic cocktail a little upgrade!',\n"
                +"'1.Cut a notch in a lime wedge, then run the lime around the rim of a glass. Dip the edge of the rim into a plate of salt.2.Place all ingredients in a cocktail shaker with 1 handful ice cubes and shake until cold.3.Strain the margarita into the glass with the salted rim. Fill the glass with ice and serve.', \n"
                +"'Tequila',"
                +"'Agave Margarita.jpg',"
                +"'White',"
                +"'144 calories',"
                +"'5 minutes',"
                +"30)";
        /*
            2 ounces tequila reposado|1 1/2 ounces fresh lime juice|1/2 ounce agave nectar|1/2 ounce Triple Sec|Kosher salt or flaky sea salt, for the rim|Ice, for serving
        */




        db.execSQL(q1);
        db.execSQL(q2);

        //template first row
        db.execSQL(r1);
        //db.execSQL(e1);

        db.execSQL(r2);
        //db.execSQL(e2);

        db.execSQL(r3);
        //db.execSQL(e3);

        db.execSQL(r4);
        //db.execSQL(e4);

        db.execSQL(r5);
        //db.execSQL(e5);

        db.execSQL(r6);
        //db.execSQL(e6);

        db.execSQL(r7);
        //db.execSQL(e7);

        db.execSQL(r8);
        //db.execSQL(e8);

        db.execSQL(r9);
        //db.execSQL(e9);

        db.execSQL(r10);
        //db.execSQL(e10);;


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public CocktailRecipe getRecipePlease() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM RECIPES";
        Cursor cursor = db.rawQuery(query, null);
        CocktailRecipe recipe = new CocktailRecipe();
        if (cursor.moveToFirst()) {
            recipe.set_id(Integer.parseInt(cursor.getString(0)));
            recipe.set_title(cursor.getString(1));
            recipe.set_description(cursor.getString(2));
            recipe.set_steps(cursor.getString(3));
            recipe.set_drink(cursor.getString(4));
            recipe.set_imageid(cursor.getString(5));
            recipe.set_color(cursor.getString(6));
            recipe.set_calories(cursor.getString(7));
            recipe.set_preptime(cursor.getString(8));
            recipe.set_timer(Integer.parseInt(cursor.getString(9)));
            cursor.close();
        } else
            recipe = null;

        return recipe;
    }
}
