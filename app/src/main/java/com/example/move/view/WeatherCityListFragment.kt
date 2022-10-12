package com.example.move.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.move.R
import com.example.move.adapters.WeatherAdapter
import com.example.move.databinding.FragmentWeatherCityListBinding
import com.example.move.model.Weather
import com.example.move.repository.ItemOnClickListener
import com.example.move.utils.IS_WORLD_KEY
import com.example.move.utils.hide
import com.example.move.utils.show
import com.example.move.utils.showSnackBar
import com.example.move.viewmodel.AppState
import com.example.move.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_weather_city_list.*
import kotlinx.android.synthetic.main.loading_fragments.*
import kotlinx.android.synthetic.main.loading_fragments.view.*


class WeatherCityListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var isDataSetRus: Boolean = false
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
        showListCity()

    }

    private fun showListCity() {
        requireActivity().let {
            if (it.getPreferences(Context.MODE_PRIVATE).getBoolean(IS_WORLD_KEY, false)) {
                changeWeatherDataSet()
            } else {
                viewModel.getWeatherFromLocalRus()
            }
        }

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
            viewModel.getWeatherFromLocalRus()
            binding.FragmentFAB.setImageResource(R.drawable.rus)
        } else {
            viewModel.getWeatherFromLocalWorld()
            binding.FragmentFAB.setImageResource(R.drawable.earth_city)
        }

        isDataSetRus = !isDataSetRus
        saveListCity(isDataSetRus)
    }

    private fun saveListCity(isDataSetRus: Boolean) {
        with(requireActivity().getPreferences(Context.MODE_PRIVATE).edit()) {
            putBoolean(IS_WORLD_KEY, isDataSetRus)
            apply()
        }

    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
            binding.includedLoadingLayout.loadingLayout.hide()
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.includedLoadingLayout.loadingLayout.show()
                binding.includedLoadingLayout.loadingLayout.hide()
            }
            is AppState.Error -> {
                binding.includedLoadingLayout.loadingLayout.hide()
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