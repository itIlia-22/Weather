package com.example.move.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.move.R
import com.example.move.databinding.FragmentMainMapsBinding
import com.example.move.model.City
import com.example.move.model.Weather
import com.example.move.view.DetailsCityFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*

class MapsFragment : Fragment() {

    private var _binding: FragmentMainMapsBinding? = null
    private val binding: FragmentMainMapsBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private lateinit var map: GoogleMap
    private val markers: ArrayList<Marker> = arrayListOf()

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val initialPlace = LatLng(52.52000659999999, 13.404953999999975)
        map.addMarker(MarkerOptions().position(initialPlace)
            .title(R.string.marker_start.toString()))
        map.moveCamera(CameraUpdateFactory.newLatLng(initialPlace))
        map.setOnMapLongClickListener {
            addMarkerToArray(it)
            drawLine()
        }
        activateMyLocation(googleMap)

        map.uiSettings.isZoomControlsEnabled = true
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission
                    .ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        )
            map.isMyLocationEnabled = true

        map.setOnMapClickListener {
            try {
                val weather =
                    Weather(City(getAddressByLocal(it), it.latitude, it.longitude))
                requireActivity().supportFragmentManager.beginTransaction().add(
                    R.id.container,
                    DetailsCityFragment.newInstance(Bundle().apply {
                        putParcelable(
                            DetailsCityFragment.BUNDLE_WEATHER,
                            weather
                        )
                    })
                ).addToBackStack("").commit()


            } catch (e: IndexOutOfBoundsException) {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.Invalid_addressTwo))
                    .setMessage(getString(R.string.Exact_addressTwo))
                    .setNegativeButton(getString(R.string.Ok)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initAddress()
    }


    private fun getAddressByLocal(local: LatLng): String {
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        val address = geoCoder.getFromLocation(
            local.latitude,
            local.longitude,
            100000
        )
        val textAddress = address[0].getAddressLine(0)
        return textAddress
    }


    private fun activateMyLocation(googleMap: GoogleMap) {
        context?.let {
            val isPermissionGranted =
                ContextCompat.checkSelfPermission(it,
                    Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED
            googleMap.isMyLocationEnabled = isPermissionGranted
            googleMap.uiSettings.isMyLocationButtonEnabled = isPermissionGranted
        }

    }


    private fun addMarkerToArray(location: LatLng) {
        val marker = setMarker(location, markers.size.toString(), R.drawable.ic_map_pin)
        markers.add(marker)


    }

    private fun setMarker(
        location: LatLng,
        searchText: String,
        resourceId: Int,
    ): Marker {
        return map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
                .icon(BitmapDescriptorFactory.fromResource(resourceId))
        )!!
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(previous, current)
                    .color(Color.GRAY)
                    .width(5f)
            )
        }
    }

    private fun initAddress() {
        binding.buttonSearch.setOnClickListener {
            val geoCoder = Geocoder(it.context)
            val searchText = binding.searchAddress.text.toString()
            val address = geoCoder.getFromLocationName(searchText, 1)
            val location = LatLng(address[0].latitude, address[0].longitude)
            map.addMarker(
                MarkerOptions().position(location)
                    .title(searchText)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker))
            )

            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    location, 10f
                )
            )

        }
    }


}

