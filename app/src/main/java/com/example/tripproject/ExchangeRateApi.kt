package com.example.tripproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("{apiKey}/latest/{monedaBase}")
    fun obtenerTasasDeCambio(
        @Path("apiKey") apiKey: String,
        @Path("monedaBase") monedaBase: String
    ): Call<ExchangeRateResponse>
}