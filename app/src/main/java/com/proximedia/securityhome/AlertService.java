package com.proximedia.securityhome;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AlertService extends Service {
    public AlertService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        super.onCreate();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        StringRequest sr = new StringRequest(Request.Method.POST, "http://porte.alwaysdata.net/ServiceAlarme.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject js=new JSONObject(response);
                            String etat=js.getString("etat");
                            if(etat.equals("1"))
                            {
                                int NOTIFICATION_ID = 12345;
                                NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification n = new Notification.Builder(getApplicationContext()).setContentTitle("SERVICE ALARME").setContentText("Alerte du ALARME du GAZ").setSmallIcon(R.mipmap.bubble).build();
                                n.defaults |= Notification.DEFAULT_SOUND;
                                n.flags |=Notification.FLAG_AUTO_CANCEL;
                                long[] vibrate = { 0, 100, 200, 300 };
                                n.vibrate = vibrate;
                                notif.notify(NOTIFICATION_ID,n);
                            }
                            else
                            {
                            }
                        }
                        catch(Exception e)
                        {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("d","0");
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(getApplicationContext());
        rq.add(sr);

      //////////////////////////////////////obstacle//////////////////////////////////////////////
       StringRequest sr1 = new StringRequest(Request.Method.POST, "http://porte.alwaysdata.net/ServiceObstacle.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject js=new JSONObject(response);
                            String etat=js.getString("etat");
                            if(etat.equals("1"))
                            {
                                int NOTIFICATION_ID = 1234;
                                NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification n = new Notification.Builder(getApplicationContext()).setContentTitle("SERVICE SECURITE").setContentText("IL y a une mouvement").setSmallIcon(R.mipmap.bubble).build();
                                n.defaults |= Notification.DEFAULT_SOUND;
                                n.flags |=Notification.FLAG_AUTO_CANCEL;
                                long[] vibrate = { 0, 100, 200, 300 };
                                n.vibrate = vibrate;
                                notif.notify(NOTIFICATION_ID,n);
                            }
                            else
                            {
                            }
                        }
                        catch(Exception e)
                        {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("a","0");
                return params;
            }
        };
        RequestQueue rq1= Volley.newRequestQueue(getApplicationContext());
        rq1.add(sr1);
        //////////////////////////////////////Personne//////////////////////////////////////////////
        StringRequest sr2 = new StringRequest(Request.Method.POST, "http://porte.alwaysdata.net/ServicePersonne.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject js=new JSONObject(response);
                            String etat=js.getString("etat");
                            if(etat.equals("1"))
                            {
                                int NOTIFICATION_ID = 123;
                                NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification n = new Notification.Builder(getApplicationContext()).setContentTitle("PERSONNE EN ATTEND").setContentText("Presence du personne").setSmallIcon(R.mipmap.bubble).build();
                                n.defaults |= Notification.DEFAULT_SOUND;
                                n.flags |=Notification.FLAG_AUTO_CANCEL;
                                long[] vibrate = { 0, 100, 200, 300 };
                                n.vibrate = vibrate;
                                notif.notify(NOTIFICATION_ID,n);
                            }
                            else
                            {
                            }
                        }
                        catch(Exception e)
                        {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("b","0");
                return params;
            }
        };
        RequestQueue RequestQueue= Volley.newRequestQueue(getApplicationContext());
        RequestQueue.add(sr2);

        return super.onStartCommand(intent, flags, startId);
    }
}
