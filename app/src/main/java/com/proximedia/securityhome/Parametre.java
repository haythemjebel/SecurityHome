package com.proximedia.securityhome;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Parametre extends AppCompatActivity {
        EditText adrip;
        ImageButton ok;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_parametre);
            adrip=(EditText)findViewById(R.id.adrip);
            ok=(ImageButton)findViewById(R.id.ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBHelper h=new DBHelper(Parametre.this);
                    int nb=h.getrows();
                    boolean b;
                    if(nb==0)
                    {
                        b= h.insertion("PC_SERVER",adrip.getText().toString());
                        if(b==true)
                        {
                            Toast.makeText(Parametre.this,"Adresse ip insérée",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(Parametre.this,"Problème insertion",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        b= h.update(adrip.getText().toString());
                        if(b==true)
                        {
                            Toast.makeText(Parametre.this,"Adresse ip modifiée",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(Parametre.this,"Problème modification",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    protected void onStart(){
        super.onStart();
        startService(new Intent(Parametre.this,AlertService.class));
    }
    }

