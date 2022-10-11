package com.example.move.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.move.repository.Repository
import com.example.move.repository.RepositoryImpl
import java.lang.Thread.sleep

open class MainViewModel(
    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(),
) : ViewModel() {


    fun getLiveData() = liveDataToObserver
    fun getWeatherFromLocalRus() = getDataFromLocalSource(isRussian = true)
    fun getWeatherFromLocalWorld() = getDataFromLocalSource(isRussian = false)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserver.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserver.postValue(AppState.Success(
                if (isRussian)
                    repository.getDataWeatherFromLocalRus()
                else
                    repository.getDataWeatherFromLocalWorld()

            ))
        }.start()
    }

}