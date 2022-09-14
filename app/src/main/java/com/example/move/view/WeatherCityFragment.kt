package com.example.move.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.move.R
import com.example.move.databinding.FragmentWeatherCityBinding
import com.example.move.model.Weather
import com.example.move.viewmodel.AppState
import com.example.move.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class WeatherCityFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentWeatherCityBinding? = null
    private val binding: FragmentWeatherCityBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeather()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWeatherCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val weather = appState.weatherData
                binding.loadingLayout.visibility = View.GONE
                setData(weather)
                Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeather() }
                    .show()

            }

        }
    }

    private fun setData(weather: Weather) {
        binding.cityName.text = weather.city.city
        binding.cityCoordinates.text = String.format(
            getString(R.string.city_coordinates),
            weather.city.lat.toString(),
            weather.city.lon.toString())

        binding.temperatureValue.text = weather.temperature.toString()
        binding.feelsLikeValue.text = weather.feelsLike.toString()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            WeatherCityFragment()
    }
}