package com.garageKoi.garage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.garageKoi.garage.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            // toolbar
            toolbar.txtTitle.text = getString(R.string.profile)
            toolbar.imgBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}