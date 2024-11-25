package com.example.tripproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moneda")
data class Moneda(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val codigo: String,
    val simbolo: String,
)
