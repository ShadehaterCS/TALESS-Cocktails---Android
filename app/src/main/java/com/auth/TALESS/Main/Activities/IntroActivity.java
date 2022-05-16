package com.auth.TALESS.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auth.TALESS.R;
import com.auth.TALESS.Main.Adapters.IntroViewPagerAdapter;
import com.auth.TALESS.Util.Utilities;

/**
 * This is the intro activity , the first time that the user will open the app
 * he will be shown a brief description of what he can do
 */
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
        setContentView(R.layout.activity_intro);
        nextButton = findViewById(R.id.nextButton);
        skipButton = findViewById(R.id.skipButton);
        startButton = findViewById(R.id.startButton);

        startButton.setVisibility(View.INVISIBLE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        /*
         * Buttons functions
         */
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
    /**
     * Creating la dots
     */
    public void setUpIndicator(int position){
        dots = new TextView[4];
        laDots.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226",0));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.grey,getApplicationContext().getTheme()));
            laDots.addView(dots[i]);

        }
        dots[position].setTextColor(getResources().getColor(R.color.pastelRed,getApplicationContext().getTheme()));
    }
    /**
     * Setting up visibility of buttons and animations
     */
    private void lastScreen(){
        Animation fadeInUp = AnimationUtils.loadAnimation(this, R.anim.fast_fade_in_and_up);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fast_fade_out);
        startButton.startAnimation(fadeInUp);
        startButton.setVisibility(View.VISIBLE);
        skipButton.startAnimation(fadeOut);
        skipButton.setVisibility(View.INVISIBLE);

        laDots.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.INVISIBLE);
    }
    /**
     * Setting up visibility of buttons and animations
     */
    private void exitedLastScreen(){
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fast_fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fast_fade_out);
        if (startButton.getVisibility() == View.VISIBLE) {
            startButton.startAnimation(fadeOut);
            startButton.setVisibility(View.INVISIBLE);
            laDots.startAnimation(fadeIn);
        }
        skipButton.startAnimation(fadeIn);
        skipButton.setVisibility(View.VISIBLE);

        nextButton.setVisibility(View.VISIBLE);
        laDots.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

        @Override
        public void onPageSelected(int position) {
            if (position == 3){
                lastScreen();
            }
            else if (position == 2){
                exitedLastScreen();
            }
            setUpIndicator(position);
        }
        @Override
        public void onPageScrollStateChanged(int state) {}
    };
    /**
     * Get the activity that is active
     */
    private int getItem(int i){
        return slideViewPager.getCurrentItem() + i;
    }

}