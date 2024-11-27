package com.example.tripproject

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("rates")
    val rates: Map<String, Float>
)
