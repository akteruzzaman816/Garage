package com.garageKoi.garage

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.garageKoi.garage.adapter.GarageTypeListAdapter
import com.garageKoi.garage.base.BaseActivity
import com.garageKoi.garage.databinding.ActivityDetailsPageBinding

class DetailsPageActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsPageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // setup garage type adapter
        setupGarageTypeAdapter()

    }

    private fun setupGarageTypeAdapter() {
        val items = listOf("Car Workshop","Truck Workshop","Bike Workshop")
        val listAdapter = GarageTypeListAdapter(this, items)
        binding.rvGarageType.adapter = listAdapter

    }
}