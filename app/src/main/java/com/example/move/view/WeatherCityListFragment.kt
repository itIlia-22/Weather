package com.example.move.view

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.move.R
import com.example.move.adapters.WeatherAdapter
import com.example.move.databinding.FragmentWeatherCityListBinding
import com.example.move.model.City
import com.example.move.model.Weather
import com.example.move.repository.ItemOnClickListener
import com.example.move.repository.OpenClickFragment
import com.example.move.utils.IS_WORLD_KEY
import com.example.move.utils.hide
import com.example.move.utils.show
import com.example.move.utils.showSnackBar
import com.example.move.view.DetailsCityFragment.Companion.BUNDLE_WEATHER
import com.example.move.viewmodel.AppState
import com.example.move.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_weather_city_list.*
import java.util.*


class WeatherCityListFragment : Fragment(), OpenClickFragment {

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
        userPermission()

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


    private fun userPermission() = with(binding) {
        Maps.setOnClickListener {
            checkPermission()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        checkPermissionsResult(requestCode, grantResults)

    }

    private fun checkPermissionsResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                var grantedPermissions = 0
                if ((grantResults.isNotEmpty())) {
                    for (i in grantResults) {
                        if (i == PackageManager.PERMISSION_GRANTED) {
                            grantedPermissions++
                        }
                    }
                    if (grantResults.size == grantedPermissions) {
                        getLocation()
                    } else {
                        showDialog(
                            getString(R.string.dialog_title_no_gps),
                            getString(R.string.dialog_message_no_gps)
                        )
                    }
                } else {
                    showDialog(
                        getString(R.string.dialog_title_no_gps),
                        getString(R.string.dialog_message_no_gps)
                    )
                }
            }
        }
    }

    private fun checkPermission() {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(it, ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }
                shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION) -> {
                    showRationaleDialog()
                }
                else -> {
                    requestPermission()
                }

            }
        }
    }


    private val REQUEST_CODE = 777
    private fun requestPermission() {
        this.requestPermissions(
            arrayOf(ACCESS_COARSE_LOCATION),
            REQUEST_CODE
        )

    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
            provider?.let {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000L,
                    100f,
                    onLocationListener
                )
            }


        }
    }

    private val onLocationListener = LocationListener { location -> getAddressByLocal(location) }

    private fun getAddressByLocal(local: Location) {
        Thread {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(
                local.latitude,
                local.longitude,
                10000
            )[0].getAddressLine(0)
            requireActivity().runOnUiThread {
                showAddressDialog(addresses, local)
            }


        }.start()

    }

    private fun showAddressDialog(address: String, location: Location) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_address_title))
                .setMessage(address)
                .setPositiveButton(getString(R.string.dialog_address_get_weather)) { _, _ ->
                    openDetailsFragment(
                        Weather(
                            City(
                                address,
                                location.latitude,
                                location.longitude
                            )
                        )
                    )
                }
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _, -> dialog.dismiss() }
                .create()
                .show()
        }
    }


    private fun showRationaleDialog() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_rationale_title))
                .setMessage(getString(R.string.dialog_rationale_meaasge))
                .setPositiveButton(getString(R.string.dialog_rationale_give_access))
                { _, _ ->
                    requestPermission()
                }
                .setNegativeButton(getString(R.string.dialog_rationale_decline)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun showDialog(title: String, message: String) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(getString(R.string.dialog_button_close)) {
                        dialog,
                        _,
                    ->
                    dialog.dismiss()
                }

                .create()
                .show()
        }
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

    override fun openDetailsFragment(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().add(
            R.id.container,
            DetailsCityFragment.newInstance(Bundle().apply {
                putParcelable(BUNDLE_WEATHER,
                    weather)
            })
        ).addToBackStack("").commit()
    }


}