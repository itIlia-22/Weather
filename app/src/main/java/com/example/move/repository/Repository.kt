package com.example.move.repository

import com.example.move.model.Weather

interface Repository {
    fun getWeatherFromLocalStorage(): Weather
    fun getDataWeatherFromLocalRus(): List<Weather>
    fun getDataWeatherFromLocalWorld(): List<Weather>


}