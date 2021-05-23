package com.authandroid_smartcookies.smartcookie.Main.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.R;
import com.authandroid_smartcookies.smartcookie.Main.Adapters.IntroViewPagerAdapter;
import com.authandroid_smartcookies.smartcookie.Util.Utilities;

public class IntroActivity extends AppCompatActivity {

    ViewPager slideViewPager;
    LinearLayout laDots;
    Button skipButton, startButton;
    ImageButton nextButton;

    TextView[] dots;
    IntroViewPagerAdapter introViewPagerAdapter;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utilities.savePrefsData(this.getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        nextButton = findViewById(R.id.nextButton);
        skipButton = findViewById(R.id.skipButton);
        startButton = findViewById(R.id.startButton);

        startButton.setVisibility(View.INVISIBLE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        nextButton.setOnClickListener(v -> slideViewPager.setCurrentItem(getItem(1),true));

        skipButton.setOnClickListener(v -> slideViewPager.setCurrentItem(3,true));

        startButton.setOnClickListener(v -> {
            Intent i = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(i);
            finish();

        });

        slideViewPager = findViewById(R.id.slideViewPager);
        laDots = findViewById(R.id.indicator_layout);

        introViewPagerAdapter = new IntroViewPagerAdapter(this);

        slideViewPager.setAdapter(introViewPagerAdapter);

        setUpIndicator(0);
        slideViewPager.addOnPageChangeListener(viewListener);

    }

    public void setUpIndicator(int position){
        dots = new TextView[4];
        laDots.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.grey,getApplicationContext().getTheme()));
            laDots.addView(dots[i]);

        }
        dots[position].setTextColor(getResources().getColor(R.color.pastelRed,getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override
        public void onPageSelected(int position) {
            if (getItem(0) < 3){
                nextButton.setVisibility(View.VISIBLE);
                laDots.setVisibility(View.VISIBLE);
                skipButton.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.INVISIBLE);
             }
            else {
                nextButton.setVisibility(View.INVISIBLE);
                laDots.setVisibility(View.INVISIBLE);
                skipButton.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.VISIBLE);
            }




            setUpIndicator(position);
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private int getItem(int i){
        return slideViewPager.getCurrentItem() + i;
    }

}