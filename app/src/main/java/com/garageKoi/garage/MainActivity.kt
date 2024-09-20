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
import com.google.android.gms.maps.model.MapStyleOptions
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
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.uber_style))
        googleMap.uiSettings.apply {
            isMyLocationButtonEnabled = false
            isTiltGesturesEnabled = false
        }
        checkLocationPermission()
        setupMarkers()
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
            }
        }
    }

    private fun setCustomInfoWindow() {
        val inflater = LayoutInflater.from(this)
        googleMap.setInfoWindowAdapter(CustomInfoWindowAdapter(inflater))
    }

    private fun addCustomMarker(lat:Double,lon:Double) {
        val position = LatLng(lat,lon)
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

        setCustomInfoWindow()
    }


    private fun setupMarkers() {
        // Dhaka city locations with their approximate latitude and longitude
        val dhakaLocations = listOf(
            Pair("Adabor", LatLng(23.7663, 90.3580)),
            Pair("Uttara", LatLng(23.8749, 90.4007)),
            Pair("Mirpur", LatLng(23.8040, 90.3667)),
            Pair("Pallabi", LatLng(23.8223, 90.3654)),
            Pair("Kazipara", LatLng(23.8112, 90.3675)),
            Pair("Kafrul", LatLng(23.7881, 90.3740)),
            Pair("Agargaon", LatLng(23.7743, 90.3797)),
            Pair("Sher-e-Bangla Nagar", LatLng(23.7623, 90.3798)),
            Pair("Cantonment Area", LatLng(23.8274, 90.4109)),
            Pair("Banani", LatLng(23.7880, 90.4001)),
            Pair("Gulshan", LatLng(23.7925, 90.4078)),
            Pair("Niketan", LatLng(23.7845, 90.4112)),
            Pair("Shahjadpur", LatLng(23.7964, 90.4170)),
            Pair("Mohakhali", LatLng(23.7784, 90.4055)),
            Pair("Bashundhara", LatLng(23.8173, 90.4272)),
            Pair("Banasree", LatLng(23.7637, 90.4316)),
            Pair("Aftab Nagar", LatLng(23.7640, 90.4360)),
            Pair("Baridhara", LatLng(23.8103, 90.4335)),
            Pair("Uttarkhan", LatLng(23.8907, 90.4310)),
            Pair("Dakshinkhan", LatLng(23.8697, 90.4269)),
            Pair("Bawnia", LatLng(23.8798, 90.4095)),
            Pair("Khilkhet", LatLng(23.8287, 90.4259)),
            Pair("Tejgaon", LatLng(23.7630, 90.4000)),
            Pair("Farmgate", LatLng(23.7557, 90.3824)),
            Pair("Mohammadpur", LatLng(23.7623, 90.3587)),
            Pair("Rampura", LatLng(23.7580, 90.4210)),
            Pair("Badda", LatLng(23.7802, 90.4274)),
            Pair("Satarkul", LatLng(23.8289, 90.4364)),
            Pair("Beraid", LatLng(23.8353, 90.4447)),
            Pair("Khilgaon", LatLng(23.7526, 90.4283)),
            Pair("Vatara", LatLng(23.8184, 90.4491)),
            Pair("Gabtali", LatLng(23.7904, 90.3294))
        )

        val customHue = 154f
        // Add markers for each location in Dhaka
        for (location in dhakaLocations) {
            val markerOptions = MarkerOptions()
                .position(location.second)
                .icon(BitmapDescriptorFactory.defaultMarker(customHue))
                .title(location.first)

            googleMap.addMarker(markerOptions)
        }

        // Coordinates for the 7 divisions of Bangladesh
        val divisionLocations = listOf(
            Pair("Dhaka Division", LatLng(23.8103, 90.4125)),
            Pair("Chittagong Division", LatLng(22.3569, 91.7832)),
            Pair("Rajshahi Division", LatLng(24.3636, 88.6241)),
            Pair("Sylhet Division", LatLng(24.9045, 91.8611)),
            Pair("Khulna Division", LatLng(22.8456, 89.5403)),
            Pair("Barisal Division", LatLng(21.4272, 92.0058)),
            Pair("Rangpur Division", LatLng(25.7439, 89.2752)),
            Pair("Mymensingh Division", LatLng(24.7499, 90.4071))
        )

        // Add markers for each division
        for (division in divisionLocations) {
            val markerOptions = MarkerOptions()
                .position(division.second)
                .icon(BitmapDescriptorFactory.defaultMarker(customHue))
                .title(division.first)

            googleMap.addMarker(markerOptions)
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