package com.proximedia.securityhome;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class AlertNot extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //On créer un TextView en Java
        TextView txt=new TextView(this);
        txt.setText( "tu peux commander votre systeme securité");

        //On ajoute notre TextView à la vue
        setContentView(txt);

        //On supprime la notification de la liste de notification comme dans la méthode cancelNotify de l'Activity principale
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);


    }
}
