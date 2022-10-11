package com.example.move.model.modelDTO


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDto(
    @SerializedName("fact")
    val fact: Fact,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("info")
    val info: Info,
    @SerializedName("now")
    val now: Int,
    @SerializedName("now_dt")
    val nowDt: String,
) : Parcelable