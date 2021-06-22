package com.penguinpay.repository

import com.penguinpay.database.AppDatabase
import com.penguinpay.database.AppDatabaseDao
import com.penguinpay.datasource.AppDataSource
import com.penguinpay.model.LatestRate
import retrofit2.Response

class AppRepositoryImpl(
    private val appDataSource: AppDataSource,
    private val appDatabase: AppDatabase
) : AppRepository {

    override suspend fun getLatestRates(appId: String): Response<LatestRate> {
        return appDataSource.getLatestRates(appId)
    }

    override fun getAppDatabaseDao(): AppDatabaseDao {
        return appDatabase.getDatabaseDao()
    }
}