package com.proximedia.securityhome;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
        private EditText Edid;
        private EditText EdMotpass ;
        private ImageButton save;
        private ImageButton Annuler;
        private ImageButton Sett;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
           ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .isConnectedOrConnecting();
            boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .isConnectedOrConnecting();
            System.out.println(is3g + " net " + isWifi);
            if (!is3g && !isWifi)
            {
                AlertDialog.Builder r=new AlertDialog.Builder(this);
                r.setTitle("Connexion Impossible \n Vérifier vous paramètres \n");
                r.setPositiveButton("Fermer", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });
                r.setNegativeButton("Parametre",  new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        Intent gpsOptionsIntent = new Intent(  android.provider.Settings.ACTION_SETTINGS);
                        startActivityForResult(gpsOptionsIntent,0);
                    }
                });
                AlertDialog alertDialog=r.create();
                alertDialog.show();
            }
            final ConnectivityManager connMgr = (ConnectivityManager)
                    this.getSystemService(Context.CONNECTIVITY_SERVICE);
            final android.net.NetworkInfo wifi =
                    connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            final android.net.NetworkInfo mobile =
                    connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if( wifi.isAvailable() && wifi.getDetailedState() == NetworkInfo.DetailedState.CONNECTED){
                Toast.makeText(this, "vous etes Connecté avec wifi" , Toast.LENGTH_LONG).show();
            }
            else if( mobile.isAvailable() && mobile.getDetailedState() == NetworkInfo.DetailedState.CONNECTED ){
                Toast.makeText(this, "vous etes Connecté avec réseau mobile 3G " , Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, "Aucun réseau detecté" , Toast.LENGTH_LONG).show();
            }
            init();
        }
        private void init() {
            Edid=(EditText)findViewById(R.id.Edid);
            EdMotpass=(EditText)findViewById(R.id.edMot_pass);
            save=(ImageButton)findViewById(R.id.IBSave);
            Annuler=(ImageButton)findViewById(R.id.IBAnnuler);
            Sett=(ImageButton)findViewById(R.id.Setting);
            ajouterEcouteur();


        }
    protected void onStart(){
        super.onStart();
        startService(new Intent(MainActivity.this,AlertService.class));
    }
        private void ajouterEcouteur() {
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String id, mot;
                    id = Edid.getText().toString();
                    mot = EdMotpass.getText().toString();

                    if (id.equals("")) {
                        Edid.setError("Identifiant obligatoire");
                    } else if (mot.equals("")) {
                        EdMotpass.setError("password obligatoire");
                    } else {
                        final String url;
                        url = "http://porte.alwaysdata.net/Authentification.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject js = new JSONObject(response);
                                            String res = js.getString("nom");
                                            String re = js.getString("etat");
                                            String da = js.getString("date");
                                            if (res.equals("introuvable")&& re.equals("introuvable") ) {
                                                Toast.makeText(MainActivity.this, "Donnees incorrectes", Toast.LENGTH_LONG).show();
                                            } else {
                                                AlertDialog.Builder r = new AlertDialog.Builder(MainActivity.this);
                                                r.setMessage("Bonjour Monsieur : " + res + "\n Votre etat du porte : "+ re + "\n Date derniere action : "+da);
                                                r.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                        Intent i = new Intent(MainActivity.this, Consultation.class);
                                                        String id= getIntent().getStringExtra("id");
                                                        i.putExtra("id",id);
                                                        startActivity(i);
                                                        NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                                        Notification n = new Notification.Builder(getApplicationContext()).setContentTitle("Systeme securite activé").setContentText("Tu peut commander votre system").setSmallIcon(R.mipmap.bubble).build();
                                                        n.defaults |= Notification.DEFAULT_SOUND;
                                                        n.flags |=Notification.FLAG_AUTO_CANCEL;
                                                        long[] vibrate = { 0, 100, 200, 300 };
                                                        n.vibrate = vibrate;
                                                        notif.notify(0,n);
                                                    }
                                                });

                                                AlertDialog alertDialog = r.create();
                                                alertDialog.show();
                                            }

                                        } catch (Exception e) {
                                            Toast.makeText(MainActivity.this, "Impossible du connexion" , Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MainActivity.this, "Probleme de connexion", Toast.LENGTH_LONG).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                //Adding parameters to request
                                params.put("id", encrypt(id));
                                params.put("mot_pass", encrypt(mot));

                                //returning parameter
                                return params;
                            }
                        };
                        //Adding the string request to the queue
                        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                        requestQueue.add(stringRequest);


                    }
                }
            });

            Annuler.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    finish();
                }
            });
            Sett.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Runnable r = new Runnable() {

                        @Override
                        public void run() {
                            Intent i = new Intent(MainActivity.this,Parametre.class);
                            startActivity(i);
                        }
                    };
                    Thread th = new Thread(r);
                    th.start();
                }
            });
        }
        private String encrypt(String id){
            String crypte=id;
            crypte = crypte.replace("a","Z");
            crypte = crypte.replace("b","Y");
            crypte = crypte.replace("c","X");
            crypte = crypte.replace("d","W");
            crypte = crypte.replace("e","V");
            crypte = crypte.replace("f","U");
            crypte = crypte.replace("g","T");
            crypte = crypte.replace("h","S");
            crypte = crypte.replace("i","R");
            crypte = crypte.replace("j","Q");
            crypte = crypte.replace("k","P");
            crypte = crypte.replace("l","O");
            crypte = crypte.replace("m","N");
            crypte = crypte.replace("n","M");
            crypte = crypte.replace("o","L");
            crypte = crypte.replace("p","K");
            crypte = crypte.replace("q","J");
            crypte = crypte.replace("r","I");
            crypte = crypte.replace("s","H");
            crypte = crypte.replace("t","G");
            crypte = crypte.replace("u","F");
            crypte = crypte.replace("v","E");
            crypte = crypte.replace("w","D");
            crypte = crypte.replace("x","C");
            crypte = crypte.replace("y","B");
            crypte = crypte.replace("z","A");

            crypte=crypte.replace("1","!");
            crypte=crypte.replace("2","?");
            crypte=crypte.replace("3","<");
            crypte=crypte.replace("4",">");
            crypte=crypte.replace("5","*");
            crypte=crypte.replace("6","$");
            crypte=crypte.replace("7","@");
            crypte=crypte.replace("8","^");
            crypte=crypte.replace("9","&");
            crypte=crypte.replace("0","_");

            return crypte;
        }
}

