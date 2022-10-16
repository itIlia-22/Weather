package com.example.move.history.historyviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.move.model.Weather
import com.example.move.history.historyviewmodel.repository.HistoryRepositoryImpl
import com.example.move.model.romm.HistoryEntity
import com.example.move.viewmodel.AppState

class HistoryViewModel(
    private val historyLaveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: HistoryRepositoryImpl = HistoryRepositoryImpl(),
) :
    ViewModel() {

    fun getLaveData() = historyLaveData

    fun getAllHistory() {
       historyRepository.getAllWeather(object :Callback{
           override fun onResponse(weather: List<Weather>) {
               historyLaveData.postValue(AppState.Success(weather))
           }

       })
    }






    interface Callback {
        fun onResponse(weather: List<Weather>)

    }
}