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
                            String result = "  Nickname         Tries           Result\n";
                            String wyniki[][] = new String[response.length()][3];
                            do{
                                JSONObject match = response.getJSONObject(g);
                                String nick = match.getString("nickname");
                                String tries = match.getString("tries");
                                String score = match.getString("result");
                                //ponizej zapisuje wynik do tablicy zeby mozna bylo go pozniej uporzadkowac
                                wyniki[g][2] = score ;
                                wyniki[g][1] = tries;
                                wyniki[g][0] = nick;
                                g++;
                            }while(g<response.length());
                            String wypisz = "";
                            //ponizej segreguje wyniki
                            for(int j=0;j<response.length();j++)
                            for(int i=0;i<response.length()-1;i++){
                                if(Double.parseDouble(wyniki[i][2])>Double.parseDouble(wyniki[i][2])){
                                    String tempNick,tempScore,tempTries;
                                    tempNick = wyniki[i][0];
                                    tempTries = wyniki[i][1];
                                    tempScore = wyniki[i][2];
                                    wyniki[i][0] = wyniki[i+1][0];
                                    wyniki[i][1] = wyniki[i+1][1];
                                    wyniki[i][2] = wyniki[i+1][2];
                                    wyniki[i+1][0] = tempNick;
                                    wyniki[i+1][1] = tempTries;
                                    wyniki[i+1][2] = tempScore;
                                }
                                if(Integer.parseInt(wyniki[i][1])>Integer.parseInt(wyniki[i+1][1])){
                                    String tempNick,tempScore,tempTries;
                                    tempNick = wyniki[i][0];
                                    tempTries = wyniki[i][1];
                                    tempScore = wyniki[i][2];
                                    wyniki[i][0] = wyniki[i+1][0];
                                    wyniki[i][1] = wyniki[i+1][1];
                                    wyniki[i][2] = wyniki[i+1][2];
                                    wyniki[i+1][0] = tempNick;
                                    wyniki[i+1][1] = tempTries;
                                    wyniki[i+1][2] = tempScore;
                                }
                            }

                            //Tworze stringa do wyswietlania ostatecznie
                            for(int j=0;j<response.length();j++){
                                wypisz+=String.format("%1$-10s %2$10s %3$20s \n",wyniki[j][0], wyniki[j][1], wyniki[j][2]);
                            }
                            TextView widok = findViewById(R.id.textViewWyniki);
                            widok.setText(result+wypisz);
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
