package com.z_news.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.z_weather.R
import com.example.z_weather.pojo.City

class CityAdapter(private val cityList: List<City>, val context: Context): RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val cityName: TextView = view.findViewById(R.id.cityName_item)
    }

    private var mItemClickListener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(itemClickListener: ItemClickListener?) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cityList[position]
        holder.cityName.text = city.name

        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener { // 这里利用回调来给RecyclerView设置点击事件
                mItemClickListener!!.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return cityList.size
    }
}