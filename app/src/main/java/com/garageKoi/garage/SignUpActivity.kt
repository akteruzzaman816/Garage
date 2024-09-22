package com.garageKoi.garage

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.contract.ActivityResultContracts
import com.garageKoi.garage.base.BaseActivity
import com.garageKoi.garage.databinding.ActivitySignUpBinding
import com.garageKoi.garage.utils.Utils
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi


class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initially disable the button
        setButtonState(enabled = false)
        // setup text listener
        setTextChangeLister()
        // Set OnClickListener for the Continue button
        binding.btnContinue.setOnClickListener {
            validateInputs()
        }

        // check for notification permission
        if (!Utils.checkNotificationPermission(this)) {
            requestNotificationPermission()
        }

    }

    private fun setTextChangeLister() = with(binding) {
        // listener for name input field
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check the input fields whenever text changes
                val name = etName.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val enableButton = name.isNotEmpty() && phone.isNotEmpty()
                setButtonState(enableButton)
                // error
                if (name.isNotEmpty()) etName.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed after text changes
            }
        })

        // listener for phone input field
        etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check the input fields whenever text changes
                val name = etName.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val enableButton = name.isNotEmpty() && phone.isNotEmpty()
                setButtonState(enableButton)
                // error
                if (phone.isNotEmpty()) etPhone.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed after text changes
            }
        })
    }



    // Function to enable or disable the button with appropriate alpha
    private fun setButtonState(enabled: Boolean) {
        binding.btnContinue.isEnabled = enabled
        if (enabled) {
            binding.btnContinue.alpha = 1.0f
        } else {
            binding.btnContinue.alpha = 0.8f
        }
    }

    // Function to validate phone number and proceed
    private fun validateInputs() = with(binding){
        val phone = etPhone.text.toString().trim()
        if (phone.length != 11 || !phone.all { it.isDigit() }) {
            // Show error on the phone input layout
            binding.etPhone.error = getString(R.string.phone_number_does_not_have_11_digits)
        } else {
            // trigger notification
            Utils.createNotificationChannel(this@SignUpActivity)
            Utils.showNotification(this@SignUpActivity,"OTP","Use OTP: 1234 to proceed with Garage. This OTP will be valid for 3 minutes.")

            // Clear any previous errors
            etPhone.error = null
            val intent = Intent(this@SignUpActivity, PinVerificationActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted, proceed with notification-related tasks
                // e.g., show notifications
            } else {
                // Permission denied, handle accordingly
                requestNotificationPermission()
            }
        }
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

}