package com.garageKoi.garage.utils

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.garageKoi.garage.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(inflater: LayoutInflater) : GoogleMap.InfoWindowAdapter {

    private val window: View = inflater.inflate(R.layout.custom_marker_info_window, null)

    private fun render(marker: Marker, view: View) {
        val title = marker.title
        val description = marker.snippet

        val titleView = view.findViewById<TextView>(R.id.txt_title)
        val descriptionView = view.findViewById<TextView>(R.id.txt_desc)

//        titleView.text = title ?: ""
//        descriptionView.text = description ?: ""

    }

    override fun getInfoWindow(marker: Marker): View {
        render(marker, window)
        return window
    }

    override fun getInfoContents(marker: Marker): View {
        render(marker, window)
        return window
    }
}