package com.example.move.repository

import com.example.move.model.modelDTO.WeatherDto
import com.example.move.utils.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {
    @GET(KEY_YANDEX)
    fun getWeather(
        @Header(KEY_YANDEX_API) apiKay:String,
        @Query(KEY_BUN_LAT) lat:Double,
        @Query(KEY_BUN_LON)lon:Double,
    ): Call<WeatherDto>


}