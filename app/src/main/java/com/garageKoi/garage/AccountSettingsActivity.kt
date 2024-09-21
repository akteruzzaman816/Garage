package com.garageKoi.garage

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.garageKoi.garage.base.BaseActivity
import com.garageKoi.garage.databinding.ActivityAccountSettingsBinding
import com.garageKoi.garage.utils.SharedPref

class AccountSettingsActivity : BaseActivity() {
    private lateinit var binding: ActivityAccountSettingsBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            // toolbar
            toolbar.imgBack.setOnClickListener {
                onBackPressed()
            }
            // profile
            txtProfile.setOnClickListener {
                startActivity(Intent(this@AccountSettingsActivity, ProfileActivity::class.java))
            }

            // layout
            layoutLogout.setOnClickListener {
                SharedPref.write(SharedPref.JWT_TOKEN, "")
                // navigate to splash screen
                startActivity(Intent(this@AccountSettingsActivity, SplashActivity::class.java))
                finishAffinity()
            }
        }

    }
}