package com.authandroid_smartcookies.smartcookie;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;



public class StepWithTimer extends AppCompatActivity {
    private TextView countdownText;
    private Button   countdownButton;

    private CountDownTimer countDownTimer ;
    private long timeLeftInMilliseconds = 12000; //30sec
    private boolean timeRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdownText = findViewById(R.id.countdown_text);
        countdownButton = findViewById(R.id.countdown_button);

        countdownButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                startStop();
            }
        });
        updateTimer();
    }


    private void startStop() {
        if (timeRunning){
            stopTimer();
        }
        else{
            startTimer();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l ;
                updateTimer();
            }

            @Override
            public void onFinish() {


            }
        }.start();
        countdownButton.setText("PAUSE");
        timeRunning = true;
    }


    private void stopTimer() {
        countDownTimer.cancel();
        countdownButton.setText("PAUSE");
        timeRunning = false;
    }


    private void updateTimer() {
        int seconds = (int) timeLeftInMilliseconds/1000;

        String timeLeftText;
        timeLeftText = ""+seconds;
        if(seconds < 10) timeLeftText ="0"+timeLeftText;
        countdownText.setText(timeLeftText);

    }

}