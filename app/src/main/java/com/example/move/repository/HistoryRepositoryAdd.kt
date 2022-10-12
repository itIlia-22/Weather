package com.example.move.repository

import com.example.move.model.Weather

interface HistoryRepositoryAdd {
    fun getAddWeather(weather: Weather)

}