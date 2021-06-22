package com.penguinpay.repository
import com.penguinpay.database.AppDatabase
import com.penguinpay.datasource.AppDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppRepositoryModule {

    @Singleton
    @Provides
    fun getRepository(appDataSource: AppDataSource, appDatabase: AppDatabase):AppRepository {
        return AppRepositoryImpl(appDataSource, appDatabase)
    }
}