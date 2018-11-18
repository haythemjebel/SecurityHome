package com.proximedia.securityhome;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import android.view.View.OnClickListener;



public class Consultation extends AppCompatActivity {
    public static final int ID_NOTIFICATION = 0000;
   // private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private ImageButton BRetour;
    private ImageButton bthome;
    private ImageButton bporte;
    private ImageButton imgv;
    private ImageButton uti ;
    private ImageButton Fenetre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

                init();
            }
            private void init() {
                bporte=(ImageButton)findViewById(R.id.btn_porte);
                bthome=(ImageButton)findViewById(R.id.btnhome);
                BRetour=(ImageButton)findViewById(R.id.btn_retour);
                imgv=(ImageButton)findViewById(R.id.imgtr);
                uti=(ImageButton)findViewById(R.id.utilisat);
                Fenetre=(ImageButton)findViewById(R.id.fenetre);
                ajouterEcouteur();
            }
            private void ajouterEcouteur() {
                bporte.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Fporte();
                    }
                });
                bthome.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        homep();
                    }
                });
                BRetour.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        finish();
                    }
                });
                imgv.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        naviger();
                    }
                });
                uti.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        inscript();
                    }
                });
                Fenetre.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                      fene();
                    }
                });
            }

         protected void fene() {
              Intent i = new Intent(Consultation.this,Fenetre.class);
                 startActivity(i);
           }

          protected void inscript() {
             Intent i = new Intent(Consultation.this,Inscription.class);
              startActivity(i);
            }

            protected void naviger() {
                String url = "https://www.ivideon.com/my/watch/100-bBZCh1BWjRQj44qDDli8Um/0";
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                startActivity(intent);

            }
            protected void Fporte() {
                Intent i = new Intent(Consultation.this,Menu.class);
                String id= getIntent().getStringExtra("id");
                i.putExtra("id",id);
                startActivity(i);
            }
    protected void onStart(){
        super.onStart();
        startService(new Intent(Consultation.this,AlertService.class));
    }
            protected void homep() {
               //verification dans la base
                StringRequest sr = new StringRequest(Request.Method.POST,"http://porte.alwaysdata.net/SMSGAZ.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject js = new JSONObject(response);
                            String n = js.getString("flag");

                           if (n.equals("1")) {
                               sendSMS("20723671", "Ilya une fuite du gaz!");
                               NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                               Notification n1 = new Notification.Builder(getApplicationContext()).setContentTitle("Alarme du gaz").setContentText("Ilya une fuite du gaz").setSmallIcon(R.mipmap.bubble).build();
                               n1.defaults |= Notification.DEFAULT_SOUND;
                               n1.flags |=Notification.FLAG_AUTO_CANCEL;
                               long[] vibrate = { 0, 100, 200, 300 };
                               n1.vibrate = vibrate;

                               notif.notify(0,n1);
                            } else {
                                Toast.makeText(Consultation.this, "Etat normal du gaz  ", Toast.LENGTH_LONG).show();
                               sendSMS("20723671", "Etat normal du gaz!");
                            }
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast t =Toast.makeText(Consultation.this, "Probleme de connexion", Toast.LENGTH_LONG);
                        t.show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<String, String>();
                        return param;
                    }
                };
                RequestQueue rq = Volley.newRequestQueue(Consultation.this);
                rq.add(sr);
                Intent i = new Intent(Consultation.this,Home.class);
                String id= getIntent().getStringExtra("id");
                i.putExtra("id",id);
                startActivity(i);

            }
                private void sendSMS(String phoneNumber, String message) {
                     SmsManager sms = SmsManager.getDefault();
                     sms.sendTextMessage(phoneNumber, null, message, null, null);
                 }
        }

