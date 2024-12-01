package com.example.tripproject.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ruta")
data class Ruta (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val descripcion: String,
)