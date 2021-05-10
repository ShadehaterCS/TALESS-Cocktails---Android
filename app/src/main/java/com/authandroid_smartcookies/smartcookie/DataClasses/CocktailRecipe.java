package com.authandroid_smartcookies.smartcookie.DataClasses;

/*
Data class for cocktail recipes, only getter and setters, no utilities
 */
public class CocktailRecipe {
    private String _id;
    private String _title;
    private String _imageID;
    private String _description;
    private String _drink;
    private String _timer;

    public CocktailRecipe(String _id, String _title, String _imageID,
                          String _description, String _drink, String _timer) {
        this._id = _id;
        this._title = _title;
        this._imageID = _imageID;
        this._description = _description;
        this._timer = _timer;
        this._drink = _drink;
    }

    public String get_drink() { return _drink; }

    public void set_drink(String _drink) { this._drink = _drink; }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_imageID() {
        return _imageID;
    }

    public void set_imageID(String _imageID) {
        this._imageID = _imageID;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_timer() {
        return _timer;
    }

    public void set_timer(String _timer) {
        this._timer = _timer;
    }
}
