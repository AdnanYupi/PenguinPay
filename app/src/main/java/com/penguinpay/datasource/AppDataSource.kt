package com.penguinpay.datasource

import com.penguinpay.model.LatestRate
import retrofit2.Response

interface AppDataSource {
    suspend fun getLatestRates(appId: String): Response<LatestRate>
}