package com.penguinpay.datasource

import com.penguinpay.model.LatestRate
import com.penguinpay.network.ApiService
import retrofit2.Response

class AppDataSourceImpl(private val apiService: ApiService) : AppDataSource {

    override suspend fun getLatestRates(appId: String): Response<LatestRate> {
        return apiService.getCurrencies(appId)
    }
}