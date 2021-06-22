package com.penguinpay.model
import com.google.gson.annotations.SerializedName

data class Rates(
    @SerializedName("KES")
    val kes: Double,
    @SerializedName("NGN")
    val ngn: Double,
    @SerializedName("TZS")
    val tzs: Double,
    @SerializedName("UGX")
    val ugx: Double,
    @SerializedName("USD")
    val usd: Double,

)