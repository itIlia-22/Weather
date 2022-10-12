package com.example.move.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.example.move.databinding.ItemListHistoryBinding
import com.example.move.model.Weather

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

    fun ImageView.loadSvg(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
            .build()
        val imageRequest = coil.request.ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()
        imageLoader.enqueue(imageRequest)
    }
}