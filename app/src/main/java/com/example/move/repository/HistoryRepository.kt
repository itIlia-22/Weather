package com.example.move.repository

import com.example.move.detailsviewmodel.DetailsViewModel
import com.example.move.model.City
import com.example.move.model.Weather

interface HistoryRepository {
   fun getWeather(city: City,callback: DetailsViewModel.Callback)

}