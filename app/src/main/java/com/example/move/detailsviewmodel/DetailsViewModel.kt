package com.example.move.detailsviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.move.model.City
import com.example.move.model.Weather
import com.example.move.repository.DetailsRepository
import com.example.move.repository.DetailsRepositoryImpl
import com.example.move.utils.CORRUPTED_DATA

class DetailsViewModel(
    private val detailsLiveData: MutableLiveData<DetailsState> = MutableLiveData(),
    private val detailsRepository: DetailsRepository = DetailsRepositoryImpl()

) : ViewModel() {
    fun getLiveData() = detailsLiveData
    fun getWeatherProcessing(city: City) {
detailsRepository.getWeatherCityFromServer(city,object : Callbak {
    override fun onResponse(weather: Weather) {
        detailsLiveData.postValue(DetailsState.Success(weather))
    }

    override fun onFail(detailsState: DetailsState) {
        detailsLiveData.postValue(DetailsState.Error(Throwable(CORRUPTED_DATA)))
    }

})
    }

    interface Callbak {
        fun onResponse(weather: Weather)
        fun onFail(detailsState: DetailsState)
    }


}