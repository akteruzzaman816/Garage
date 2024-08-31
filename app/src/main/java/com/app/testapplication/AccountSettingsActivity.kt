package com.app.testapplication

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.app.testapplication.base.BaseActivity
import com.app.testapplication.databinding.ActivityAccountSettingsBinding

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
        }

    }
}