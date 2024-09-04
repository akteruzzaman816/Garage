package com.garageKoi.garage.utils

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.garageKoi.garage.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(private val inflater: LayoutInflater) : GoogleMap.InfoWindowAdapter {

    override fun getInfoWindow(marker: Marker): View? {
        // Use default info window frame, so return null
        return null
    }

    override fun getInfoContents(marker: Marker): View? {
        // Inflate custom info window layout
        val view = inflater.inflate(R.layout.custom_marker_info_window, null)

        // Set the title and snippet
        val titleTextView = view.findViewById<TextView>(R.id.info_window_title)
        val snippetTextView = view.findViewById<TextView>(R.id.info_window_snippet)

        titleTextView.text = marker.title
        snippetTextView.text = marker.snippet

        return view
    }
}