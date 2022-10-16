package com.example.move.history.historyviewmodel.repository

import com.example.move.model.Weather

interface HistoryRepositoryAdd {
    fun getAddWeather(weather: Weather)

}