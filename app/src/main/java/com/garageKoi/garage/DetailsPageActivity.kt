package com.garageKoi.garage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.garageKoi.garage.base.BaseActivity
import com.garageKoi.garage.databinding.ActivityDetailsPageBinding

class DetailsPageActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            toolbar.imgBack.setOnClickListener { finish() }
            toolbar.txtTitle.text = getString(R.string.details_page)
        }

    }
}