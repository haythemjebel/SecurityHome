package com.proximedia.securityhome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by easer on 12/04/2017.
 */
public class DBHelper extends SQLiteOpenHelper{
    public static final String database_name="porte.db";
    String table_name="server";

    public DBHelper(Context context) {

        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creer la nouvelle table
        db.execSQL("create table "+table_name+" (nom text,ip text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //supprimer la table s'elle existe
        db.execSQL("drop table if exists "+table_name);
    }
    public boolean insertion(String n,String i)
    {
        //appeler la base de données en mode écriture
        SQLiteDatabase db=this.getWritableDatabase();
        //creer objet nommé cv pour ajouter les donnees dans la table existante dans la base db
        ContentValues cv=new ContentValues();
        cv.put("nom",n);
        cv.put("ip",i);
        db.insert(table_name,null,cv);
        return true;
    }
    public Cursor getdata()
    {
        //appeler la base de données en mode lecture
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from "+table_name, null);
        return res;
    }
    public int getrows()
    {
        //appeler la base de données en mode lecture
        SQLiteDatabase db=this.getReadableDatabase();
        int nr= (int)DatabaseUtils.queryNumEntries(db,table_name);
        return nr;
    }
    public boolean update(String i)
    {
        //appeler la base de données en mode écriture
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("ip",i);
        db.update(table_name,cv,null,null);
        return true;
    }
    public boolean delete(String n)
    {
        //appeler la base de données en mode écriture
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(table_name,"nom=?",new String[]{n});
        return true;
    }
}

