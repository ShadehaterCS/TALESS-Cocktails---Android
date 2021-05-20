package com.authandroid_smartcookies.smartcookie.Main.Activities;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Main.Adapters.SearchAdapter;
import com.authandroid_smartcookies.smartcookie.Util.GenericTextWatcher;
import com.authandroid_smartcookies.smartcookie.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/*
TODO cleanup code, add map and recyclerView.
TODO Using new simpler adapter rather than the same for home/favorites
 */
public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView searchAutoComplete;
    private TextView resultsTV;
    private Button searchButton;

    private String[] DRINKS;
    private String[] NAMES;
    private String[] complete;

    private ArrayList<CocktailRecipe> recipes;

    private ArrayList<CocktailRecipe> dataset;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        Objects.requireNonNull(getSupportActionBar()).hide();

        searchAutoComplete = findViewById(R.id.searchAutoComplete);
        recyclerView = findViewById(R.id.searchRecyclerView);
        initDataset();

        /*
        todo placeholder, will be removed and replaced with recyclerview dataset creator
         */
        searchAutoComplete.addTextChangedListener(
                new GenericTextWatcher(this::getNewRecipesAndUpdateDataset));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SearchAdapter(this, recipes));
    }

    @MainThread
    private void getNewRecipesAndUpdateDataset(){
        String searchable = searchAutoComplete.getText().toString();
        StringBuilder builder = new StringBuilder();
        if (Arrays.asList(DRINKS).contains(searchable))
            dataset = recipes.stream()
                    .filter(r -> r.get_drink().equals(searchable))
                    .collect(Collectors.toCollection(ArrayList::new));

        else if (Arrays.asList(NAMES).contains(searchable))
            dataset = recipes.stream()
                    .filter(r -> r.get_title().equals(searchable))
                    .collect(Collectors.toCollection(ArrayList::new));
        else if (searchable.isEmpty()) {
            dataset = new ArrayList<>(0);
            return;
        }
        else
            return;
        recyclerView.swapAdapter(new SearchAdapter(this,dataset), false);
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
        searchAutoComplete.setAdapter(adapter);
    }
}