package com.garageKoi.garage

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.garageKoi.garage.base.BaseActivity
import com.garageKoi.garage.databinding.ActivitySignUpBinding
import com.garageKoi.garage.utils.Utils

class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
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
            Utils.showNotification(this@SignUpActivity)

            // Clear any previous errors
            etPhone.error = null
            val intent = Intent(this@SignUpActivity, PinVerificationActivity::class.java)
            startActivity(intent)
        }
    }

}