package com.example.tripproject

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Moneda::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase {
    abstract fun monedaDao() : MonedaDAO
}