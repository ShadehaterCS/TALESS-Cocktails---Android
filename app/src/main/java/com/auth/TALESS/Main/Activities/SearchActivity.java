package com.auth.TALESS.Main.Activities;

import androidx.annotation.AnyThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.auth.TALESS.DataClasses.CocktailRecipe;
import com.auth.TALESS.Main.Adapters.GeneralAdapter;
import com.auth.TALESS.Util.GenericTextWatcher;
import com.auth.TALESS.R;
import com.auth.TALESS.Util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class implements the Search Activity.
 * Comprised of an AutoCompleteTextView with a dynamically created dataset and a RecyclerView using
 * the GeneralAdapter class.
 * @link GeneralAdapter
 * @link GenericTextWatcher
 */
public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView searchAutoComplete;
    private ArrayList<String> DRINKS;
    private ArrayList<String> NAMES;
    private ArrayList<String> PARTIAL_NAMES;
    private ArrayList<CocktailRecipe> recipes;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Utilities.setAnimationAndExcludeTargets(getWindow());
        Objects.requireNonNull(getSupportActionBar()).hide();

        searchAutoComplete = findViewById(R.id.searchAutoComplete);
        recyclerView = findViewById(R.id.searchRecyclerView);
        initAutoCompleteDataset();

        searchAutoComplete.addTextChangedListener(
                new GenericTextWatcher(this::getNewRecipesAndUpdateDataset));

        searchAutoComplete.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE)
                Utilities.hideKeyboard(this);
            return true;
        });
        recipes.sort(Comparator.comparing(CocktailRecipe::get_title));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new GeneralAdapter(this, recipes));
    }

    /**
     * Dynamically creates the ArrayList<CocktailRecipe> to be used by the RecyclerView adapter
     * Transforms the input text to have capital first letter as all ingredients and recipes use that pattern.
     * Searches in every ArrayList if the searchText is contained in them and adds the elements that
     * match that searchText to the new dataset.
     * Matches are distinct so if they're found in one then it's guaranteed to not be found in the others.
     * Sets the adapter to the combined ArrayList named "dataset".
     * @caution if search is empty sets the adapter to the complete dataset that comes with the Intent.
     * @caution works on background thread
     */
    @AnyThread
    private void getNewRecipesAndUpdateDataset() {
        new Handler(Looper.getMainLooper()).post(() -> {
            String dirtyInput = searchAutoComplete.getText().toString();
            String searchable = !dirtyInput.isEmpty()
                    ? dirtyInput.substring(0, 1).toUpperCase(Locale.getDefault()) + dirtyInput.substring(1)
                    : "";

            ArrayList<CocktailRecipe> dataset;
            if (searchable.isEmpty())
                dataset = new ArrayList<>(recipes);
            else if (DRINKS.contains(searchable))
                dataset = recipes.stream()
                        .filter(r -> r.get_drink().equals(searchable))
                        .collect(Collectors.toCollection(ArrayList::new));
            else if (NAMES.contains(searchable))
                dataset = recipes.stream()
                        .filter(r -> r.get_title().equals(searchable))
                        .collect(Collectors.toCollection(ArrayList::new));
            else if (PARTIAL_NAMES.contains(searchable))
                dataset = recipes.stream()
                        .filter(r -> Arrays.asList(r.get_title().split(" ")).contains(searchable))
                        .collect(Collectors.toCollection(ArrayList::new));
            else
                return;
            //Only runs if dataset changed
            dataset.sort(Comparator.comparing((CocktailRecipe::get_title)));
            recyclerView.swapAdapter(new GeneralAdapter(this, dataset), false);
        });
    }

    /**
     * Initializes the dataset for the Activity.
     * Builds the separate ArrayLists for the possible searches, main ingredient(DRINK), names and
     * partial names (such as 'Classic' || 'Daiquiri' in 'Classic Daiquiri')
     * Combines the ArrayLists to one and sets that as the adapter for the AutoComplete TextView
     */
    @AnyThread
    @SuppressWarnings("unchecked")
    private void initAutoCompleteDataset(){
        recipes = (ArrayList<CocktailRecipe>) getIntent().getSerializableExtra("recipes");
        new Handler(Looper.getMainLooper()).post(() -> {
            DRINKS = recipes.stream()
                    .map(CocktailRecipe::get_drink)
                    .distinct().collect(Collectors.toCollection(ArrayList::new));

            NAMES = recipes.stream()
                    .map(CocktailRecipe::get_title)
                    .distinct().collect(Collectors.toCollection(ArrayList::new));

            PARTIAL_NAMES = NAMES.stream()
                    .flatMap(fullDrinkName -> Arrays.stream(fullDrinkName.split(" ").clone()))
                    .distinct().collect(Collectors.toCollection(ArrayList::new));

            final ArrayList<String> completeStringDataset = new ArrayList<>(DRINKS);
            completeStringDataset.addAll(PARTIAL_NAMES);
            completeStringDataset.addAll(NAMES);

            searchAutoComplete.setAdapter(new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line, completeStringDataset));
        });
    }

    /**
     * Clears the focus so SoftKeyboard doesn't open up again after user has clicked on a searched
     * recipe. Assumes that they may want to press on another one in the same search.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (!searchAutoComplete.getText().toString().isEmpty())
            searchAutoComplete.clearFocus();
    }
}