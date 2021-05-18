package com.authandroid_smartcookies.smartcookie.Menu;

import java.lang.Math;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.R;
import com.google.android.material.internal.TextWatcherAdapter;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class ConvertersFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_converters, container, false);

        EditText amountsText = view.findViewById(R.id.input);
        Spinner from_sp = view.findViewById(R.id.spinner_from);
        Spinner to_sp = view.findViewById(R.id.spinner_to);
        Button convertButton = view.findViewById(R.id.convert);
        Button clearButton = view.findViewById(R.id.clear);
        TextView resultTV = view.findViewById(R.id.result);

        //didn't work as it should on phone for some reason, explicit call here
        amountsText.setInputType(InputType.TYPE_CLASS_PHONE);
        //updates text on every button press
        amountsText.addTextChangedListener(new customTextWatcher(convertButton));

        String[] from = {"gr", "ml", "oz", "teaspoons", "tablespoons"};

        ArrayAdapter<String> first = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, from);
        from_sp.setAdapter(first);

        String[] to = {"gr", "ml", "oz", "teaspoons", "tablespoons"};
        ArrayAdapter<String> second = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, to);
        to_sp.setAdapter(second);

        //Hashmaps, it's a lot but oh well ♥
        HashMap<String, Double> grams = new HashMap<String, Double>() {{
            put("gr", 1.0);
            put("ml", 1.0);
            put("oz", 0.03527);
            put("teaspoons", 0.24);
            put("tablespoons", 0.06666);
        }};
        HashMap<String, Double> mls = new HashMap<String, Double>() {{
            put("gr", 1.0);
            put("ml", 1.0);
            put("oz", 0.03381);
            put("teaspoons", 0.20288);
            put("tablespoons", 0.06762);
        }};
        HashMap<String, Double> ounces = new HashMap<String, Double>() {{
            put("gr", 28.3495231);
            put("ml", 29.5735296);
            put("oz", 1.0);
            put("teaspoons", 6.0);
            put("tablespoons", 2.0);
        }};
        HashMap<String, Double> teaspoons = new HashMap<String, Double>() {{
            put("gr", 4.928921594);
            put("ml", 4.92892);
            put("oz", 0.166667);
            put("teaspoons", 1.0);
            put("tablespoons", 0.333333);
        }};
        HashMap<String, Double> tablespoons = new HashMap<String, Double>() {{
            put("gr", 14.7867648);
            put("ml", 14.7868);
            put("oz", 0.5);
            put("teaspoons", 3.0);
            put("tablespoons", 1.0);
        }};
        HashMap<String, HashMap<String,Double>> maps = new HashMap<String, HashMap<String,Double>>(){{
            put ("gr", grams);
            put ("ml", mls);
            put ("oz", ounces);
            put("teaspoons", teaspoons);
            put("tablespoons", tablespoons);
        }};

        convertButton.setOnClickListener(v -> {
            String input = amountsText.getText().toString();
            if (!input.isEmpty()) {
                String fromType = from_sp.getSelectedItem().toString();
                String toType = to_sp.getSelectedItem().toString();
                String result = "";
                double amount = Double.parseDouble(amountsText.getText().toString());
                //get the map with with fromType, get the conversion double with toType,
                amount *= maps.get(fromType).get(toType);
                //round number and set it as the TextView
                resultTV.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + toType));
            }
        });

        clearButton.setOnClickListener(v -> {
            amountsText.setText("");
            resultTV.setText(("Cleared"));
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Open keyboard and focus the edit text element

        EditText amounts = requireActivity().findViewById(R.id.input);
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        InputMethodManager methodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        amounts.requestFocus();
    }

    private class customTextWatcher implements TextWatcher {
        private final Button convertButton;
        public customTextWatcher(Button button) {
            convertButton = button;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            convertButton.performClick();
        }
    }
}