package com.proximedia.securityhome;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Inscription extends AppCompatActivity {
    EditText id, nom, password,tel;
    ImageButton ok, quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        id = (EditText) findViewById(R.id.ID);
        nom = (EditText) findViewById(R.id.nom);
        tel=(EditText) findViewById(R.id.tel);
        password = (EditText) findViewById(R.id.password);
        ok = (ImageButton) findViewById(R.id.ok);
        quit=(ImageButton) findViewById(R.id.quit);
        ajouteurEcouteur();
    }
    protected void onStart(){
        super.onStart();
        startService(new Intent(Inscription.this,AlertService.class));
    }

    private void ajouteurEcouteur() {
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String c, n, p,t;
                c = id.getText().toString();
                n = nom.getText().toString();
                p = password.getText().toString();
                t=tel.getText().toString();
                if (c.equals("")) {
                    id.setError("cin Obligatoire");
                } else {
                    if (n.equals("")) {
                        nom.setError("nom Obligatoire");
                    } else {
                        if (t.equals("")) {
                            tel.setError("numero Obligatoire");
                        } else {
                            if (p.equals("")) {
                                password.setError("password Obligatoire");
                            } else {
                                StringRequest sr = new StringRequest(Request.Method.POST, "http://porte.alwaysdata.net/Ajout.php", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject js = new JSONObject(response);
                                            String r = js.getString("flag");
                                            if (r.equals("introuvable")) {
                                                Toast.makeText(Inscription.this, "Probleme d'Ajout", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(Inscription.this, "Ajout avec sucses du " + n, Toast.LENGTH_LONG).show();
                                            }
                                        } catch (Exception e) {

                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(Inscription.this, "Probleme de connexion", Toast.LENGTH_LONG).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> para = new HashMap<String, String>();
                                        para.put("id", c);
                                        para.put("nom", n);
                                        para.put("pass", p);
                                        para.put("tel",t);
                                        return para;
                                    }
                                };
                                RequestQueue rq = Volley.newRequestQueue(Inscription.this);
                                rq.add(sr);
                            }
                        }
                    }
                }
            };
        });
    }
}