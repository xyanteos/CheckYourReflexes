package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final    long    startTimeInMilis=3000;
    private TextView countDownTextView;
    private Button buttonStartPause, buttonSendOnServer, buttonReset;
    private CountDownTimer cdt;
    private boolean timeRunning;
    private long timeLeftInMilis = startTimeInMilis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countDownTextView = findViewById(R.id.timer);
        buttonStartPause = findViewById(R.id.buttonStart_Pause);
        buttonSendOnServer = findViewById(R.id.buttonWyslanieNaServer);
        buttonReset = findViewById(R.id.reset);




        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeLeftInMilis = 3000;
                int dziesiateMilisekundy = (int) (timeLeftInMilis/100);
                int sekundy = (int) (timeLeftInMilis/1000)%60;
                String timeleftFormatted =  String.format(Locale.getDefault(),"%02d:%02d", sekundy,dziesiateMilisekundy);
                countDownTextView.setText(timeleftFormatted);
            }
        });

        buttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeRunning) {
                    pauseTimer();
                }
                else   {
                    startTimer();
                }
            }
        });
        buttonSendOnServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendOnServer();
            }
        });



    }

    private void startTimer() {
        buttonSendOnServer.setVisibility(View.INVISIBLE);
        cdt = new CountDownTimer(timeLeftInMilis,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilis=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeRunning = false;
                buttonStartPause.setText("Start");
            }
        }.start();
        timeRunning = true;
        buttonStartPause.setText("pause");

    }

    private void updateCountDownText() {
        int dziesiateMilisekundy = (int) (timeLeftInMilis/100);
        int sekundy = (int) (timeLeftInMilis/1000)%60;
        String timeleftFormatted =  String.format(Locale.getDefault(),"%02d:%02d", sekundy,dziesiateMilisekundy);
        countDownTextView.setText(timeleftFormatted);
    }

    private void pauseTimer() {
        timeRunning = false;
        buttonStartPause.setText("start");
        cdt.cancel();
        buttonSendOnServer.setVisibility(View.VISIBLE);
    }
}
