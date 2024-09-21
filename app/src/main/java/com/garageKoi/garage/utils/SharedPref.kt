package com.garageKoi.garage.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPref {
    private var mSharedPref: SharedPreferences? = null
    private const val NAME = "com.garageKoi.garage"

    // const names
    const val JWT_TOKEN = "jwt_token"


    fun init(context: Context) {
        if (mSharedPref == null) mSharedPref =
            context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    fun read(key: String?, defValue: String?): String? {
        return mSharedPref!!.getString(key, defValue)
    }

    fun write(key: String?, value: String?) {
        val prefsEditor = mSharedPref!!.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }
}
