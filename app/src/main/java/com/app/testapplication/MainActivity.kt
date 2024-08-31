package com.app.testapplication

import android.content.Intent
import android.os.Bundle
import com.app.testapplication.base.BaseActivity
import com.app.testapplication.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            // profile
            imgProfile.setOnClickListener {
                val intent = Intent(this@MainActivity, AccountSettingsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}