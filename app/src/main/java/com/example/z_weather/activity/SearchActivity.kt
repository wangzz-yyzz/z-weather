package com.example.z_weather.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.z_weather.R
import com.example.z_weather.dao.CityDao
import com.example.z_weather.pojo.City
import com.z_news.adapters.CityAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), View.OnClickListener{

    private var cityDao:CityDao? = null
    var list:ArrayList<City> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        cityDao = CityDao(this)
        list = cityDao!!.selectCityAll()
        Log.d("city", list.toString())
        initView()
    }


    fun initView(){
        val adapter:CityAdapter = CityAdapter(list, this)
        add_search.setOnClickListener(View.OnClickListener {
            if (!cityName_search.text.isEmpty()){
                cityDao?.insertCity(City(cityName_search.text.toString()))
                list.add(City(cityName_search.text.toString()))
                Log.d("city", "add city done")
                cityName_search.text.clear()
                Toast.makeText(this,"add done",Toast.LENGTH_SHORT).show()
            }
            adapter.notifyDataSetChanged()
        })

        // 加载列表
        list_search.layoutManager = LinearLayoutManager(this)
        list_search.adapter = adapter

        adapter.setOnItemClickListener(object : CityAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("city","click item")
                cityDao?.deleteCity(list[position])
                adapter.notifyItemChanged(position)
                Log.d("city", "delete city done")
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onClick(v: View?) {
        Log.d("city", "click any")
    }
}