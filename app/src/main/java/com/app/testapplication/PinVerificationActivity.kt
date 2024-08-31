package com.app.testapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.testapplication.databinding.ActivityPinVerificationBinding

class PinVerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPinVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            // continue
            btnContinue.setOnClickListener {
                val intent = Intent(this@PinVerificationActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}