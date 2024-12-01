package com.example.tripproject.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tripproject.dao.MonedaDAO
import com.example.tripproject.dao.RutaDAO
import com.example.tripproject.models.Moneda
import com.example.tripproject.models.Ruta

@Database(entities = [Moneda::class, Ruta::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun monedaDao() : MonedaDAO
    abstract fun rutaDao() : RutaDAO
}