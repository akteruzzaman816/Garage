package com.app.testapplication

import android.content.Intent
import android.os.Bundle
import com.app.testapplication.base.BaseActivity
import com.app.testapplication.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            // continue
            btnContinue.setOnClickListener {
                val intent = Intent(this@SignUpActivity, PinVerificationActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}