package com.example.move.repository

import com.example.move.BuildConfig
import com.example.move.viewmodel.detailsviewmodel.DetailsState
import com.example.move.viewmodel.detailsviewmodel.DetailsViewModel
import com.example.move.model.City
import com.example.move.model.modelDTO.WeatherDto
import com.example.move.utils.KEY_YANDEX_DOMEN
import com.example.move.utils.REQUEST_ERROR
import com.example.move.utils.SERVER_ERROR
import com.example.move.utils.convertDtoToModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsRepositoryImpl() : DetailsRepository {
    override fun getWeatherCityFromServer(
        city: City,
        callback: DetailsViewModel.Callback,
    ) {
        val weatherApi = Retrofit.Builder().apply {
            baseUrl(KEY_YANDEX_DOMEN)
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        }.build().create(WeatherApi::class.java)
        weatherApi.getWeather(BuildConfig.WEATHER_API_KEY, city.lat, city.lon)
            .enqueue(object : retrofit2.Callback<WeatherDto> {
                override fun onResponse(call: Call<WeatherDto>, response: Response<WeatherDto>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val weather = convertDtoToModel(it)
                            weather.city = city
                            callback.onResponse(weather)

                        }
                    } else {
                        callback.onFail(DetailsState.Error(Throwable(SERVER_ERROR)))
                    }

                }

                override fun onFailure(call: Call<WeatherDto>, t: Throwable) {
                    callback.onFail(DetailsState.Error(Throwable(REQUEST_ERROR)))
                }

            })
    }


}


