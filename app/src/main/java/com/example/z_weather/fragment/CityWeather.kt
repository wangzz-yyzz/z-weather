package com.example.z_weather.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.z_weather.R
import com.example.z_weather.activity.SearchActivity
import com.example.z_weather.pojo.City
import com.example.z_weather.util.IconUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_city_weather.*
import kotlinx.android.synthetic.main.life_index.*
import java.text.SimpleDateFormat
import java.util.*

class CityWeather : Fragment(), View.OnClickListener {
    private var city: City = City("重庆")
    var gson = Gson()
    var cal = Calendar.getInstance()
    var imageUtils = IconUtils()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_city_weather, container, false)
    }

    override fun onStart() {
        // 渲染界面
        super.onStart()
        basicData
        add_city.setOnClickListener(this)
        initView()
    }

    // 解析传来的城市数据
    val basicData: Unit
        get() {
            // 解析传来的城市数据
            val bundle = arguments
            val cityJson = bundle!!.getString("city", "")
            city = gson.fromJson(cityJson, City::class.java)
        }

    override fun onClick(v: View) {
        Log.d("city", v.id.toString() + "")
        if (v.id == R.id.add_city) {
            val intent = Intent()
            intent.setClass(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
    }

    fun initView(){
        city_name.text = city.name
        current_temp.text = getAvgTemp(city.weather.tempMax,city.weather.tempMin)
        frag_condition.text = city.weather.textDay
        frag_maxmin.text = formatTemp(city.weather.tempMax,city.weather.tempMin)

        // 1
        frag_date.text = formatDate(city.weather.date)
        frag_wimag.setImageResource(imageUtils.getDayIconDark(city.weather.iconDay))
        frag_condition0.text = city.weather.textDay
        frag_value.text = formatTemp(city.weather.tempMax,city.weather.tempMin)

        // 2
        frag_date1.text = formatDate(city.weathers[1].date)
        frag_wimag1.setImageResource(imageUtils.getDayIconDark(city.weathers[1].iconDay))
        frag_condition1.text = city.weathers[1].textDay
        frag_value1.text = formatTemp(city.weathers[1].tempMax,city.weathers[1].tempMin)

        // 3
        frag_date2.text = formatDate(city.weathers[2].date)
        frag_wimag2.setImageResource(imageUtils.getDayIconDark(city.weathers[2].iconDay))
        frag_condition2.text = city.weathers[2].textDay
        frag_value2.text = formatTemp(city.weathers[2].tempMax,city.weathers[2].tempMin)

        // 4
        frag_date3.text = formatDate(city.weathers[3].date)
        frag_wimag3.setImageResource(imageUtils.getDayIconDark(city.weathers[3].iconDay))
        frag_condition3.text = city.weathers[3].textDay
        frag_value3.text = formatTemp(city.weathers[3].tempMax,city.weathers[3].tempMin)

        // 5
        frag_date4.text = formatDate(city.weathers[4].date)
        frag_wimag4.setImageResource(imageUtils.getDayIconDark(city.weathers[4].iconDay))
        frag_condition4.text = city.weathers[4].textDay
        frag_value4.text = formatTemp(city.weathers[4].tempMax,city.weathers[4].tempMin)

        // 6
        frag_date5.text = formatDate(city.weathers[5].date)
        frag_wimag5.setImageResource(imageUtils.getDayIconDark(city.weathers[5].iconDay))
        frag_condition5.text = city.weathers[5].textDay
        frag_value5.text = formatTemp(city.weathers[5].tempMax,city.weathers[5].tempMin)

        // 7
        frag_date6.text = formatDate(city.weathers[6].date)
        frag_wimag6.setImageResource(imageUtils.getDayIconDark(city.weathers[6].iconDay))
        frag_condition6.text = city.weathers[6].textDay
        frag_value6.text = formatTemp(city.weathers[6].tempMax,city.weathers[6].tempMin)

        windText.text = city.weather.windDirDay
        humidityText.text = city.weather.humidity
        uvIndexText.text = city.weather.uvIndex
        visText.text = city.weather.vis

        backImage.setImageResource(imageUtils.getDayBack(city.weather.iconDay))
    }

    fun formatTemp(max:Float, min:Float): String{
        return max.toInt().toString()+"/"+min.toInt().toString()+"℃"
    }

    fun formatDate(date: Date):String{
        cal.time = date
        return (cal.get(Calendar.MONDAY)+1).toString() + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日"
    }

    fun getAvgTemp(max:Float, min:Float): String{
        return ((max.toInt()+min.toInt())/2).toString()
    }
}