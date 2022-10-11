package com.example.move.repository

import com.example.move.model.City
import com.example.move.detailsviewmodel.DetailsViewModel

interface DetailsRepository {
    fun getWeatherCityFromServer(city: City, callback: DetailsViewModel.Callbak)


}