package com.auth.TALESS.Main.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.AnyThread;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.auth.TALESS.DataClasses.CocktailRecipe;
import com.auth.TALESS.Database.SenpaiDB;
import com.auth.TALESS.Main.Adapters.HomeAdapter;
import com.auth.TALESS.Main.Activities.SearchActivity;
import com.auth.TALESS.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Implements the Home Fragment View
 * @link SenpaiDB when opening and dataset for the HomeAdapter is empty.
 */
public class HomeFragment extends Fragment {
    private final String TAG = "MAIN_MENU_FRAGMENT";
    protected RecyclerView recyclerView;
    protected SenpaiDB db;

    protected HomeAdapter adapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected static ArrayList<CocktailRecipe> dataset;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = SenpaiDB.getInstance(this.requireContext());
        fetchInitialDatasetInBackgroundAndSetUpAdapter();
    }


    /**
     * Method opens the initial connection to the database and does the biggest query the app is
     * going to encounter, thus this is a slow operation.
     * If openDatabase() fails that means that a database object does not exist so it calls
     * createDatabase(). If that doesn't throw an exception then openDatabase() is guaranteed to
     * succeed.
     * Shuffles the dataset so it's different everyTime the Activity is started.
     */
    @AnyThread
    public void fetchInitialDatasetInBackgroundAndSetUpAdapter() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            //If database doesn't exist, create it and open the connection
            if (!db.openDatabase()) {
                db.createDatabase(requireContext().getApplicationContext());
                db.openDatabase();
            }
            if (dataset == null) {
                dataset = db.getAllRecipes();
                Collections.shuffle(dataset);
            }
            adapter = new HomeAdapter(requireContext(), dataset);
            adapter.setFavorites(db.getFavoritesIds());
            //Run on UI thread
            handler.post(() -> recyclerView.swapAdapter(adapter,false));
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        root.setTag(TAG);
        recyclerView = root.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        ImageButton search = root.findViewById(R.id.searchButton);
        search.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SearchActivity.class);
            intent.putExtra("recipes", dataset);
            v.getContext().startActivity(intent);
        });

        ConstraintLayout titleBarLayout = root.findViewById(R.id.mainMenuTitlesConLayout);
        final int threshold = 20;
        //Used to show or hide the top 'app bar' containing the logo and the SearchButton
        recyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int dy = scrollY - oldScrollY;
            //Positive scroll, show layout
            if (dy < -threshold && titleBarLayout.getVisibility() == View.GONE)
                titleBarLayout.setVisibility(View.VISIBLE);
            //Negative scroll, hide layout
            else if (dy > threshold && titleBarLayout.getVisibility() == View.VISIBLE)
                titleBarLayout.setVisibility(View.GONE);
        });

        //placeholder adapter while data is loading
        recyclerView.setAdapter(new HomeAdapter(requireContext(), new ArrayList<>()));
        return root;
    }
    //Used by parent activity when Home button is reselected inside the BottomNavigationView
    public void scrollToTop() {
        recyclerView.smoothScrollToPosition(0);
    }
}