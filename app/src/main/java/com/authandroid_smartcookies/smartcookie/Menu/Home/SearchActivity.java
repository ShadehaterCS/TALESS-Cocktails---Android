package com.authandroid_smartcookies.smartcookie.Menu.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.R;

import java.util.ArrayList;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {
    private EditText search;
    private TextView results;
    private Button searchButton;
    SenpaiDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = SenpaiDB.getInstance(this);
        search = findViewById(R.id.searchText);
        results = findViewById(R.id.searchResults);
        searchButton = findViewById(R.id.search_button);

        searchButton.setOnClickListener(v -> {
            results.setText("NONE");
            String searchable = search.getText().toString();
            ArrayList<CocktailRecipe> recipes = db.getRecipesBasedOnSearch(searchable);
            StringBuilder builder = new StringBuilder();
            for (CocktailRecipe c : recipes)
                builder.append(c.get_title()).append("\n");
            String lol = builder.toString();

            results.setText(lol);
        });
    }

}