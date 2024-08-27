package com.app.testapplication

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.app.testapplication.base.BaseActivity

class AccountSettingsActivity : BaseActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)


    }
}