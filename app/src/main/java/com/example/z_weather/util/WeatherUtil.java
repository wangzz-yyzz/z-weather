package com.example.z_weather.util;

import android.util.Log;

import com.example.z_weather.pojo.Weather;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherUtil {
    public ArrayList<Weather> getWeather (String cityCode) throws InterruptedException {
        final JsonParser parser = new JsonParser();
        final ArrayList<Weather> weathers = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String url1 = "https://devapi.qweather.com/v7/weather/7d?";
//        String key = "8a9c925248bf4da29c3790b918391786";
        String key = "e752552475f6456ea0d333d3232c8773";
        String url = url1 + "key=" + key + "&location=" + cityCode;

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        //第三步构建Call对象
        final Call call = okHttpClient.newCall(request);
        //第四步:同步get请求
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();//必须子线程执行
                    String result = response.body().string();

                    JsonElement element = parser.parse(result);
                    JsonObject object = element.getAsJsonObject();  // 转化为对象

                    for (int i = 0; i < 7; i++){
                        Weather weather = new Weather();
                        JsonObject dailyObj = object.get("daily").getAsJsonArray().get(i).getAsJsonObject();

                        // 装填数据
                        weather.setDate(simpleDateFormat.parse(dailyObj.get("fxDate").getAsString()));
                        weather.setTempMax(dailyObj.get("tempMax").getAsFloat());
                        weather.setTempMin(dailyObj.get("tempMin").getAsFloat());
                        weather.setTextDay(dailyObj.get("textDay").getAsString());
                        weather.setWindDirDay(dailyObj.get("windDirDay").getAsString());
                        weather.setIconDay(dailyObj.get("iconDay").getAsString());
                        weather.setUvIndex(dailyObj.get("uvIndex").getAsString());
                        weather.setHumidity(dailyObj.get("humidity").getAsString());
                        weather.setVis(dailyObj.get("vis").getAsString());
                        weather.setDate(simpleDateFormat.parse(dailyObj.get("fxDate").getAsString()));

                        weathers.add(weather);
                        Log.d("city",weather.getTextDay());
                    }

                    Log.d("city weather(weather)", result);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("city",e.toString());
                }
            }
        });

        thread.start();
        thread.join();

        return weathers;
    }

    public String getCityCode(String cityName) throws InterruptedException {
        String key = "e752552475f6456ea0d333d3232c8773";
        String url = "https://geoapi.qweather.com/v2/city/lookup?key="+key+"&location="+cityName;
        final String[] code = new String[1];

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        //第三步构建Call对象
        final Call call = okHttpClient.newCall(request);
        //第四步:同步get请求
        Log.d("city code: ", "start getting code");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();//必须子线程执行
                    String result = response.body().string();

                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(result);
                    JsonObject object = element.getAsJsonObject();  // 转化为对象
                    JsonArray location = object.getAsJsonArray("location");
                    JsonObject locationObj = location.get(0).getAsJsonObject();
                    String id = locationObj.get("id").getAsString();
                    code[0] = id;

                    Log.d("city id(weather)", id);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("city failed", e.toString());
                }
            }
        });
        thread.start();
        thread.join();
        return code[0];
    }
}
