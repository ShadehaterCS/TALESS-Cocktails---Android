package com.authandroid_smartcookies.smartcookie.Menu;
import java.lang.Math;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.R;


public class ConvertersFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view  = inflater.inflate(R.layout.fragment_converters, container, false);

        EditText amounts = view.findViewById(R.id.input);
        Spinner from_sp = view.findViewById(R.id.spinner_from);
        Spinner to_sp   = view.findViewById(R.id.spinner_to);
        Button convert = view.findViewById(R.id.convert);
        Button clear   = view.findViewById(R.id.clear);
        TextView result  = view.findViewById(R.id.result);

        String[] from = {"gr","ml","oz","teaspoons","tablespoons"};

        ArrayAdapter first = new ArrayAdapter<String>(this.getContext(),R.layout.support_simple_spinner_dropdown_item,from);
        from_sp.setAdapter(first);

        String[] to   = {"gr","ml","oz","teaspoons","tablespoons"};
        ArrayAdapter  second = new ArrayAdapter<String>(this.getContext(),R.layout.support_simple_spinner_dropdown_item,to);
        to_sp.setAdapter(second);

        convert.setOnClickListener(v -> {
            String test = amounts.getText().toString();

            if (TextUtils.isEmpty(test)) {
                result.setText(("Enter your amount first"));

            } else {
                Double amount = Double.parseDouble(amounts.getText().toString());
                //    gr
                if (from_sp.getSelectedItem().toString().equals("gr") && to_sp.getSelectedItem().toString().equals("gr")) {
                    result.setText(("This is equivalent to : " + amount + " gr"));
                } else if (from_sp.getSelectedItem().toString().equals("gr") && to_sp.getSelectedItem().toString().equals("ml")) {
                    result.setText(("This is equivalent to : " + amount + " ml"));
                } else if (from_sp.getSelectedItem().toString().equals("gr") && to_sp.getSelectedItem().toString().equals("oz")) {
                    amount = amount * 0.0352739619;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " oz"));
                } else if (from_sp.getSelectedItem().toString().equals("gr") && to_sp.getSelectedItem().toString().equals("teaspoons")) {
                    amount = amount * 0.24;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " teaspoons"));
                } else if (from_sp.getSelectedItem().toString().equals("gr") && to_sp.getSelectedItem().toString().equals("tablespoons")) {
                    amount = amount * 0.0666666666666667;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " teaspoons"));
                }
                //  ml

                else if (from_sp.getSelectedItem().toString().equals("ml") && to_sp.getSelectedItem().toString().equals("gr")) {
                    result.setText(("This is equivalent to : " + amount + " gr"));
                } else if (from_sp.getSelectedItem().toString().equals("ml") && to_sp.getSelectedItem().toString().equals("ml")) {
                    result.setText(("This is equivalent to : " + amount + " ml"));
                } else if (from_sp.getSelectedItem().toString().equals("ml") && to_sp.getSelectedItem().toString().equals("oz")) {
                    amount = amount * 0.0338140227;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " oz"));
                } else if (from_sp.getSelectedItem().toString().equals("ml") && to_sp.getSelectedItem().toString().equals("teaspoons")) {
                    amount = amount * 0.202884136;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " teaspoons"));
                } else if (from_sp.getSelectedItem().toString().equals("ml") && to_sp.getSelectedItem().toString().equals("tablespoons")) {
                    amount = amount * 0.0676280454;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " tablespoons"));
                }
                //  oz
                else if (from_sp.getSelectedItem().toString().equals("oz") && to_sp.getSelectedItem().toString().equals("gr")) {
                    amount = amount * 28.3495231;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " gr"));
                } else if (from_sp.getSelectedItem().toString().equals("oz") && to_sp.getSelectedItem().toString().equals("ml")) {
                    amount = amount * 29.5735296;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " ml"));
                } else if (from_sp.getSelectedItem().toString().equals("oz") && to_sp.getSelectedItem().toString().equals("oz")) {
                    result.setText(("This is equivalent to : " + amount + " oz"));
                } else if (from_sp.getSelectedItem().toString().equals("oz") && to_sp.getSelectedItem().toString().equals("teaspoons")) {
                    amount = amount * 6;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " teaspoons"));
                } else if (from_sp.getSelectedItem().toString().equals("oz") && to_sp.getSelectedItem().toString().equals("tablespoons")) {
                    amount = amount * 2;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " tablespoons"));
                }
                //  teaspoons
                else if (from_sp.getSelectedItem().toString().equals("teaspoons") && to_sp.getSelectedItem().toString().equals("gr")) {
                    amount = amount * 28.3495231;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " gr"));
                } else if (from_sp.getSelectedItem().toString().equals("teaspoons") && to_sp.getSelectedItem().toString().equals("ml")) {
                    amount = amount * 29.5735296;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " ml"));
                } else if (from_sp.getSelectedItem().toString().equals("teaspoons") && to_sp.getSelectedItem().toString().equals("oz")) {
                    amount = amount * 6;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " oz"));
                } else if (from_sp.getSelectedItem().toString().equals("teaspoons") && to_sp.getSelectedItem().toString().equals("teaspoons")) {
                    result.setText(("This is equivalent to : " + amount + " teaspoons"));
                } else if (from_sp.getSelectedItem().toString().equals("teaspoons") && to_sp.getSelectedItem().toString().equals("tablespoons")) {
                    amount = amount * 2;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " tablespoons"));
                }
                //  tablespoons
                else if (from_sp.getSelectedItem().toString().equals("teaspoons") && to_sp.getSelectedItem().toString().equals("gr")) {
                    amount = amount * 12.781700527272;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " gr"));
                } else if (from_sp.getSelectedItem().toString().equals("teaspoons") && to_sp.getSelectedItem().toString().equals("ml")) {
                    amount = amount * 14.7867648;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " ml"));
                } else if (from_sp.getSelectedItem().toString().equals("teaspoons") && to_sp.getSelectedItem().toString().equals("oz")) {
                    amount = amount * 0.5;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " oz"));
                } else if (from_sp.getSelectedItem().toString().equals("teaspoons") && to_sp.getSelectedItem().toString().equals("teaspoons")) {
                    amount = amount * 3;
                    result.setText(("This is equivalent to : " + Math.round(amount * 100.0) / 100.0 + " teaspoons"));

                } else if (from_sp.getSelectedItem().toString().equals("teaspoons") && to_sp.getSelectedItem().toString().equals("tablespoons")) {
                    result.setText(("This is equivalent to : " + amount + " tablespoons"));
                } else {
                    result.setText(("ERROR - No such a thing"));
                }

            }

        });

        clear.setOnClickListener(v -> {
            amounts.setText("");
            result.setText (("Cleared"));
            from_sp.setSelection(0);
            to_sp.setSelection(0);
        });

        return view;
    }
}