package com.garageKoi.garage

import android.content.Intent
import android.os.Bundle
import com.garageKoi.garage.base.BaseActivity
import com.garageKoi.garage.databinding.ActivityPinVerificationBinding

class PinVerificationActivity : BaseActivity() {
    private lateinit var binding: ActivityPinVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initial button state
        setButtonState(false)

        binding.apply {
            // continue
            btnContinue.setOnClickListener {
                val intent = Intent(this@PinVerificationActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        listenOTP()
    }

    private fun listenOTP() = with(binding) {
        otpView.setOtpCompletionListener {
            if (it.length == 4 && it == "1234") {
                setButtonState(true)
            } else setButtonState(false)
        }
    }

    private fun setButtonState(enabled: Boolean) {
        binding.btnContinue.isEnabled = enabled
        if (enabled) {
            binding.btnContinue.alpha = 1.0f
        } else {
            binding.btnContinue.alpha = 0.8f
        }
    }
}