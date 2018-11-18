package com.proximedia.securityhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import android.view.View.OnClickListener;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity {
        private ImageButton retour;
        private ImageButton arreter;
        private EditText EDalam;
        private TextView alarm ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            init();
        }

        private void init() {
            retour=(ImageButton)findViewById(R.id.retour3);
            arreter=(ImageButton)findViewById(R.id.stop);
            alarm=(TextView)findViewById(R.id.tvalarm);
            ajouterEcouteur();
        }
    protected void onStart(){
        super.onStart();
        startService(new Intent(Home.this,AlertService.class));
    }

        private void ajouterEcouteur() {
            retour.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    finish();
                }
            });
            arreter.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Runnable r = new Runnable() {

                        @Override
                        public void run() {
                           execarreter();
                        }
                    };
                    Thread th = new Thread(r);
                    th.start();
                    alarm.setText("STOP ALARM");
                }
            });
        }

     private void execarreter() {
         final String url;
         url = "http://porte.alwaysdata.net/ARRETALARME.php";
         StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {
                         try {
                             JSONObject js = new JSONObject(response);
                             String res = js.getString("nom");
                             if (res.equals("introuvable")) {
                                 Toast.makeText(Home.this, "Donnees incorrectes", Toast.LENGTH_LONG).show();
                             } else {
                                 AlertDialog.Builder r = new AlertDialog.Builder(Home.this);
                                 r.setMessage("Votre alarme est Arreté" +"\n");
                                 r.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                     @Override
                                     public void onClick(DialogInterface arg0, int arg1) {
                                     }
                                 });

                                 AlertDialog alertDialog = r.create();
                                 alertDialog.show();
                             }

                         } catch (Exception e) {
                             Toast.makeText(Home.this, "Impossible du connexion" , Toast.LENGTH_LONG).show();
                         }
                     }
                 },
                 new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         Toast.makeText(Home.this, "Probleme de connexion", Toast.LENGTH_LONG).show();
                     }
                 }) {
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String, String> params = new HashMap<>();
                 //Adding parameters to request
                 params.put("all","0");

                 //returning parameter
                 return params;
             }
         };
         //Adding the string request to the queue
         RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
         requestQueue.add(stringRequest);
     }
        }





