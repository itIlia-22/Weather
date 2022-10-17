package com.example.move.repository

import com.example.move.viewmodel.detailsviewmodel.DetailsViewModel
import com.example.move.model.City

interface DetailsRepository {
    fun getWeatherCityFromServer(city: City, callback: DetailsViewModel.Callback)


}