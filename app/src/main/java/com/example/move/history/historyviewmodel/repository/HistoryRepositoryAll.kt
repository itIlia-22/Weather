package com.example.move.history.historyviewmodel.repository

import com.example.move.history.historyviewmodel.HistoryViewModel

interface HistoryRepositoryAll {
    fun getAllWeather(callback: HistoryViewModel.Callback)

}