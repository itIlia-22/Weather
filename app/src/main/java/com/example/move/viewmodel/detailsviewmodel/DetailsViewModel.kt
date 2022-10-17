package com.example.move.viewmodel.detailsviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.move.history.historyviewmodel.repository.HistoryRepositoryAdd
import com.example.move.history.historyviewmodel.repository.HistoryRepositoryImpl
import com.example.move.model.City
import com.example.move.model.Weather
import com.example.move.repository.*
import com.example.move.utils.CORRUPTED_DATA

class DetailsViewModel(
    private val detailsLiveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val detailsRepository: DetailsRepository = DetailsRepositoryImpl(),
    private val historyRepository: HistoryRepositoryAdd = HistoryRepositoryImpl()

    ) : ViewModel() {
    fun getLiveData() = detailsLiveData
    fun getWeatherProcessing(city: City) {
        detailsRepository.getWeatherCityFromServer(city, object : Callback {
            override fun onResponse(weather: Weather) {
                detailsLiveData.postValue(DetailsState.Success(weather))
                historyRepository.getAddWeather(weather)
            }

            override fun onFail(detailsState: DetailsState) {
                detailsLiveData.postValue(DetailsState.Error(Throwable(CORRUPTED_DATA)))
            }

        })
    }



    interface Callback {
        fun onResponse(weather: Weather)
        fun onFail(detailsState: DetailsState)
    }


}