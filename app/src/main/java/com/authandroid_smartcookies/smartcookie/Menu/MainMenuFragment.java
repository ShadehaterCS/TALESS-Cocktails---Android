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
import com.authandroid_smartcookies.smartcookie.Database.DBHandler;
import com.authandroid_smartcookies.smartcookie.R;

public class MainMenuFragment extends Fragment {

    DBHandler dbhandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tv = requireActivity().findViewById(R.id.addrecipebutton);
        dbhandler = new DBHandler(this.getContext());
        tv.setOnClickListener(v -> {
            CocktailRecipe g = dbhandler.getRecipePlease();
            if (g != null)
                tv.setText(g.get_title());
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dbhandler = new DBHandler(this.getContext(), null, null, 1);
    }

}