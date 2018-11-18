package com.proximedia.securityhome;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Fenetre extends AppCompatActivity {
    private ImageButton ouvrir;
    private ImageButton fermer;
    private TextView fenetre;
    private ImageButton retour ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenetre);
        init();
    }

    private void init() {
        fermer = (ImageButton) findViewById(R.id.close);
        ouvrir = (ImageButton) findViewById(R.id.open);
        fenetre=(TextView)findViewById(R.id.fene);
        retour=(ImageButton)findViewById(R.id.retour);
        ajouterEcouteur();
    }

    private void ajouterEcouteur() {
        fermer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fermeture();
            }
        });
        ouvrir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Ouverture();

            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        fenetre.setText("FERMER");

    }
    protected void onStart(){
        super.onStart();
        startService(new Intent(Fenetre.this,AlertService.class));
    }

      protected void execfermer() {
          final String url;
          url = "http://porte.alwaysdata.net/Mischlere.php";
          StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          try {
                              JSONObject js = new JSONObject(response);
                              String res = js.getString("nom");
                              if (res.equals("introuvable")) {
                                  Toast.makeText(Fenetre.this, "Donnees incorrectes", Toast.LENGTH_LONG).show();
                              } else {
                                  AlertDialog.Builder r = new AlertDialog.Builder(Fenetre.this);
                                  r.setMessage("Votre etat du fenetre modifier" +"\n");
                                  r.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                      @Override
                                      public void onClick(DialogInterface arg0, int arg1) {
                                      }
                                  });

                                  AlertDialog alertDialog = r.create();
                                  alertDialog.show();
                              }

                          } catch (Exception e) {
                              Toast.makeText(Fenetre.this, "Impossible du connexion" , Toast.LENGTH_LONG).show();
                          }
                      }
                  },
                  new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          Toast.makeText(Fenetre.this, "Probleme de connexion", Toast.LENGTH_LONG).show();
                      }
                  }) {
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                  Map<String, String> params = new HashMap<>();
                  //Adding parameters to request
                  params.put("fen","0");
                  DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                  String date = df.format(Calendar.getInstance().getTime());
                  params.put("date",date);

                  //returning parameter
                  return params;
              }
          };
          //Adding the string request to the queue
          RequestQueue requestQueue = Volley.newRequestQueue(Fenetre.this);
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
        fenetre.setText("OUVERT");
    }

        protected void execouvrir() {
            final String url;
            url = "http://porte.alwaysdata.net/Mischlere.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject js = new JSONObject(response);
                                String res = js.getString("nom");
                                if (res.equals("introuvable")) {
                                    Toast.makeText(Fenetre.this, "Donnees incorrectes", Toast.LENGTH_LONG).show();
                                } else {
                                    AlertDialog.Builder r = new AlertDialog.Builder(Fenetre.this);
                                    r.setMessage("Votre etat du fenetre modifier" +"\n");
                                    r.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                        }
                                    });

                                    AlertDialog alertDialog = r.create();
                                    alertDialog.show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(Fenetre.this, "Impossible du connexion" , Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Fenetre.this, "Probleme de connexion", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request
                    params.put("fen","1");
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String date = df.format(Calendar.getInstance().getTime());
                    params.put("date",date);

                    //returning parameter
                    return params;
                }
            };
            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(Fenetre.this);
            requestQueue.add(stringRequest);
        }
}
