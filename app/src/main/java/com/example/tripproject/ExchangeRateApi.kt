package com.example.tripproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("v6/8d20a8e45aa2f53e28213f28/latest/{monedaBase}")
    fun obtenerTasasDeCambio(
        @Query("base") monedaBase: String
    ): Call<ExchangeRateResponse>
}