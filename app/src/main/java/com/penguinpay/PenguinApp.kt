package com.penguinpay

import android.app.Application
import com.penguinpay.database.AppDatabase
import com.penguinpay.util.PrefUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PenguinApp: Application() {

    override fun onCreate() {
        super.onCreate()
        PrefUtil.invoke(this)
        AppDatabase.init(this)
    }
}