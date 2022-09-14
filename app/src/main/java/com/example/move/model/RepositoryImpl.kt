package com.example.move.model

class RepositoryImpl : Repository{
    override fun getWeatherFromServer() = Weather()

    override fun getWeatherFromLocalStorage() = Weather()


}