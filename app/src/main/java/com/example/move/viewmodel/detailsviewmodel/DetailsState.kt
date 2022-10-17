package com.example.move.viewmodel.detailsviewmodel

import com.example.move.model.Weather

sealed class DetailsState {
    data class Success(val weatherData: Weather) : DetailsState()
    data class Error(val error: Throwable) : DetailsState()
    object Loading : DetailsState()
}