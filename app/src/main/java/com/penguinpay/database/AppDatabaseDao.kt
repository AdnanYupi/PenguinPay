package com.penguinpay.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.penguinpay.model.LatestRate
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDatabaseDao {

    @Insert(onConflict = REPLACE)
    fun insertLatestRates(latestRate: LatestRate)

    @Query("SELECT * FROM latestrate WHERE id==0")
    fun getLatestRatesFromDBAsync(): List<LatestRate>?
}