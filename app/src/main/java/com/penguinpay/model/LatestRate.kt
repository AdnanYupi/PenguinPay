package com.penguinpay.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LatestRate(
    val base: String?,
    val disclaimer: String?,
    val license: String?,
    @Embedded
    val rates: Rates?,
    val timestamp: Int,
    @PrimaryKey
    val id: Int = 0
)