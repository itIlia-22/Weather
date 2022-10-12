package com.example.move.repository

import com.example.move.detailsviewmodel.DetailsViewModel
import com.example.move.history.historyviewmodel.HistoryViewModel
import com.example.move.model.Weather

interface HistoryRepositoryAll {
    fun getAllWeather(callback: HistoryViewModel.Callback)

}