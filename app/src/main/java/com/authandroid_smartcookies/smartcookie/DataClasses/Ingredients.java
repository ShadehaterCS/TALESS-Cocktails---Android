package com.authandroid_smartcookies.smartcookie.DataClasses;

public class Ingredients {
    private int id;
    private int recipeId;
    private String[] ingredients;
    private String[] measure;
    private int[] ingredientValue;

    public Ingredients(int recipeId, String[] ingredients, String[] measure, int[] ingredientValue) {
        this.recipeId = recipeId;
        this.ingredients = ingredients;
        this.measure = measure;
        this.ingredientValue = ingredientValue;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getMeasure() {
        return measure;
    }

    public void setMeasure(String[] measure) {
        this.measure = measure;
    }

    public int[] getIngredientValue() {
        return ingredientValue;
    }

    public void setIngredientValue(int[] ingredientValue) {
        this.ingredientValue = ingredientValue;
    }
}
