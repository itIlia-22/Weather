package com.example.move.model

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorage(): Weather
    fun getDataWeatherFromLocalRus(): List<Weather>
    fun getDataWeatherFromLocalWorld(): List<Weather>


}