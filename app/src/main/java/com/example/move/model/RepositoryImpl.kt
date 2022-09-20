package com.example.move.model

class RepositoryImpl : Repository {
    override fun getWeatherFromServer() = Weather()
    override fun getWeatherFromLocalStorage() = Weather()
    override fun getDataWeatherFromLocalRus(): List<Weather> = getRussianCities()
    override fun getDataWeatherFromLocalWorld(): List<Weather> = getWorldCities()


}