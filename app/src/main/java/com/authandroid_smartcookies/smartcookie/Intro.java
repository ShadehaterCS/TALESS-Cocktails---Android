package com.authandroid_smartcookies.smartcookie;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.Main.Activities.HomeActivity;

public class Intro extends AppCompatActivity {

    ViewPager mSLideViewPager;
    LinearLayout laDots;
    Button nextButton, skipButton;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        nextButton = findViewById(R.id.nextbtn);
        skipButton = findViewById(R.id.skipButton);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        nextButton.setOnClickListener(v -> {

            if (getitem(0) < 2)
                mSLideViewPager.setCurrentItem(getitem(1),true);
            else {
                Intent i = new Intent(Intro.this,HomeActivity.class);
                startActivity(i);
                finish();

            }

        });

        skipButton.setOnClickListener(v -> {


            Intent i = new Intent(Intro.this, HomeActivity.class);
            startActivity(i);
            finish();

        });

        mSLideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        laDots = (LinearLayout) findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);

        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);

    }

    public void setUpindicator(int position){

        dots = new TextView[3];
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
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i){

        return mSLideViewPager.getCurrentItem() + i;
    }

}