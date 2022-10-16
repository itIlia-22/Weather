package com.example.move.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.move.databinding.ItemListHistoryBinding
import com.example.move.history.historyviewmodel.repository.OnDeleteClickListener
import com.example.move.model.Weather
import com.example.move.model.romm.HistoryEntity
import com.example.move.utils.loadSvg

class HistoryWeatherAdapter(

) : RecyclerView.Adapter<HistoryWeatherAdapter.CityViewHolder>() {

    private var weatherData: List<Weather> = listOf()



    @SuppressLint("NotifyDataSetChanged")
    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding =
            ItemListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(weatherData[position])

    }

    override fun getItemCount(): Int = weatherData.size

    inner class CityViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        fun bind(weather: Weather) = ItemListHistoryBinding.bind(itemView).apply {
            city.text = weather.city.city
            cond.text = weather.condition
            fils.text = weather.feelsLike.toString()
            temp.text = weather.temperature.toString()
            icon.loadSvg("https://yastatic.net/weather/i/icons/blueye/color/svg/${weather.icon}.svg")


        }



    }


}