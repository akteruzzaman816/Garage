package com.garageKoi.garage

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.garageKoi.garage.base.BaseActivity
import com.garageKoi.garage.databinding.ActivityMainBinding
import com.garageKoi.garage.utils.CustomInfoWindowAdapter
import com.garageKoi.garage.utils.Utils
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : BaseActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var googleMap: GoogleMap

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            enableUserLocation()
        } else {
            // Permission denied, handle accordingly
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.apply {
            // map
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this@MainActivity)
            // profile
            imgProfile.setOnClickListener {
                val intent = Intent(this@MainActivity, AccountSettingsActivity::class.java)
                startActivity(intent)
            }
        }
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.apply {
            isMyLocationButtonEnabled = false
            isTiltGesturesEnabled = false
        }
        checkLocationPermission()
        setCustomInfoWindow()
        addCustomMarker()
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            enableUserLocation()
        } else {
            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) { return }
        googleMap.isMyLocationEnabled = true

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val currentLatLng = LatLng(it.latitude, it.longitude)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16f))
//                googleMap.addMarker(MarkerOptions().position(currentLatLng).title("You are here"))
            }
        }
    }

    private fun setCustomInfoWindow() {
        val inflater = LayoutInflater.from(this)
        googleMap.setInfoWindowAdapter(CustomInfoWindowAdapter(inflater))
    }

    private fun addCustomMarker() {
        val position = LatLng(24.741450314569484, 89.82932144162278)
        val markerOptions = MarkerOptions()
            .position(position)
            .title("Garage")
            .snippet("sarishabari, Jamalpur")

        // If using a custom marker layout, uncomment the following lines
         val customMarkerView = layoutInflater.inflate(R.layout.custom_marker, null)
         val markerBitmap = Utils.createBitmapFromView(customMarkerView)
         markerOptions.icon(BitmapDescriptorFactory.fromBitmap(markerBitmap))

        val marker = googleMap.addMarker(markerOptions)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12f))
        // Show the info window immediately after adding the marker
        marker?.showInfoWindow()
        googleMap.setOnInfoWindowClickListener {
            val intent = Intent(this@MainActivity, DetailsPageActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}