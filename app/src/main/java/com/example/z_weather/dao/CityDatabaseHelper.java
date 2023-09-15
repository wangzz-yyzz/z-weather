package com.example.z_weather.dao;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class CityDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "cityDatabase";
    private static final int DB_VERSION = 1;
    Context context;

    public CityDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table city(id integer primary key AUTOINCREMENT," + "name text," + "data text)");
        Toast.makeText(context, "Create succeed", Toast.LENGTH_SHORT).show();
        Log.e("great", "great");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table city");
//        db.execSQL("create table city(id integer primary key AUTOINCREMENT," + "name text," + "data text)");
//        Toast.makeText(context, "Create succeed", Toast.LENGTH_SHORT).show();
//        Log.e("great", "great");
    }
}
