package com.example.move.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityAndWeather(
    val city: String,
    val lat: Double,
    val lon: Double,
):Parcelable