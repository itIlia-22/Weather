package com.example.move.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.move.model.Repository
import com.example.move.model.RepositoryImpl
import java.lang.Thread.sleep

open class MainViewModel(
    private val liveDataToObserver:MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
): ViewModel() {

    fun getLiveData() = liveDataToObserver
    fun getWeather()= getDataFromLocalSource()

     private fun getDataFromLocalSource(){
         liveDataToObserver.value = AppState.Loading
         Thread{
             sleep(1000)
            liveDataToObserver.postValue(AppState.Success(repository.getWeatherFromLocalStorage()))
         }.start()
     }

}