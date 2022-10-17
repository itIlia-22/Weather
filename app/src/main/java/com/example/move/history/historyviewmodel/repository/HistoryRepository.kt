package com.example.move.history.historyviewmodel.repository

import com.example.move.viewmodel.detailsviewmodel.DetailsViewModel
import com.example.move.model.City

interface HistoryRepository {
   fun getWeather(city: City,callback: DetailsViewModel.Callback)

}