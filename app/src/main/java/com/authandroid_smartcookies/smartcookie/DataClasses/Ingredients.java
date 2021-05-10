package com.authandroid_smartcookies.smartcookie.DataClasses;

public class Ingredients {
    private String[] ingredients;
    private String[] measure;
    private int[] ingredientValue;

    public Ingredients(String[] ingredients, String[] measure, int[] inredientValue) {
        this.ingredients = ingredients;
        this.measure = measure;
        this.ingredientValue = inredientValue;
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

    public int[] getInredientValue() {
        return ingredientValue;
    }

    public void setInredientValue(int[] inredientValue) {
        this.ingredientValue = inredientValue;
    }
}
