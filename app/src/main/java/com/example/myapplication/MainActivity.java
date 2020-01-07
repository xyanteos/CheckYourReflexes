package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final    long    startTimeInMilis=3000,timeToNotify = 8000000;
    private TextView countDownTextView, scoreTextView, triesTextView;
    private Button buttonStartPause, buttonSendOnServer, buttonReset;
    public int tries = 0;
    public double score=0;
    private CountDownTimer cdt, notificationCountDownTimer;
    private boolean timeRunning;
    private long timeLeftInMilis = startTimeInMilis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        notificationCountDownTimer = new CountDownTimer(timeToNotify,10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilis = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"Get as close to 1 s as You can!",Toast.LENGTH_LONG).show();
            }
        }.start();*/
        Toast.makeText(getApplicationContext(),"Get as close to 1 s as You can!",Toast.LENGTH_LONG).show();
        countDownTextView = findViewById(R.id.timer);
        buttonStartPause = findViewById(R.id.buttonStart_Pause);
        buttonSendOnServer = findViewById(R.id.buttonWyslanieNaServer);
        buttonReset = findViewById(R.id.reset);
        scoreTextView = findViewById(R.id.textScoreValue);
        triesTextView = findViewById(R.id.textTriesValue);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeLeftInMilis = 3000;
                int dziesiateMilisekundy = (int) (timeLeftInMilis%1000);
                int sekundy = (int) (timeLeftInMilis/1000)%60;
                String timeleftFormatted =  String.format(Locale.getDefault(),"%02d:%02d", sekundy,dziesiateMilisekundy);
                countDownTextView.setText(timeleftFormatted);
                tries = 0;
                triesTextView.setText(String.valueOf(tries));
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
        int dziesiateMilisekundy = (int) (timeLeftInMilis%1000);
        int sekundy = (int) (timeLeftInMilis/1000)%60;
        String timeleftFormatted =  String.format(Locale.getDefault(),"%02d:%02d", sekundy,dziesiateMilisekundy);
        countDownTextView.setText(timeleftFormatted);
    }

    private void pauseTimer() {

        timeRunning = false;
        buttonStartPause.setText("start");
        cdt.cancel();
        tries++;
        //ponizej jest zawarta logika obliczania punktow zdobydych przez uzytkownika
        if((int)timeLeftInMilis/1000.000<1)
        {
            score = Math.round(-(((int)timeLeftInMilis/1000.000)-1) * 1e3)  / 1e3;
        }
        else if((int)timeLeftInMilis/1000.000==1){
            score = 0;
        }
        else{
            score = ((int)timeLeftInMilis-1000.000)/1000;
        }
        //score =(int) timeLeftInMilis /1000.000;
        triesTextView.setText(String.valueOf(tries));
        scoreTextView.setText(String.valueOf(score));

        buttonSendOnServer.setVisibility(View.VISIBLE);

        buttonSendOnServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        //Tu je onclick do wysyłania wyników

            }
        });
    }
}
