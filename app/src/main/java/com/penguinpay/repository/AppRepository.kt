package com.penguinpay.repository

import com.penguinpay.database.AppDatabaseDao
import com.penguinpay.model.LatestRate
import retrofit2.Response

interface AppRepository {

    suspend fun getLatestRates(appId: String): Response<LatestRate>
    fun getAppDatabaseDao(): AppDatabaseDao
}