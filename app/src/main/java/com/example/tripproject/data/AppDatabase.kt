package com.example.tripproject.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tripproject.dao.MonedaDAO
import com.example.tripproject.models.Moneda

@Database(entities = [Moneda::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun monedaDao() : MonedaDAO
}