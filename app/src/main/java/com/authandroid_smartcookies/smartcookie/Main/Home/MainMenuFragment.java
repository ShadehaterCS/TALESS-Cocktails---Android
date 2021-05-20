package com.authandroid_smartcookies.smartcookie.Main.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.Main.SearchActivity;
import com.authandroid_smartcookies.smartcookie.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainMenuFragment extends Fragment {
    private final String TAG = "MAIN_MENU_FRAGMENT";
    protected RecyclerView recyclerView;
    protected SenpaiDB db;

    protected HomeRecipeAdapter adapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<CocktailRecipe> dataset;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If database doesn't exist, create it and always open the connection
        db = SenpaiDB.getInstance(this.requireContext());
        fetchInitialDatasetInBackground();
    }

    public void fetchInitialDatasetInBackground(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            if (!db.openDatabase()) {
                db.createDatabase(requireContext().getApplicationContext());
                db.openDatabase();
            }
            dataset = db.getAllRecipes();
            adapter = new HomeRecipeAdapter(requireContext(), dataset);
            adapter.setFavorites(db.getFavoritesIds());
            //Run on UI thread
            handler.post(() -> {
                recyclerView.setAdapter(adapter);
            });
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main_menu, container, false);
        root.setTag(TAG);
        recyclerView=root.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        ImageButton search = root.findViewById(R.id.searchButton);
        search.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SearchActivity.class);
            intent.putExtra("recipes", dataset);
            v.getContext().startActivity(intent);
        });

        //placeholder adapter while data is loading
        recyclerView.setAdapter(new HomeRecipeAdapter(requireContext(),new ArrayList<>()));
        return root;
    }

    //Used by parent activity when Home button is reselected inside the BottomNav
    public void scrollToTop(){
        recyclerView.smoothScrollToPosition(0);
    }
}