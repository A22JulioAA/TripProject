package com.example.tripproject.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tripproject.models.Ruta
import kotlinx.coroutines.flow.Flow

@Dao
interface RutaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ruta: Ruta)

    @Query("SELECT * FROM ruta")
    fun getAllRutas(): Flow<List<Ruta>>

    @Query("SELECT * FROM ruta WHERE id = :rutaId")
    suspend fun getRutaById(rutaId: Int): Ruta
}