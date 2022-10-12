package com.example.move.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.move.databinding.ItemListCityBinding
import com.example.move.model.Weather
import com.example.move.repository.ItemOnClickListener

class WeatherAdapter(
    private var itemOnClickListener: ItemOnClickListener?
) : RecyclerView.Adapter<WeatherAdapter.CityViewHolder>() {

    private var weatherData: List<Weather> = listOf()
    fun removeListener() {
        itemOnClickListener = null
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding =
            ItemListCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
       holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int = weatherData.size

    inner class CityViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        fun bind(weather: Weather) = ItemListCityBinding.bind(itemView).apply {
            tvCity.text = weather.city.city
            itemView.setOnClickListener{
                itemOnClickListener?.itemOnClickListener(weather)
            }

        }

    }
}