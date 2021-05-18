package com.authandroid_smartcookies.smartcookie.Menu.Home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.HomeActivity;
import com.authandroid_smartcookies.smartcookie.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

public class MainMenuFragment extends Fragment {
    private final String TAG = "MAIN_MENU_FRAGMENT";
    protected RecyclerView recyclerView;
    protected SenpaiDB db;
    protected Random random = new Random();

    protected RecipeAdapter adapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<CocktailRecipe> dataset;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If database doesn't exist, create it and always open the connection
        db = SenpaiDB.getInstance(this.requireContext());
        new async(this).execute();
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
        recyclerView.setAdapter(new RecipeAdapter(requireContext(),new ArrayList<>()));
        return root;
    }

    private void setAdapter(ArrayList<CocktailRecipe> recipes){
        assert recyclerView != null;
        dataset = recipes;
        adapter = new RecipeAdapter(requireContext(), dataset);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private class async extends AsyncTask <Void, Void, Void>{
        private final WeakReference<MainMenuFragment> fragment;
        private ArrayList<CocktailRecipe> recipes;

        public async(MainMenuFragment fragment){
            this.fragment = new WeakReference<>(fragment);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (!db.openDatabase()) {
                db.createDatabase(requireContext().getApplicationContext());
                db.openDatabase();
            }
            recipes = db.getAllRecipes();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            fragment.get().setAdapter(recipes);
        }
    }

    //Used by parent activity when Home button is reselected inside the BottomNav
    public void scrollToTop(){
        recyclerView.smoothScrollToPosition(0);
    }
}