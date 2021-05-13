package com.authandroid_smartcookies.smartcookie.Menu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.DataClasses.CocktailRecipe;
import com.authandroid_smartcookies.smartcookie.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.database.sqlite.SQLiteOpenHelper;

public class MainMenuFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recyclerView=requireActivity().findViewById(R.id.recyclerview);
        add_button=requireActivity().findViewById(R.id.add_button);



        return inflater.inflate(R.layout.fragment_main_menu, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tv = requireActivity().findViewById(R.id.textView4);
        dbhandler = new DBHandler(this.getContext());
        tv.setOnClickListener(v -> {
            CocktailRecipe g = dbhandler.getRecipePlease();
            if (g != null)
                tv.setText(g.get_title());
        });
    }



}