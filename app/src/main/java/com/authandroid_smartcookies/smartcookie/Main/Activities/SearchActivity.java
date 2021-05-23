package com.authandroid_smartcookies.smartcookie.Main.Activities;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Main.Adapters.GeneralAdapter;
import com.authandroid_smartcookies.smartcookie.Util.GenericTextWatcher;
import com.authandroid_smartcookies.smartcookie.R;
import com.authandroid_smartcookies.smartcookie.Util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

//todo autocomplete requests focus
public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView searchAutoComplete;
    private ArrayList<String> DRINKS;
    private ArrayList<String> NAMES;
    private ArrayList<String> PARTIAL_NAMES;
    private ArrayList<String> completeStringDataset;

    private ArrayList<CocktailRecipe> recipes;

    private ArrayList<CocktailRecipe> dataset;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Utilities.setAnimationAndExcludeTargets(getWindow());
        Objects.requireNonNull(getSupportActionBar()).hide();

        searchAutoComplete = findViewById(R.id.searchAutoComplete);
        recyclerView = findViewById(R.id.searchRecyclerView);
        initDataset();

        searchAutoComplete.addTextChangedListener(
                new GenericTextWatcher(this::getNewRecipesAndUpdateDataset));

        searchAutoComplete.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE)
                Utilities.hideKeyboard(this);
            return true;
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new GeneralAdapter(this, recipes));
    }

    @MainThread
    private void getNewRecipesAndUpdateDataset(){
        String dirtyInput = searchAutoComplete.getText().toString();
        String searchable = !dirtyInput.isEmpty()
                ? dirtyInput.substring(0, 1).toUpperCase(Locale.getDefault()) + dirtyInput.substring(1)
                : "";

        if (DRINKS.contains(searchable))
            dataset = recipes.stream()
                    .filter(r -> r.get_drink().equals(searchable))
                    .collect(Collectors.toCollection(ArrayList::new));
        else if (NAMES.contains(searchable))
            dataset = recipes.stream()
                    .filter(r -> r.get_title().equals(searchable))
                    .collect(Collectors.toCollection(ArrayList::new));
        else if (PARTIAL_NAMES.contains(searchable)){
            dataset = recipes.  stream()
                    .filter(r -> Arrays.asList(r.get_title().split(" ")).contains(searchable))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        else if (searchable.isEmpty())
            dataset = new ArrayList<>(recipes);
        else
            return;
        //Only runs if dataset changed
        recyclerView.swapAdapter(new GeneralAdapter(this,dataset), false);
    }

    @MainThread
    @SuppressWarnings("unchecked")
    private void initDataset(){
        recipes = (ArrayList<CocktailRecipe>) getIntent().getSerializableExtra("recipes");
        DRINKS = recipes.stream()
                .map(CocktailRecipe::get_drink)
                .distinct().collect(Collectors.toCollection(ArrayList::new));

        NAMES = recipes.stream()
                .map(CocktailRecipe::get_title)
                .distinct().collect(Collectors.toCollection(ArrayList::new));

        PARTIAL_NAMES = NAMES.stream()
                .flatMap(fullDrinkName -> Arrays.stream(fullDrinkName.split(" ").clone()))
                .distinct().collect(Collectors.toCollection(ArrayList::new));

        completeStringDataset = new ArrayList<>(DRINKS);
        completeStringDataset.addAll(PARTIAL_NAMES);
        completeStringDataset.addAll(NAMES);

        searchAutoComplete.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, completeStringDataset));
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchAutoComplete.clearFocus();
    }
}