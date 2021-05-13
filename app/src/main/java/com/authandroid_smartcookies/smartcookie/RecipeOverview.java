package com.authandroid_smartcookies.smartcookie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecipeOverview extends AppCompatActivity {

    Button ContinueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipeoverview);
        ContinueButton = findViewById(R.id.Continuetorecipebutton);

        ContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RecipeOverview.this , Steps.class);
                startActivity(intent);
            }
        });
    }
}
