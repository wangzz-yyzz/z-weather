package com.example.z_weather;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.z_weather.dao.CityDao;
import com.example.z_weather.fragment.CityWeather;
import com.example.z_weather.pojo.City;
import com.example.z_weather.pojo.Weather;
import com.example.z_weather.util.WeatherUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    GestureDetector mGestureDetector;
    ArrayList<City> cityList = new ArrayList<>();
    private int currentCity = 0;
    WeatherUtil weatherUtil = new WeatherUtil();
    Gson gson = new Gson();
    CityDao cityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityDao = new CityDao(this);
        mGestureDetector = new GestureDetector(this, new MyGestureListener());


        // 获取城市数据
        if (cityDao.selectCityAll().isEmpty()){
            cityList.add(new City("重庆"));
            try {
                getData();
                changeCity();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cityDao.insertCity(cityList.get(0));
        }
        cityList = cityDao.selectCityAll();
        try {
            getData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        changeCity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获取城市数据
        currentCity = 0;

        // 检测到数据发生改变时，重新渲染界面
        ArrayList<City> newList = cityDao.selectCityAll();
        if (cityList.size()!=newList.size()){
            if (cityDao.selectCityAll().isEmpty()){
                cityList.clear();
                cityList.add(new City("重庆"));
                try {
                    getData();
                    changeCity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cityDao.insertCity(cityList.get(0));
                cityList = cityDao.selectCityAll();
                changeCity();
            }

            cityDao.deleteAll();
            cityList = newList;
            // APP初始化时开始获取数据
            try {
                getData();
                changeCity();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < cityList.size(); i++){
                cityDao.insertCity(cityList.get(i));
            }
            cityList = cityDao.selectCityAll();
        }

    }

    public void getData() throws InterruptedException {
        for (int i = 0; i < cityList.size(); i++){
            City city = cityList.get(i);
            city.setCode(weatherUtil.getCityCode(city.getName()));

            ArrayList<Weather> weathers = weatherUtil.getWeather(city.getCode());
            city.setWeather(weathers.get(0));
            city.setWeathers(weathers);
        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public void changeCity(){
        // 给碎片传送新的城市数据
        City city = cityList.get(currentCity);

        // 碎片获取数据
        Fragment cityFragment = getSupportFragmentManager().findFragmentById(R.id.city);
        Bundle bundle = new Bundle();
        bundle.putString("city", gson.toJson(city));
        cityFragment.setArguments(bundle);

        cityFragment.onStart();
        // 给碎片重新发送数据
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            if (e1.getX() - e2.getX() > 20) {
                Log.i("MainActivity", "To Left");
                changeCityLeft();
            }
            if (e2.getX() - e1.getX() > 20) {
                Log.i("MainActivity", "To Right");
                changeCityRight();
            }
            return true;
        }
    }

    public void changeCityLeft(){
        if (currentCity < cityList.size() - 1){
            currentCity = currentCity + 1;
            changeCity();
        } else {
            Toast.makeText(this,"this is the last city",Toast.LENGTH_SHORT).show();
        }
    }

    public void changeCityRight(){
        if (currentCity > 0){
            currentCity = currentCity - 1;
            changeCity();
        } else {
            Toast.makeText(this,"this is the first city",Toast.LENGTH_SHORT).show();
        }
    }

    public void gotoSearch() {
        CityWeather cityWeather = new CityWeather();
        getSupportFragmentManager().beginTransaction().replace(R.id.cityFragment,cityWeather).commit();
    }
}
