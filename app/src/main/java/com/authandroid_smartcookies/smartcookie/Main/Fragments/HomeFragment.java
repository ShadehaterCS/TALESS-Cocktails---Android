package com.authandroid_smartcookies.smartcookie.Main.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.Main.Adapters.HomeAdapter;
import com.authandroid_smartcookies.smartcookie.Main.Activities.SearchActivity;
import com.authandroid_smartcookies.smartcookie.R;

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
        //If database doesn't exist, create it and always open the connection
        db = SenpaiDB.getInstance(this.requireContext());
        fetchInitialDatasetInBackgroundAndSetUpAdapter();
    }

    public void fetchInitialDatasetInBackgroundAndSetUpAdapter() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
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

        ConstraintLayout layout = root.findViewById(R.id.mainMenuTitlesConLayout);
        final int threshold = 20;
        //Used to show or hide the top 'app bar' containing the logo and the SearchButton
        Animation fadeOut_Up = AnimationUtils.loadAnimation(requireContext(), R.anim.fast_fade_out_and_up);
        Animation fadeIn_Down = AnimationUtils.loadAnimation(requireContext(), R.anim.fast_fade_in_and_down);
        recyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int dy = scrollY - oldScrollY;
            //Positive scroll, show layout
            if (dy < -threshold && layout.getVisibility() == View.GONE) {
                layout.clearAnimation();
                layout.startAnimation(fadeIn_Down);
                layout.setVisibility(View.VISIBLE);
            }
            //Negative scroll, hide layout
            else if (dy > threshold && layout.getVisibility() == View.VISIBLE) {
                layout.clearAnimation();
                layout.startAnimation(fadeOut_Up);
                layout.setVisibility(View.GONE);
            }
        });

        //placeholder adapter while data is loading
        recyclerView.setAdapter(new HomeAdapter(requireContext(), new ArrayList<>()));
        return root;
    }

    //Used by parent activity when Home button is reselected inside the BottomNav
    public void scrollToTop() {
        recyclerView.smoothScrollToPosition(0);
    }
}