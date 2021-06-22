package com.penguinpay.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PrefUtil {

    fun saveLongToPref(millis: Long, key: String) {
        sharedPreferences.edit()
            .putLong(key, millis)
            .apply()
    }

    fun getLongFromPref(key: String): Long {
        return sharedPreferences
            .getLong(key, 0)
    }

    companion object {
        lateinit var sharedPreferences: SharedPreferences
        fun invoke(context: Context) {
            if (::sharedPreferences.isInitialized) return
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        }
    }
}