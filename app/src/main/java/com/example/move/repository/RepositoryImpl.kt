package com.example.move.repository

import com.example.move.model.Weather
import com.example.move.model.getRussianCities
import com.example.move.model.getWorldCities

class RepositoryImpl : Repository {

    override fun getWeatherFromLocalStorage() = Weather()
    override fun getDataWeatherFromLocalRus(): List<Weather> = getRussianCities()
    override fun getDataWeatherFromLocalWorld(): List<Weather> = getWorldCities()


}