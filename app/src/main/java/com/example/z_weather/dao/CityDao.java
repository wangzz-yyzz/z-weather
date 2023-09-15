package com.example.z_weather.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.z_weather.pojo.City;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CityDao extends Activity {
    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    Context context;
    Gson gson;

    public CityDao(Context context){
        this.context = context;
        helper = new CityDatabaseHelper(context);
        db = helper.getWritableDatabase();
        gson = new Gson();
    }

    public void insertCity(City city){
        ContentValues values = new ContentValues();
        values.put("name", city.getName());
        values.put("data", gson.toJson(city));

        db.insertWithOnConflict("city", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<City> selectCityAll(){
        ArrayList<City> cityList = new ArrayList<>();
        Cursor cursor = db.query("city",null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            do {
                String data = cursor.getString(cursor.getColumnIndex("data"));
                City city = gson.fromJson(data, City.class);
                cityList.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cityList;
    }

    public void deleteCity(City city){
        db.delete("city", "name = ?", new String[]{city.getName()});
    }

    public void deleteAll(){
        db.delete("city","",null);
    }
}
