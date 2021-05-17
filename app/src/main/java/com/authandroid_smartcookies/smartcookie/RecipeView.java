package com.authandroid_smartcookies.smartcookie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RecipeView extends AppCompatActivity {

    Button ContinueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipeoverview);
        ContinueButton = findViewById(R.id.Continuetorecipebutton);

        ContinueButton.setOnClickListener(v -> {
            Intent intent= new Intent(RecipeView.this , Steps.class);
            startActivity(intent);
        });
    }


}
