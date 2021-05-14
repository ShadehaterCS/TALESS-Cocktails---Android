package com.authandroid_smartcookies.smartcookie.Menu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.Database.DBHandler;
import com.authandroid_smartcookies.smartcookie.Database.SenpaiDB;
import com.authandroid_smartcookies.smartcookie.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainMenuFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    SenpaiDB db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recyclerView=requireActivity().findViewById(R.id.recyclerview);


        db = new SenpaiDB(this.getContext());
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tv = requireActivity().findViewById(R.id.textView4);
        db.getReadableDatabase();
        tv.setOnClickListener(v -> {
            CocktailRecipe g = db.getRecipePlease();
            if (g != null)
                tv.setText(g.get_title());
        });
    }

}