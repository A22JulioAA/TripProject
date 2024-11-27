package com.example.tripproject.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tripproject.models.Moneda
import kotlinx.coroutines.flow.Flow

@Dao
interface MonedaDAO {
    @Insert
    suspend fun insertMoneda(monedas: List<Moneda>)

    @Query("SELECT * FROM moneda WHERE codigo = :codigo LIMIT 1")
    suspend fun getMonedaByCodigo(codigo: String): Moneda?

    @Query("SELECT * FROM moneda ORDER BY nombre ASC")
    fun getMonedas(): Flow<List<Moneda>>

    @Update
    suspend fun updateMoneda(moneda: Moneda)
}