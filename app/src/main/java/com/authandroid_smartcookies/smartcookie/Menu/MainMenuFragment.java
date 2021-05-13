package com.authandroid_smartcookies.smartcookie.Menu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.DBHandler;
import com.authandroid_smartcookies.smartcookie.R;

import java.util.UUID;

public class MainMenuFragment extends Fragment {

    DBHandler dbhandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dbhandler = new DBHandler(this.getContext(), null,null, 1);
    }

    private void addrecipe(DBHandler db){
        CocktailRecipe recipe = new CocktailRecipe(
                UUID.randomUUID().toString(),
                "Classic Aviation Cocktail",
                "0",
                "1",
                "20",
                "gin");
        db.addRecipe(recipe);
    }
}