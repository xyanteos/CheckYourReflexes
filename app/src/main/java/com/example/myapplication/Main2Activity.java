package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

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

        Button updateScoreBoard = findViewById(R.id.buttonRequestStats);

        updateScoreBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateuj();
            }
        });

        //Toast.makeText(getApplicationContext(),"Witam!", Toast.LENGTH_LONG).show();


    }

    private void updateuj() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, "https://webhooks.mongodb-stitch.com/api/client/v2.0/app/checkyourreflex-dcdzf/service/http/incoming_webhook/routerget", null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            int g=0;
                            String result = "         Nickname      Tries       Result\n";
                            do{
                                JSONObject match = response.getJSONObject(g);
                                result +="         "+  match.getString("nickname");
                                result += "   "+ match.getString("tries");
                                result += "   "+ match.getString("result");
                                result += "\n";
                                g++;
                            }while(g<response.length());
                            TextView widok = findViewById(R.id.textViewWyniki);
                            widok.setText(result);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                });

        Volley.newRequestQueue(getApplicationContext()).add(jsonArrayRequest);
    }

    private void wracamy() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}
