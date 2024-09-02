package com.app.testapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.app.testapplication.base.BaseActivity
import com.app.testapplication.databinding.ActivitySignUpBinding

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
        textLayoutName.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check the input fields whenever text changes
                val name = textLayoutName.editText?.text.toString().trim()
                val phone = textLayoutPhone.editText?.text.toString().trim()
                val enableButton = name.isNotEmpty() && phone.isNotEmpty()
                setButtonState(enableButton)
                // error
                if (name.isNotEmpty()) textLayoutName.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed after text changes
            }
        })

        // listener for phone input field
        textLayoutPhone.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check the input fields whenever text changes
                val name = textLayoutName.editText?.text.toString().trim()
                val phone = textLayoutPhone.editText?.text.toString().trim()
                val enableButton = name.isNotEmpty() && phone.isNotEmpty()
                setButtonState(enableButton)
                // error
                if (phone.isNotEmpty()) textLayoutPhone.error = null
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed after text changes
            }
        })
    }


    // TextWatcher to monitor changes in both input fields
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // No action needed before text changes
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Check the input fields whenever text changes
            val name = binding.textLayoutName.editText?.text.toString().trim()
            val phone = binding.textLayoutPhone.editText?.text.toString().trim()
            val enableButton = name.isNotEmpty() && phone.isNotEmpty()
            setButtonState(enableButton)
        }

        override fun afterTextChanged(s: Editable?) {
            // No action needed after text changes
        }
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
    private fun validateInputs() {
        val phone = binding.textLayoutPhone.editText?.text.toString().trim()
        if (phone.length != 11 || !phone.all { it.isDigit() }) {
            // Show error on the phone input layout
            binding.textLayoutPhone.error = getString(R.string.phone_number_does_not_have_11_digits)
        } else {
            // Clear any previous errors
            binding.textLayoutPhone.error = null
            val intent = Intent(this, PinVerificationActivity::class.java)
            startActivity(intent)
        }
    }

}