package com.authandroid_smartcookies.smartcookie.DataClasses;
/*
Data class for cocktail recipes, only getter and setters, no utilities
 */
public class CocktailRecipe {
    private int _id;
    private String _title;
    private String _description;
    private String _steps;
    private String _drink;
    private String _imageid;
    private String _color;
    private String _calories;
    private String _preptime;
    private int _timer;

    public CocktailRecipe() {
    }

    public CocktailRecipe(int _id, String _title, String _description, String _steps, String _drink, String _imageid, String _color, String _calories, String _preptime, int _timer) {
        this._id = _id;
        this._title = _title;
        this._description = _description;
        this._steps = _steps;
        this._drink = _drink;
        this._imageid = _imageid;
        this._color = _color;
        this._calories = _calories;
        this._preptime = _preptime;
        this._timer = _timer;
    }

    public CocktailRecipe(String _title, String _description, String _steps, String _drink, String _imageid, String _color, int _timer) {
        this._title = _title;
        this._description = _description;
        this._steps = _steps;
        this._drink = _drink;
        this._imageid = _imageid;
        this._color = _color;
        this._calories = _calories;
        this._preptime = _preptime;
        this._timer = _timer;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_steps() {
        return _steps;
    }

    public void set_steps(String _steps) {
        this._steps = _steps;
    }

    public String get_drink() {
        return _drink;
    }

    public void set_drink(String _drink) {
        this._drink = _drink;
    }

    public String get_imageid() {
        return _imageid;
    }

    public void set_imageid(String _imageid) {
        this._imageid = _imageid;
    }

    public String get_color() {
        return _color;
    }

    public void set_color(String _color) {
        this._color = _color;
    }

    public String get_calories() {
        return _calories;
    }

    public void set_calories(String _calories) {
        this._calories = _calories;
    }

    public String get_preptime() {
        return _preptime;
    }

    public void set_preptime(String _preptime) {this._preptime = _preptime;}

    public int get_timer() {
        return _timer;
    }

    public void set_timer(int _timer) {
        this._timer = _timer;
    }
}
