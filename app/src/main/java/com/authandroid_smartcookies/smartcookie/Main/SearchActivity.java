package com.authandroid_smartcookies.smartcookie.Main;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
/*
TODO cleanup code, add map and recyclerView.
TODO Using new simpler adapter rather than the same for home/favorites
 */
public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView search;
    private TextView results;
    private Button searchButton;

    private String[] DRINKS;
    private String[] NAMES;
    private String[] complete;

    ArrayList<CocktailRecipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Objects.requireNonNull(getSupportActionBar()).hide();

        search = findViewById(R.id.searchText);
        results = findViewById(R.id.searchResults);
        searchButton = findViewById(R.id.search_button);

        initDataset();

        /*
        todo placeholder, will be removed and replaced with recyclerview dataset creator
         */
        search.setOnClickListener(View::showContextMenu);
        searchButton.setOnClickListener(v -> {
            String searchable = search.getText().toString();
            StringBuilder builder = new StringBuilder();

            if (Arrays.asList(DRINKS).contains(searchable)) {
                for (CocktailRecipe r : recipes)
                    if (r.get_drink().equals(search.getText().toString()))
                        builder.append(r.get_title()).append("\n");
            }
            else if (Arrays.asList(NAMES).contains(searchable)){
                for (CocktailRecipe r : recipes)
                    if (r.get_title().equals(search.getText().toString()))
                        builder.append(r.get_title()).append("\n");
            }
            else{
                results.setText("No results found");
                return;
            }

            results.setText(builder.toString());
        });
    }

    @MainThread
    @SuppressWarnings("unchecked")
    private void initDataset(){
        recipes = (ArrayList<CocktailRecipe>) getIntent().getSerializableExtra("recipes");
        DRINKS = recipes.stream()
                .map(CocktailRecipe::get_drink)
                .distinct().toArray(String[]::new);
        NAMES = recipes.stream()
                .map(CocktailRecipe::get_title)
                .distinct().toArray(String[]::new);
        complete = Stream.of(DRINKS,NAMES).flatMap(Stream::of).toArray(String[]::new);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, complete);
        search.setAdapter(adapter);
    }
}