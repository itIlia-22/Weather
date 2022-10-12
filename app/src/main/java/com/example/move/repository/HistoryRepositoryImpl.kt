package com.example.move.repository

import com.example.move.app.App
import com.example.move.detailsviewmodel.DetailsViewModel
import com.example.move.history.historyviewmodel.HistoryViewModel
import com.example.move.model.City
import com.example.move.model.Weather
import com.example.move.utils.convertHistoryToWeather
import com.example.move.utils.convertWeatherToHistory

class HistoryRepositoryImpl() : HistoryRepository,HistoryRepositoryAll,HistoryRepositoryAdd {
    override fun getWeather(city: City, callback: DetailsViewModel.Callback) {
val list = convertHistoryToWeather(App.getHistoryDao().getDataByWorld(city.city))
        callback.onResponse(list.last())
    }

    override fun getAddWeather(weather: Weather) {
        App.getHistoryDao().insert(convertWeatherToHistory(weather))
    }

    override fun getAllWeather(callback: HistoryViewModel.Callback) {
        callback.onResponse(convertHistoryToWeather(App.getHistoryDao().all()))
    }


}