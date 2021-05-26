package com.auth.TALESS.Main.Fragments;

import java.lang.Math;
import java.util.HashMap;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.auth.TALESS.Util.GenericTextWatcher;
import com.auth.TALESS.R;

/**
 * Implements the Converters Fragment View
 * Uses multiple hashmaps to map each value to another
 * Uses a HashMap to connect the individual Maps
 */
public class ConvertersFragment extends Fragment {
    EditText amountsText;
    Button clearButton;
    TextView resultTV;
    HashMap<String, HashMap<String,Double>> maps;
    //Used in @function Convert()
    String fromType;
    String toType;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_converters, container, false);

        amountsText = view.findViewById(R.id.input);
        clearButton = view.findViewById(R.id.clear);
        resultTV = view.findViewById(R.id.resultTextView);

        //updates text on every button press
        amountsText.addTextChangedListener(new GenericTextWatcher(this::convert));

        /*Hashmaps, it's a lot but oh well ♥
        One hashmap for every correlation between types
        One hashmap to access the hashmaps using the from type*/
        HashMap<String, Double> grams = new HashMap<String, Double>() {{
            put("gr", 1.0);
            put("ml", 1.0);
            put("oz", 0.03527);
            put("tsp", 0.24);
            put("tbsp", 0.06666);
        }};
        HashMap<String, Double> mls = new HashMap<String, Double>() {{
            put("gr", 1.0);
            put("ml", 1.0);
            put("oz", 0.03381);
            put("tsp", 0.20288);
            put("tbsp", 0.06762);
        }};
        HashMap<String, Double> ounces = new HashMap<String, Double>() {{
            put("gr", 28.3495231);
            put("ml", 29.5735296);
            put("oz", 1.0);
            put("tsp", 6.0);
            put("tbsp", 2.0);
        }};
        HashMap<String, Double> teaspoons = new HashMap<String, Double>() {{
            put("gr", 4.928921594);
            put("ml", 4.92892);
            put("oz", 0.166667);
            put("tsp", 1.0);
            put("tbsp", 0.333333);
        }};
        HashMap<String, Double> tablespoons = new HashMap<String, Double>() {{
            put("gr", 14.7867648);
            put("ml", 14.7868);
            put("oz", 0.5);
            put("tsp", 3.0);
            put("tbsp", 1.0);
        }};
        maps = new HashMap<String, HashMap<String,Double>>(){{
            put ("gr", grams);
            put ("ml", mls);
            put ("oz", ounces);
            put("tsp", teaspoons);
            put("tbsp", tablespoons);
        }};

        clearButton.setOnClickListener(v -> {
            amountsText.setText("");
            resultTV.setText(("Cleared"));
        });

        RadioGroup fromRB = view.findViewById(R.id.radioGroupFrom);
        RadioGroup toRB = view.findViewById(R.id.radioGroupTo);
        fromRB.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton button = group.findViewById(checkedId);
            fromType = button.getText().toString();
            convert();
        });
        toRB.setOnCheckedChangeListener((group, checkedId) ->{
            RadioButton button = group.findViewById(checkedId);
            toType = button.getText().toString();
            convert();
        });

        fromRB.check(R.id.frommlRB);
        toRB.check(R.id.toozRB);
        return view;
    }

    /**
     * To be called constantly for reactive typing
     * Converts the @string from input(EditText) to a number based on checked RadioButtons
    */
    double amount = 0;
    public void convert(){
        String input = amountsText.getText().toString();
        if (!input.isEmpty() && fromType != null && toType != null) {
            amount = sanitizeInput(input);
            //get the map with with fromType, get the conversion double with toType,
            amount *= maps.get(fromType).get(toType);
            //round number and set it as the TextView
            String text = Math.round(amount * 100.0) / 100.0 + " " +toType;
            resultTV.setText(text);
        }
    }
    /**
     * @param input Since this can be all sorts of things, from numbers "4, 5", to fractions "1/2"
     *              or real numbers (1.42, .34) it needs to be sanitized
     * @exception NumberFormatException
     * example: a real number ".34" is backspaced and . is left alone, exception at parsing
    */
    private double sanitizeInput(String input){
        if (input.contains("/")){
            String[] parts = input.split("/");
            if (parts.length != 2)
                return 0;
            double dividend = Double.parseDouble(parts[0]);
            double divisor = Double.parseDouble(parts[1]);
            amount = dividend / divisor;
        }
        else if (input.equals("."))
            amount = 0;
        else
            amount = Double.parseDouble(input);
        return amount;
    }
}