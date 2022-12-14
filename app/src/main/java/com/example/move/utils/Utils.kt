package com.example.move.utils

import android.view.View
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.example.move.model.City
import com.example.move.model.Weather
import com.example.move.model.getDefaultCity
import com.example.move.model.modelDTO.Fact
import com.example.move.model.modelDTO.Forecast
import com.example.move.model.modelDTO.WeatherDto
import com.example.move.model.romm.HistoryEntity
import com.google.android.material.snackbar.Snackbar


fun convertDtoToModel(weatherDto: WeatherDto): Weather {
    val fact: Fact = weatherDto.fact!!
    val forecast: Forecast = weatherDto.forecast!!
    return (Weather(getDefaultCity(), fact.temp!!, fact.feelsLike!!, fact.condition!!,
        fact.icon!!, fact.windSpeed!!, fact.windGust!!,
        fact.humidity!!, fact.windDir!!, fact.daytime!!,
        false, fact.season!!, forecast.date!!, forecast.week!!, forecast.sunrise!!,
        forecast.sunset!!, forecast.moonCode!!))
}

fun convertHistoryToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(City(it.city, 0.0, 0.0), it.temperature, it.feelsLike, it.condition, it.icon)
    }
}

fun convertWeatherToHistory(weather: Weather): HistoryEntity {
    return HistoryEntity(0,
        weather.city.city,
        weather.temperature,
        weather.feelsLike,
        weather.condition,
        weather.icon)
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

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE,
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun View.detailsSnackBar(
    text: String,
    length: Int = Snackbar.LENGTH_LONG,
) {
    Snackbar.make(this, text, length).show()
}


fun View.show(): View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

fun View.hide(): View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

inline fun View.showIf(condition: () -> Boolean): View {
    if (visibility != View.VISIBLE && condition()) {
        visibility = View.VISIBLE
    }
    return this
}

inline fun View.hideIf(predicate: () -> Boolean): View {
    if (visibility != View.GONE && predicate()) {
        visibility = View.GONE
    }
    return this
}



