package com.authandroid_smartcookies.smartcookie.Main.Activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.authandroid_smartcookies.smartcookie.R;
import com.authandroid_smartcookies.smartcookie.Main.Adapters.IntroViewPagerAdapter;
import com.authandroid_smartcookies.smartcookie.Util.Utilities;

public class IntroActivity extends AppCompatActivity {

    ViewPager mSLideViewPager;
    LinearLayout laDots;
    Button nextButton, skipButton;

    TextView[] dots;
    IntroViewPagerAdapter introViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utilities.savePrefsData(this.getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        nextButton = findViewById(R.id.nextbtn);
        skipButton = findViewById(R.id.skipButton);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        nextButton.setOnClickListener(v -> {
            if (getItem(0) < 4)
                mSLideViewPager.setCurrentItem(getItem(1),true);
            else {
                Intent i = new Intent(IntroActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        skipButton.setOnClickListener(v -> {


            Intent i = new Intent(IntroActivity.this, HomeActivity.class);
            startActivity(i);
            finish();

        });

        mSLideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        laDots = (LinearLayout) findViewById(R.id.indicator_layout);

        introViewPagerAdapter = new IntroViewPagerAdapter(this);

        mSLideViewPager.setAdapter(introViewPagerAdapter);

        setUpIndicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);

    }

    public void setUpIndicator(int position){
        dots = new TextView[5];
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
            setUpIndicator(position);
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
    private int getItem(int i){
        return mSLideViewPager.getCurrentItem() + i;
    }

}