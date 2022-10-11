package com.example.move.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.example.move.R
import com.example.move.databinding.FragmentDetailsCityBinding
import com.example.move.detailsviewmodel.DetailsState
import com.example.move.detailsviewmodel.DetailsViewModel
import com.example.move.model.Weather
import com.example.move.utils.detailsSnackBar
import com.example.move.utils.hide
import com.example.move.utils.show
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_weather.*
import kotlinx.android.synthetic.main.bottom_sheet_weather.view.*
import kotlinx.android.synthetic.main.fragment_details_city.*
import kotlinx.android.synthetic.main.fragment_weather_city_list.view.*


class DetailsCityFragment : Fragment() {

    private val viewModel: DetailsViewModel by lazy {
        val detailsViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        detailsViewModel
    }

    private var _binding: FragmentDetailsCityBinding? = null
    private val binding: FragmentDetailsCityBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        behaviour()
        arguments?.getParcelable<Weather>(BUNDLE_WEATHER).let {
            if (it != null) {
                viewModel.getWeatherProcessing(it.city)
            }
        }
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailsCityBinding.inflate(inflater, container, false)
        return binding.root
    }


    fun ImageView.loadSvg(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
            .build()
        val imageRequest = coil.request.ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()
        imageLoader.enqueue(imageRequest)
    }

    @SuppressLint("SetTextI18n")
    private fun renderData(detailsState: DetailsState) {
        when (detailsState) {
            is DetailsState.Success -> {
                val weather = detailsState.weatherData
                with(binding) {
                    cityName.text = weather.city.city
                    cityCoordinates.text = String.format(
                        getString(R.string.city_coordinates),
                        weather.city.lat.toString(),
                        weather.city.lon.toString()
                    )
                    temperatureValue.text = weather.temperature.toString()
                    feelsLikeValue.text = weather.feelsLike.toString()
                    s.text = weather.condition
                    season.text = weather.season
                    CloudIcon.loadSvg("https://yastatic.net/weather/i/icons/funky/dark/${weather.icon}.svg")
                    btnSheet.windSpeed.text = weather.wind_speed.toString()
                    btnSheet.windGust.text = weather.wind_gust.toString()
                    btnSheet.humidity.text = weather.humidity.toString()
                    btnSheet.polar.text = weather.polar.toString()
                    btnSheet.daytime.text = weather.daytime
                    btnSheet.windDir.text = weather.wind_dir
                    btnSheet.date.text = weather.date
                    btnSheet.week.text = weather.week.toString()
                    btnSheet.sunrise.text = weather.sunrise
                    btnSheet.sunset.text = weather.sunset
                    btnSheet.moonCode.text = weather.moonCode.toString()




                }


            }
            is DetailsState.Loading -> {
                loadingLayout.hide()
                mainView.show()

            }
            is DetailsState.Error -> {
                mainView.show()
                loadingLayout.hide()
                mainView.detailsSnackBar(getString(R.string.something_went_wrong))
            }

        }


    }


    private fun behaviour() {
        with(binding) {
            val bottomSheet = BottomSheetBehavior.from(btnSheet.bottomSheetContainer)
            bottomSheet.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }


    }


    companion object {
        const val BUNDLE_WEATHER = "weather"
        fun newInstance(bundle: Bundle): DetailsCityFragment {
            val fragment = DetailsCityFragment()
            fragment.arguments = bundle
            return fragment
        }


    }


}