package com.proximedia.securityhome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Menu extends AppCompatActivity {
    private ImageButton btnouvrir;
    private ImageButton btnfermer;
    private ImageButton retour;
    private EditText edEtat;
    private TextView Etat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
    }
        private void init() {
            btnfermer = (ImageButton) findViewById(R.id.close);
            btnouvrir = (ImageButton) findViewById(R.id.open);
            retour = (ImageButton) findViewById(R.id.ret);
            Etat=(TextView)findViewById(R.id.tvEtat);

            ajouteurEcouteur();
        }

        private void ajouteurEcouteur() {
            retour.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    finish();
                }
            });
            btnfermer.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Fermeture();
                }
            });
            btnouvrir.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Ouverture();

                }
            });
        }

        protected void Fermeture() {
            Context context = getApplicationContext();
            CharSequence text = "en cours du fermeture";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    execfermer();
                }
            };
            Thread th = new Thread(r);
            th.start();
            Etat.setText("FERMER");
        }

        protected void execfermer() {
            final String url;
            url = "http://porte.alwaysdata.net/commandeU.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject js = new JSONObject(response);
                                String res = js.getString("nom");
                                if (res.equals("introuvable")) {
                                    Toast.makeText(Menu.this, "Donnees incorrectes", Toast.LENGTH_LONG).show();
                                } else {
                                    AlertDialog.Builder r = new AlertDialog.Builder(Menu.this);
                                    r.setMessage("Votre etat du porte modifier" +"\n");
                                    r.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                        }
                                    });

                                    AlertDialog alertDialog = r.create();
                                    alertDialog.show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(Menu.this, "Impossible du connexion" , Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Menu.this, "Probleme de connexion", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String date = df.format(Calendar.getInstance().getTime());
                    params.put("action","0");
                    params.put("date",date);

                    //returning parameter
                    return params;
                }
            };
            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(Menu.this);
            requestQueue.add(stringRequest);

        }

        protected void Ouverture() {
            Context context = getApplicationContext();
            CharSequence text = "en cours du ouverture ";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Runnable r = new Runnable() {
                @Override
                public void run() {
                 execouvrir();
                }
            };
            Thread th = new Thread(r);
            th.start();
            Etat.setText("OUVERT");
        }
    protected void onStart(){
        super.onStart();
        startService(new Intent(Menu.this,AlertService.class));
    }

     protected void execouvrir() {
         final String url;
         url = "http://porte.alwaysdata.net/commandeU.php";
         StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {
                         try {
                             JSONObject js = new JSONObject(response);
                             String res = js.getString("nom");
                             if (res.equals("introuvable")) {
                                 Toast.makeText(Menu.this, "Donnees incorrectes", Toast.LENGTH_LONG).show();
                             } else {
                                 AlertDialog.Builder r = new AlertDialog.Builder(Menu.this);
                                 r.setMessage("Votre etat du porte modifier" +"\n");
                                 r.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                     @Override
                                     public void onClick(DialogInterface arg0, int arg1) {
                                     }
                                 });

                                 AlertDialog alertDialog = r.create();
                                 alertDialog.show();
                             }

                         } catch (Exception e) {
                             Toast.makeText(Menu.this, "Impossible du connexion" , Toast.LENGTH_LONG).show();
                         }
                     }
                 },
                 new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         Toast.makeText(Menu.this, "Probleme de connexion", Toast.LENGTH_LONG).show();
                     }
                 }) {
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String, String> params = new HashMap<>();
                 //Adding parameters to request
                 params.put("action","1");
                 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                 String date = df.format(Calendar.getInstance().getTime());
                 params.put("date",date);
                 return params;
             }
         };
         //Adding the string request to the queue
         RequestQueue requestQueue = Volley.newRequestQueue(Menu.this);
         requestQueue.add(stringRequest);
     }

}
