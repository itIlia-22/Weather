package com.example.move.history.historyviewmodel.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.move.R
import com.example.move.adapters.HistoryWeatherAdapter
import com.example.move.databinding.FragmentHistoryBinding
import com.example.move.history.historyviewmodel.HistoryViewModel
import com.example.move.history.historyviewmodel.repository.OnDeleteClickListener
import com.example.move.model.romm.HistoryEntity
import com.example.move.utils.detailsSnackBar
import com.example.move.utils.hide
import com.example.move.utils.show
import com.example.move.viewmodel.AppState
import kotlinx.android.synthetic.main.fragment_details_city.*
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_weather_city_list.*


class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val viewModel: HistoryViewModel by lazy {
        val detailsViewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        detailsViewModel
    }

    private val adapter = HistoryWeatherAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.historyFragmentRecyclerview.adapter = adapter
        viewModel.getLaveData().observe(viewLifecycleOwner, Observer {
            renderData(it)

        })
        viewModel.getAllHistory()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                adapter.setWeather(appState.weatherData)

            }
            is AppState.Loading -> {
                with(binding) {
                    includedLoadingLayout.loadingLayout.hide()
                    mainView.show()
                }

            }
            is AppState.Error -> {
                with(binding){
                    mainView.show()
                    includedLoadingLayout.loadingLayout.hide()
                    mainView.detailsSnackBar(getString(R.string.something_went_wrong))
                }

            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HistoryFragment()
    }




}

