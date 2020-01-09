package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button getBack = findViewById(R.id.buttonGetBack);
        getBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wracamy();
            }
        });

        Toast.makeText(getApplicationContext(),"Witam!", Toast.LENGTH_LONG).show();

    }

    private void wracamy() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}
