package com.example.move.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.move.R
import com.example.move.databinding.FragmentWeatherCityListBinding
import com.example.move.model.Weather
import com.example.move.repository.ItemOnClickListener
import com.example.move.utils.hide
import com.example.move.utils.show
import com.example.move.utils.showSnackBar
import com.example.move.viewmodel.AppState
import com.example.move.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather_city_list.*
import retrofit2.Response.error
import test.TestThreadFragment


class WeatherCityListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var isDataSetRus: Boolean = true
    private var _binding: FragmentWeatherCityListBinding? = null
    private val binding: FragmentWeatherCityListBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        adapter.removeListener()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.FragmentRecyclerView.adapter = adapter
        binding.FragmentFAB.setOnClickListener { changeWeatherDataSet() }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.getWeatherFromLocalRus()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWeatherCityListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val adapter = WeatherAdapter(object : ItemOnClickListener {
        override fun itemOnClickListener(weather: Weather) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(DetailsCityFragment.BUNDLE_WEATHER, weather)
                manager.beginTransaction()
                    .add(R.id.container, DetailsCityFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()

            }
        }

    })

    private fun changeWeatherDataSet() {
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalWorld()
            binding.FragmentFAB.setImageResource(R.drawable.earth_city)
        } else {
            viewModel.getWeatherFromLocalRus()
            binding.FragmentFAB.setImageResource(R.drawable.rus)
        }

        isDataSetRus = !isDataSetRus
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.FragmentLoadingLayout.hide()
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                 binding.FragmentLoadingLayout.show()
            }
            is AppState.Error -> {
                binding.FragmentLoadingLayout.hide()
                expMain.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getWeatherFromLocalRus() })

            }
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            WeatherCityListFragment()
    }




}