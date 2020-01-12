package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class Main3Activity extends AppCompatActivity {
    private double score;
    private int tries;
    TextInputEditText yourNickname;
    Button sendResult;
    TextView finalScore, yourTries;
    String nickname;
    private String r_url ="https://webhooks.mongodb-stitch.com/api/client/v2.0/app/checkyourreflex-dcdzf/service/http/incoming_webhook/routerpost";

    public class RequestAsync extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("nn", nickname);
                postDataParams.put("t", tries);
                postDataParams.put("r", score);
                return RequestHandler.sendPost(r_url,postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        yourNickname = findViewById(R.id.TextInputNickname);
        sendResult = findViewById(R.id.buttonSendResult);
        finalScore = findViewById(R.id.textViewScore2);
        yourTries = findViewById(R.id.textViewTries2);

        score = MainActivity.sSEND;
        tries = MainActivity.tSEND;

        finalScore.setText(String.valueOf(score));
        yourTries.setText(String.valueOf(tries));

        new RequestHandler();


        sendResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickname = yourNickname.getText().toString();
                new RequestAsync().execute();
                Toast.makeText(getApplicationContext(),"Score saved!",Toast.LENGTH_LONG).show();
                finish();
            }
});}}