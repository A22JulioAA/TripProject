package com.example.tripproject.retrofit

import com.example.tripproject.ExchangeRateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {

    @GET("convert")
    fun obtenerTasaDeCambio (
        @Query("from") monedaBase: String,
        @Query("to") modenaDestino: String,
        @Query("amount") cantidad: Double,
        @Query("access_key") apiKey: String = "9744c63b28fdb2f0708ba90c971d9b8f"
    ): Call<ExchangeRateResponse>
}